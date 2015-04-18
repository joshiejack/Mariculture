package maritech.tile;

import maritech.extensions.modules.ExtensionFactory;
import net.minecraft.item.ItemStack;

public class TileRotorTitanium extends TileRotor {
    @Override
    protected double getTier() {
        return 5D;
    }
    
    //8 Hours of Operation

    @Override
    protected int getMaxDamage() {
        return 34560000;
    }
    
    @Override
    public void setDamage(int meta) {
        this.damage = (int) Math.floor(damage * 1152);
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(ExtensionFactory.turbineTitanium);
        stack.setItemDamage((int) Math.floor(damage / 1152));
        return stack;
    }
}
