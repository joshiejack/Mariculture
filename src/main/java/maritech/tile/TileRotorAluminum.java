package maritech.tile;

import maritech.extensions.modules.ExtensionFactory;
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
        ItemStack stack = new ItemStack(ExtensionFactory.turbineAluminum);
        stack.setItemDamage((int) Math.floor(damage / 200));
        return stack;
    }
}
