package mariculture.core.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMaterial extends ItemMCMeta {
    @Override
    public int getMetaCount() {
        return MaterialsMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case MaterialsMeta.INGOT_ALUMINUM:
                return "ingotAluminum";
            case MaterialsMeta.INGOT_MAGNESIUM:
                return "ingotMagnesium";
            case MaterialsMeta.INGOT_TITANIUM:
                return "ingotTitanium";
            case MaterialsMeta.INGOT_COPPER:
                return "ingotCopper";
            case MaterialsMeta.FISH_MEAL:
                return "fishMeal";
            case MaterialsMeta.DYE_YELLOW:
                return "dyeYellow";
            case MaterialsMeta.DYE_RED:
                return "dyeRed";
            case MaterialsMeta.DYE_BROWN:
                return "dyeBrown";
            case MaterialsMeta.DUST_ALUMINUM:
                return "dustAluminum";
            case MaterialsMeta.DUST_MAGNESITE:
                return "dustMagnesite";
            case MaterialsMeta.DUST_SALT:
                return "foodSalt";
            case MaterialsMeta.INGOT_RUTILE:
                return "ingotRutile";
            case MaterialsMeta.DUST_COPPEROUS:
                return "dustCopperous";
            case MaterialsMeta.DUST_GOLDEN:
                return "dustGolden";
            case MaterialsMeta.DUST_IRONIC:
                return "dustIronic";
            case MaterialsMeta.DUST_LEADER:
                return "dustLeader";
            case MaterialsMeta.DUST_SILVERY:
                return "dustSilvery";
            case MaterialsMeta.DUST_TINNIC:
                return "dustTinnic";
            case MaterialsMeta.DYE_GREEN:
                return "dyeGreen";
            case MaterialsMeta.DYE_WHITE:
                return "dyeWhite";
            case MaterialsMeta.DYE_BLUE:
                return "dyeBlue";
            case MaterialsMeta.NUGGET_ALUMINUM:
                return "nuggetAluminum";
            case MaterialsMeta.NUGGET_COPPER:
                return "nuggetCopper";
            case MaterialsMeta.NUGGET_IRON:
                return "nuggetIron";
            case MaterialsMeta.NUGGET_MAGNESIUM:
                return "nuggetMagnesium";
            case MaterialsMeta.NUGGET_RUTILE:
                return "nuggetRutile";
            case MaterialsMeta.NUGGET_TITANIUM:
                return "nuggetTitanium";
            default:
                return "nuggetTitanium";
        }
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case MaterialsMeta.FISH_MEAL:
                return Modules.isActive(Modules.fishery);
            case MaterialsMeta.DYE_RED:
                return Modules.isActive(Modules.worldplus);
            case MaterialsMeta.DYE_YELLOW:
                return Modules.isActive(Modules.worldplus);
            case MaterialsMeta.DYE_BROWN:
                return Modules.isActive(Modules.worldplus);
            case MaterialsMeta.DYE_GREEN:
                return Modules.isActive(Modules.worldplus);
            case MaterialsMeta.DYE_WHITE:
                return Modules.isActive(Modules.worldplus);
            case MaterialsMeta.DUST_MAGNESITE:
                return false;
        }

        if (meta >= MaterialsMeta.EMPTY_START && meta <= MaterialsMeta.EMPTY_END) return false;

        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!player.canPlayerEdit(x, y, z, par7, stack)) return false;
        else {
            int blockID;
            int var12;
            int var13;

            if (stack.getItemDamage() == MaterialsMeta.FISH_MEAL) {
                if (ItemDye.applyBonemeal(stack, world, x, y, z, player)) {
                    if (!world.isRemote) {
                        world.playAuxSFX(2005, x, y, z, 0);
                    }

                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public boolean isValidTab(CreativeTabs creative, int meta) {
        return meta == MaterialsMeta.FISH_MEAL && creative == MaricultureTab.tabFishery ? true : creative == MaricultureTab.tabCore;
    }
}
