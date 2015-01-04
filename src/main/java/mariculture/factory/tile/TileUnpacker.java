package mariculture.factory.tile;

import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.tile.base.TileStorage;
import mariculture.factory.UnpackerHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileUnpacker extends TileStorage implements ISidedInventory {
    public static final int INPUT = 0;
    public static final int[] SLOTS = new int[] { INPUT };

    public TileUnpacker() {
        inventory = new ItemStack[1];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        if (tile instanceof IInventory) {
            InventoryHelper.insertItemStackIntoInventory((IInventory) tile, UnpackerHelper.unpack(worldObj, stack), ForgeDirection.UP.ordinal());
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return SLOTS;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == INPUT && side == ForgeDirection.UP.ordinal()) {
            ItemStack ret = UnpackerHelper.unpack(worldObj, stack);
            if (ret == null) return false;
            else {
                TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
                if (tile instanceof IInventory) {
                    return InventoryHelper.canInsertItemStackIntoInventory((IInventory) tile, ret, ForgeDirection.UP.ordinal());
                } else return false;
            }
        } else return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return false;
    }
}
