package mariculture.factory.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotFluidContainer;
import mariculture.core.gui.SlotOutput;
import mariculture.core.helpers.FluidHelper;
import mariculture.factory.items.ItemRotor;
import mariculture.factory.tile.TileTurbineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerTurbine extends ContainerMachine {
	
	public ContainerTurbine(TileTurbineBase tile, InventoryPlayer playerInventory) {
		super(tile);
		addUpgradeSlots(tile);
		addSlotToContainer(new SlotFluidContainer(tile, 3, 149, 25));
		addSlotToContainer(new SlotOutput(tile, 4, 149, 56));
		addSlotToContainer(new Slot(tile, 5, 8, 62));
		addSlotToContainer(new Slot(tile, 6, 58, 40));
		bindPlayerInventory(playerInventory, 10);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = getSizeInventory();
		int low = size + 27;
		int high = low + 9;
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotID < size) {
				if (!this.mergeItemStack(stack, size, high, true)) {
					return null;
				}

				slot.onSlotChange(stack, itemstack);
			} else if (slotID >= size) {
				if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 0, 3, false)) { // Slot 0-2
						return null;
					}
				} else if (FluidHelper.isFluidOrEmpty(stack)) {
					if (!this.mergeItemStack(stack, 3, 4, false)) { // Slot 3-3
						return null;
					}
				} else if (stack.getItem() instanceof IEnergyContainerItem) {
					if (!this.mergeItemStack(stack, 5, 6, false)) { // Slot 5-5
						return null;
					}
				} else if (stack.getItem() instanceof ItemRotor) {
					if (!this.mergeItemStack(stack, 6, 7, false)) { // Slot 6-6
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high
						&& !this.mergeItemStack(stack, high, low, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack, size, high, false)) {
				return null;
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack);
		}

		return itemstack;
	}
}