package joshie.maritech.gui;

import joshie.mariculture.api.core.IItemUpgrade;
import joshie.mariculture.api.core.interfaces.IPVChargeable;
import joshie.mariculture.core.gui.ContainerMachine;
import joshie.mariculture.core.gui.SlotFluidContainer;
import joshie.mariculture.core.gui.SlotOutput;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.maritech.tile.TilePressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPressureVessel extends ContainerMachine {
    public ContainerPressureVessel(TilePressureVessel tile, InventoryPlayer playerInventory) {
        super(tile);

        addUpgradeSlots(tile);
        addSlotToContainer(new SlotFluidContainer(tile, 3, 128, 25));
        addSlotToContainer(new SlotOutput(tile, 4, 128, 56));
        addSlotToContainer(new Slot(tile, 5, 37, 40));

        bindPlayerInventory(playerInventory, 10);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = ((IInventory) tile).getSizeInventory();
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
                if (itemstack.getItem() instanceof IPVChargeable) {
                    if (!mergeItemStack(stack, 5, 6, false)) return null;
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null;
                } else if (FluidHelper.isFluidOrEmpty(stack)) {
                    if (!mergeItemStack(stack, 3, 4, false)) return null;
                } else if (slotID >= size && slotID < low) {
                    if (!mergeItemStack(stack, low, high, false)) return null;
                } else if (slotID >= low && slotID < high && !mergeItemStack(stack, high, low, false)) return null;
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