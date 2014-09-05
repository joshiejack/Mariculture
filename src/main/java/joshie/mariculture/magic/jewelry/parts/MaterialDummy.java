package joshie.mariculture.magic.jewelry.parts;

import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class MaterialDummy extends JewelryMaterial {
    public MaterialDummy() {
        ignore = true;
    }

    @Override
    public String getColor() {
        return joshie.lib.util.Text.WHITE;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 0;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.0F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 1.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 1.0F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Blocks.fire);
    }
}
