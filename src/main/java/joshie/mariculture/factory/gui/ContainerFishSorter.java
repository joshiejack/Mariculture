package joshie.mariculture.factory.gui;

import joshie.mariculture.core.gui.ContainerMachine;
import joshie.mariculture.factory.tile.TileFishSorter;
import joshie.mariculture.fishery.gui.SlotSorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishSorter extends ContainerMachine {
    public ContainerFishSorter(TileFishSorter tile, InventoryPlayer playerInventory) {
        super(tile);

        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 7; ++k) {
                addSlotToContainer(new SlotSorter(tile, k + j * 7, 38 + k * 18, 21 + j * 18));
            }
        }

        addSlotToContainer(new Slot(tile, 21, 9, 25));
        bindPlayerInventory(playerInventory, 10);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = getSizeInventory();
        int low = size + 27;
        int high = low + 9;
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;
                slot.onSlotChange(stack, itemstack);
            } else if (slotID >= size) {
                if (!mergeItemStack(stack, TileFishSorter.input, TileFishSorter.input + 1, false)) return null;
            } else if (!mergeItemStack(stack, size, high, false)) return null;

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, stack);
        }

        return itemstack;
    }
}