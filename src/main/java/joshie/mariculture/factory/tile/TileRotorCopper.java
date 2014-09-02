package joshie.mariculture.factory.tile;

import joshie.mariculture.factory.Factory;
import net.minecraft.item.ItemStack;

public class TileRotorCopper extends TileRotor {
    @Override
    protected double getTier() {
        return 1D;
    }

    @Override
    protected int getMaxDamage() {
        return 180000;
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(Factory.turbineCopper);
        stack.setItemDamage((int) Math.floor(damage / 200));
        return stack;
    }
}
