package mariculture.factory.tile;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IEjectable;
import mariculture.factory.UnpackerHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileUnpacker extends TileStorage implements ISidedInventory, IEjectable {
    public BlockTransferHelper helper;
    public static final int INPUT = 0;
    public static final int[] SLOTS = new int[] { INPUT };

    public TileUnpacker() {
        inventory = new ItemStack[1];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        ItemStack ejecting = stack.copy();
        if (ejecting != null) {
            if (helper == null) helper = new BlockTransferHelper(this);
            helper.insertStack(UnpackerHelper.unpack(worldObj, stack), null);
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return SLOTS;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == INPUT && inventory[INPUT] == null && UnpackerHelper.canUnpack(worldObj, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public EjectSetting getEjectSetting() {
        return EjectSetting.ITEM;
    }

    @Override
    public void setEjectSetting(EjectSetting setting) {
        return;
    }

    @Override
    public void handleButtonClick(int id) {
        return;
    }
}
