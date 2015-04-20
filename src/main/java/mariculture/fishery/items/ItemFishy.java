package mariculture.fishery.items;

import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.ISpecialSorting;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.items.ItemMCBaseSingle;
import mariculture.core.util.MCTranslate;
import mariculture.fishery.Fish;
import mariculture.fishery.FishyHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishy extends ItemMCBaseSingle implements ISpecialSorting {
    private static IIcon egg;

    public ItemFishy() {
        setCreativeTab(MaricultureTab.tabFishery);
        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    @Override
    public boolean isSame(ItemStack fish1, ItemStack fish2, boolean isPerfectMatch) {
        if (fish2.getItem() instanceof ItemFishy) {
            if (Fishing.fishHelper.isEgg(fish1)) return Fishing.fishHelper.isEgg(fish2);
            if (Fish.species.getDNA(fish1).equals(Fish.species.getDNA(fish2))) return Fish.species.getLowerDNA(fish1).equals(Fish.species.getLowerDNA(fish2));
            if (Fish.species.getDNA(fish1).equals(Fish.species.getLowerDNA(fish2))) return Fish.species.getLowerDNA(fish1).equals(Fish.species.getDNA(fish2));
        }
        return false;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        FishSpecies species = Fishing.fishHelper.getSpecies(stack);
        if (species != null) return getUnlocalizedName() + "." + species.getID();

        return "fishy";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        FishSpecies active = FishSpecies.species.get(Fish.species.getDNA(stack));
        FishSpecies inactive = FishSpecies.species.get(Fish.species.getLowerDNA(stack));
        if (active == null || inactive == null) return MCTranslate.translate("anyFish");
        if (active != inactive) return mariculture.lib.util.Text.AQUA + active.getName() + "-" + inactive.getName() + " " + mariculture.lib.util.Text.localize("mariculture.fish.data.hybrid") + convertToSymbol(Fish.gender.getDNA(stack));
        else return mariculture.lib.util.Text.AQUA + active.getName() + convertToSymbol(Fish.gender.getDNA(stack));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        FishSpecies species = Fishing.fishHelper.getSpecies(stack);
        if (species != null) {
            for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
                FishDNABase.DNAParts.get(i).getInformationDisplay(stack, list);
            }
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        FishSpecies species = Fishing.fishHelper.getSpecies(stack);
        if (species != null) return species.getIcon(Fish.gender.getDNA(stack));
        else return new ItemStack(Items.fish, 1, 32000).getIconIndex();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
            species.getValue().registerIcon(iconRegister);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
            FishSpecies fishy = species.getValue();
            ItemStack fish = Fishing.fishHelper.makePureFish(fishy);
            list.add(Fish.gender.addDNA(fish, FishyHelper.MALE));
            list.add(Fish.gender.addDNA(fish.copy(), FishyHelper.FEMALE));
        }
    }

    private String convertToSymbol(int gender) {
        if (gender == FishyHelper.MALE) return "\u2642";
        if (gender == FishyHelper.FEMALE) return "\u2640";

        return "";
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world) {
        if (stack.getItem() == this && stack.hasTagCompound()) {
            FishSpecies fishy = Fishing.fishHelper.getSpecies(stack);
            return fishy.getOnLandLifespan(fishy);
        } else return 15;
    }
}
