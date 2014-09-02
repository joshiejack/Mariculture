package joshie.mariculture.magic.jewelry.parts;

import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BindingBasic extends JewelryBinding {
    @Override
    public String getColor() {
        return joshie.lib.util.Text.WHITE;
    }

    @Override
    public int getHitsBase(JewelryType type) {
        switch (type) {
            case RING:
                return 35;
            case BRACELET:
                return 65;
            case NECKLACE:
                return 100;
            default:
                return 50;
        }
    }

    @Override
    public int getDurabilityBase(JewelryType type) {
        switch (type) {
            case RING:
                return 50;
            case BRACELET:
                return 150;
            case NECKLACE:
                return 300;
            default:
                return 100;
        }
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        switch (type) {
            case RING:
                return new ItemStack(Items.iron_ingot);
            case BRACELET:
                return new ItemStack(Items.string);
            case NECKLACE:
                return new ItemStack(Blocks.wool);
            default:
                return null;
        }
    }

    @Override
    public int getKeepEnchantmentChance(JewelryType type) {
        switch (type) {
            case RING:
                return 50;
            case BRACELET:
                return 50;
            case NECKLACE:
                return 50;
            default:
                return 30;
        }
    }

    @Override
    public int getMaxEnchantmentLevel(JewelryType type) {
        switch (type) {
            case RING:
                return 2;
            case BRACELET:
                return 3;
            case NECKLACE:
                return 3;
            default:
                return 3;
        }
    }
}
