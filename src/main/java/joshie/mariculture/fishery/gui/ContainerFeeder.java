package joshie.mariculture.fishery.gui;

import joshie.mariculture.api.core.interfaces.IItemUpgrade;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.core.gui.ContainerMachine;
import joshie.mariculture.core.gui.SlotFluidContainer;
import joshie.mariculture.core.gui.SlotOutput;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.fishery.Fish;
import joshie.mariculture.fishery.FishFoodHandler;
import joshie.mariculture.fishery.FishyHelper;
import joshie.mariculture.fishery.items.ItemFishy;
import joshie.mariculture.fishery.tile.TileFeeder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFeeder extends ContainerMachine {

    public ContainerFeeder(TileFeeder tile, InventoryPlayer playerInventory) {
        super(tile);

        addUpgradeSlots(tile);

        //Fluids
        addSlotToContainer(new SlotFluidContainer(tile, 3, 10, 25));
        addSlotToContainer(new SlotOutput(tile, 4, 10, 56));

        //Fish
        addSlotToContainer(new SlotFather(tile, 5, 76, 25));
        addSlotToContainer(new SlotMother(tile, 6, 76, 53));

        //Output
        addSlotToContainer(new SlotOutput(tile, 7, 132, 22));
        addSlotToContainer(new SlotOutput(tile, 8, 132, 40));
        addSlotToContainer(new SlotOutput(tile, 9, 132, 58));
        addSlotToContainer(new SlotOutput(tile, 10, 150, 22));
        addSlotToContainer(new SlotOutput(tile, 11, 150, 40));
        addSlotToContainer(new SlotOutput(tile, 12, 150, 58));

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
                if (stack.getItem() instanceof ItemFishy && Fish.gender.getDNA(stack) == FishyHelper.MALE) {
                    if (!mergeItemStack(stack, 5, 6, false)) return null;
                } else if (stack.getItem() instanceof ItemFishy && Fish.gender.getDNA(stack) == FishyHelper.FEMALE) {
                    if (!mergeItemStack(stack, 6, 7, false)) return null;
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null;
                } else if (FluidHelper.isFluidOrEmpty(stack) || FishFoodHandler.isFishFood(stack)) {
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

    private class SlotFather extends Slot {
        public SlotFather(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return Fishing.fishHelper.isMale(stack);
        }
    }

    private class SlotMother extends Slot {
        public SlotMother(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return Fishing.fishHelper.isFemale(stack);
        }
    }
}
