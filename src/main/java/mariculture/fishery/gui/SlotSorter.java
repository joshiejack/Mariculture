package mariculture.fishery.gui;

import mariculture.core.gui.SlotFake;
import mariculture.factory.tile.TileFishSorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSorter extends SlotFake {
    private IInventory tile;

    public SlotSorter(IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
        tile = inv;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        if (getHasStack()) {
            Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return false;
    }

    @Override
    public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {
        if (mouseButton > 0) {
            slot.putStack(null);
        } else if (mouseButton == 0) {
            ItemStack stack;
            InventoryPlayer playerInv = player.inventory;
            slot.onSlotChanged();
            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();

            if (stackSlot == null && stackHeld != null && slot.slotNumber != 22) if (isItemValid(stackHeld)) {
                ItemStack copy = stackHeld.copy();
                copy.stackSize = 1;
                slot.putStack(copy);
            }

            if (stackHeld == null) if (tile instanceof TileFishSorter) {
                ((TileFishSorter) tile).swapSide(slot.slotNumber);
            }
        }

        return null;
    }
}