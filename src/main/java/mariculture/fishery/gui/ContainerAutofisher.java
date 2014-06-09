package mariculture.fishery.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.fishery.Fishing;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotOutput;
import mariculture.fishery.tile.TileAutofisher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerAutofisher extends ContainerMachine {
    public ContainerAutofisher(TileAutofisher tile, InventoryPlayer playerInventory) {
        super(tile);

        addUpgradeSlots(tile);
        addPowerSlot(tile);

        addSlotToContainer(new SlotFishingRod(tile, 4, 49, 18));

        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                addSlotToContainer(new SlotBait(tile, 4 + i + j * 3, 31 + (i - 1) * 18, 41 + j * 18));
            }
        }

        for (int i = 7; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new SlotOutput(tile, 4 + i + j * 3, 115 + (i - 7) * 18, 23 + j * 18));
            }
        }

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
                if (Fishing.fishing.getRodType(stack) != null) {
                    if (!mergeItemStack(stack, 4, 5, false)) return null;
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null;
                } else if (stack.getItem() instanceof IEnergyContainerItem) {
                    if (!mergeItemStack(stack, 3, 4, false)) return null;
                } else if (Fishing.fishing.getBaitQuality(stack) > 0) {
                    if (!mergeItemStack(stack, 5, 11, false)) // 5-10
                    return null;
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

    private class SlotFishingRod extends Slot {
        public SlotFishingRod(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return Fishing.fishing.getRodType(stack) != null;
        }
    }

    private class SlotBait extends Slot {
        public SlotBait(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return Fishing.fishing.getBaitQuality(stack) > 0;
        }
    }
}