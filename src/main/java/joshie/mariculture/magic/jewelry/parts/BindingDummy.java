package joshie.mariculture.magic.jewelry.parts;

import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BindingDummy extends JewelryBinding {
    public BindingDummy() {
        ignore = true;
    }

    @Override
    public String getColor() {
        return joshie.lib.util.Text.WHITE;
    }

    @Override
    public int getHitsBase(JewelryType type) {
        return 100;
    }

    @Override
    public int getDurabilityBase(JewelryType type) {
        return 250;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Blocks.fire);
    }

    @Override
    public int getKeepEnchantmentChance(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaxEnchantmentLevel(JewelryType type) {
        return 0;
    }
}
