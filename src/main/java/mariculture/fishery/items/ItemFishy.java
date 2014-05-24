package mariculture.fishery.items;

import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Text;
import mariculture.fishery.Fish;
import mariculture.fishery.FishHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishy extends Item {
	private static Icon egg;

	public ItemFishy(int i) {
		super(i);
		maxStackSize = 1;
		setHasSubtypes(true);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		FishSpecies species = Fishing.fishHelper.getSpecies(stack);
		if(species != null) {
			return getUnlocalizedName() + "." + species.getID();
		}

		return "fishy";
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if(Fishing.fishHelper.isEgg(stack)) return Text.localize("fish.data.species.egg");
		FishSpecies active = FishSpecies.species.get(Fish.species.getDNA(stack));
		FishSpecies inactive = FishSpecies.species.get(Fish.species.getLowerDNA(stack));
		if(active == null || inactive == null) return Text.translate("anyFish");
		if(active != inactive) {
			return Text.AQUA + active.getName() + "-" + inactive.getName() + " " + Text.localize("fish.data.hybrid") + convertToSymbol(Fish.gender.getDNA(stack));
		} else {
			return Text.AQUA + active.getName() + convertToSymbol(Fish.gender.getDNA(stack));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(Fishing.fishHelper.isEgg(stack)) {
			if(stack.stackTagCompound.getInteger("currentFertility") > 0) {
				list.add(stack.stackTagCompound.getInteger("currentFertility") + " " + Text.translate("eggsRemaining"));
			} else {
				list.add(Text.translate("undetermined") + " " + Text.translate("eggsRemaining"));
			}
		} else {
			FishSpecies species = Fishing.fishHelper.getSpecies(stack);
			if(species != null) {
				for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
					FishDNABase.DNAParts.get(i).getInformationDisplay(stack, list);
				}
			}
		}
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(Fishing.fishHelper.isEgg(stack)) return egg;
		else {
			FishSpecies species = Fishing.fishHelper.getSpecies(stack);
			if(species != null) {
				return species.getIcon(Fish.gender.getDNA(stack));
			} else return Fish.cod.getIcon(0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
			species.getValue().registerIcon(iconRegister);
		}

		egg = iconRegister.registerIcon(Mariculture.modid + ":" + "fish/egg");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
			FishSpecies fishy = species.getValue();
			ItemStack fish = Fishing.fishHelper.makePureFish(fishy);
			list.add(Fish.gender.addDNA(fish, FishHelper.MALE));
			list.add(Fish.gender.addDNA(fish.copy(), FishHelper.FEMALE));
		}
	}

	private String convertToSymbol(int gender) {
		return gender == FishHelper.MALE? "\u2642": "\u2640";
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		if(Fishing.fishHelper.isEgg(stack)) return 6000;
		else return 15;
	}
}
