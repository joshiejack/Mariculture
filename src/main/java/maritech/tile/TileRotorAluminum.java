package maritech.tile;

import maritech.extensions.modules.ExtensionFactory;
import net.minecraft.item.ItemStack;

public class TileRotorAluminum extends TileRotor {
    @Override
    protected double getTier() {
        return 2D;
    }
    
    //10 Damage Per Tick
    //1 Hour Operation

    @Override
    protected int getMaxDamage() {
        return 8640000;
    }
    
    @Override
    public void setDamage(int meta) {
        this.damage = (int) Math.floor(damage * 288);
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(ExtensionFactory.turbineAluminum);
        stack.setItemDamage((int) Math.floor(damage / 288));
        return stack;
    }
}
