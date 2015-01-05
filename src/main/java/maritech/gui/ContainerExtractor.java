package maritech.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.IUpgradable;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotFluidContainer;
import mariculture.core.gui.SlotOutput;
import mariculture.core.gui.SlotUpgrade;
import mariculture.core.helpers.FluidHelper;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import maritech.tile.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerExtractor extends ContainerMachine {
    public ContainerExtractor(TileExtractor tile, InventoryPlayer playerInventory) {
        super(tile);

        addUpgradeSlots(tile);
        addSlotToContainer(new SlotFluidContainer(tile, 3, 50, 25));
        addSlotToContainer(new SlotOutput(tile, 4, 50, 56));
        addSlotToContainer(new Slot(tile, 5, 8, 62));
        addSlotToContainer(new SlotFish(tile, 6, 78, 40));
        addSlotToContainer(new SlotOutput(tile, 7, 133, 23));
        addSlotToContainer(new SlotOutput(tile, 8, 133, 41));
        addSlotToContainer(new SlotOutput(tile, 9, 133, 59));
        addSlotToContainer(new SlotOutput(tile, 10, 151, 23));
        addSlotToContainer(new SlotOutput(tile, 11, 151, 41));
        addSlotToContainer(new SlotOutput(tile, 12, 151, 59));

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
                if (stack.getItem() instanceof ItemFishy) {
                    if (!mergeItemStack(stack, 6, 7, false)) return null; //Slot 6 for the Fish
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null; //Slot 0-2 for the Upgrades
                } else if (stack.getItem() instanceof IEnergyContainerItem) {
                    if (!mergeItemStack(stack, 5, 6, false)) return null; //Slot 5 for the Energy
                } else if (FluidHelper.isFluidOrEmpty(stack) || FishFoodHandler.isFishFood(stack)) {
                    if (!mergeItemStack(stack, 3, 4, false)) return null; // Slot 3 for the Liquids
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

    private class SlotFish extends Slot {
        public SlotFish(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == Fishery.fishy;
        }
    }
}