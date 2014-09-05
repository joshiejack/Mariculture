package joshie.maritech.tile;

import joshie.mariculture.factory.Factory;
import net.minecraft.item.ItemStack;

public class TileRotorAluminum extends TileRotor {
    @Override
    protected double getTier() {
        return 2D;
    }

    @Override
    protected int getMaxDamage() {
        return 720000;
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(Factory.turbineAluminum);
        stack.setItemDamage((int) Math.floor(damage / 200));
        return stack;
    }
}
