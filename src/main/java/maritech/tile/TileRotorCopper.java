package maritech.tile;

import maritech.extensions.modules.ExtensionFactory;
import net.minecraft.item.ItemStack;

public class TileRotorCopper extends TileRotor {
    @Override
    protected double getTier() {
        return 1D;
    }
    
    //10 Damage per TICK
    //15 Minute Operation

    @Override
    protected int getMaxDamage() {
        return 1440000;
    }
    
    @Override
    public void setDamage(int meta) {
        this.damage = (int) Math.floor(damage * 38); 
    }

    @Override
    public ItemStack getDrop() {
        ItemStack stack = new ItemStack(ExtensionFactory.turbineCopper);
        stack.setItemDamage((int) Math.floor(damage / 38));
        return stack;
    }
}
