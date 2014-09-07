package joshie.maritech.tile;

import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;
import joshie.mariculture.core.lib.MachineSpeeds;
import joshie.maritech.tile.base.TileTankPowered;
import net.minecraft.item.ItemStack;

public class TileInjector extends TileTankPowered {
    public static final int FISH = 6;
    public static final int TEMPLATE = 7;
    
    public TileInjector() {
        inventory = new ItemStack[9];
        max = MachineSpeeds.getDNAMachineSpeed();
        output = new int[] { 8 };
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 3, 4, 6, 7, 8 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return false;
    }
    
    @Override
    public int getTankCapacity(int size) {
        return size * 1000;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public int getRFCapacity() {
        return (1 + rf) * 10000;
    }

    @Override
    public void process() {
        // TODO Auto-generated method stub
        
    }
}
