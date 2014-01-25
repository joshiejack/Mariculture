package mariculture.fishery.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
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
		if (stack.hasTagCompound()) {
			return (getUnlocalizedName() + "." + stack.getTagCompound().getInteger("SpeciesID"));
		}

		return "fishy";
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if (stack.hasTagCompound() && !Fishing.fishHelper.isEgg(stack)) {
			int species1 = stack.stackTagCompound.getInteger("SpeciesID");
			int species2 = stack.stackTagCompound.getInteger("lowerSpeciesID");

			if (species1 != species2) {
				return "\u00a7b" + Fishing.fishHelper.getSpecies(species1).getName() + "-"
						+ Fishing.fishHelper.getSpecies(species2).getName() + " "
						+ StatCollector.translateToLocal("fish.data.hybrid")
						+ convertToSymbol(stack.stackTagCompound.getInteger("Gender"));
			}
		}

		if (stack.hasTagCompound()) {
			if (Fishing.fishHelper.isEgg(stack)) {
				return "\u00a7b" + StatCollector.translateToLocal("fish.data.species.egg");
			}

			return "\u00a7b" + Fishing.fishHelper.getSpecies(stack.stackTagCompound.getInteger("SpeciesID")).getName()
					+ convertToSymbol(stack.stackTagCompound.getInteger("Gender"));
		}

		return "\u00a7b" + StatCollector.translateToLocal("mariculture.string.anyFish");
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound()) {
			if(!Fishing.fishHelper.isEgg(stack)) {
				for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
					FishDNA.DNAParts.get(i).getInformationDisplay(stack, list);
				}
			} else {
				if(stack.stackTagCompound.getInteger("currentFertility") > 0) {
					list.add(stack.stackTagCompound.getInteger("currentFertility") + " " 
							+ StatCollector.translateToLocal("mariculture.string.eggsRemaining"));
				} else {
					list.add(StatCollector.translateToLocal("mariculture.string.undetermined") + " " 
							+ StatCollector.translateToLocal("mariculture.string.eggsRemaining"));
				}
			}
		}
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("isEgg")) {
				return egg;
			}
			
			FishSpecies fish = Fishing.fishHelper.getSpecies(stack.stackTagCompound.getInteger("SpeciesID"));
			if(fish != null) {
				return fish.getIcon();
			}
		}

		return Fishery.cod.getIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);
			if(fish != null) {
				fish.registerIcon(iconRegister);
			}
		}

		egg = iconRegister.registerIcon(Mariculture.modid + ":" + "fish/egg");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		for (int i = 0; i < FishSpecies.speciesList.size(); ++i) {
			ItemStack fish = Fishing.fishHelper.makePureFish(FishSpecies.speciesList.get(i));

			list.add(Fishery.gender.addDNA(fish, FishHelper.MALE));
			list.add(Fishery.gender.addDNA(fish.copy(), FishHelper.FEMALE));
		}
	}

	private String convertToSymbol(int gender) {
		if (gender == FishHelper.MALE) {
			return "\u2642";
		}
		if (gender == FishHelper.FEMALE) {
			return "\u2640";
		}

		return "";
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public int getEntityLifespan(ItemStack stack, World world) {
		if(stack.hasTagCompound() && !Fishing.fishHelper.isEgg(stack)) {
			return 10;
		} else{
			return 6000;
		}
	}
}
