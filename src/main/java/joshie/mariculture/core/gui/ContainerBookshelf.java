package joshie.mariculture.core.gui;

import joshie.mariculture.core.tile.TileBookshelf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBookshelf extends Container {
    private TileBookshelf tile;

    public ContainerBookshelf(TileBookshelf tile, InventoryPlayer playerInventory) {
        this.tile = tile;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new Slot(tile, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

        bindPlayerInventory(playerInventory);
    }

    private void bindPlayerInventory(InventoryPlayer playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            stack = itemstack1.copy();

            if (slotID < 9) {
                if (!mergeItemStack(itemstack1, 9, 45, true)) return null;
            } else if (!mergeItemStack(itemstack1, 0, 9, false)) return null;

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == stack.stackSize) return null;

            slot.onPickupFromSlot(player, itemstack1);
        }

        return stack;
    }
}