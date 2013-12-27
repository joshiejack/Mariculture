package mariculture.factory.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotUpgrade;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPressureVessel extends ContainerMachine {
	public ContainerPressureVessel(TilePressureVessel tile, InventoryPlayer playerInventory) {
		super(tile);
		
		addSlotToContainer(new Slot(tile, 0, 34, 34));
		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade(tile, i + 1, 148, 16 + (i * 18)));
		}

		bindPlayerInventory(playerInventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = ((IInventory)tile).getSizeInventory();
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
				if (itemstack.itemID == Factory.fludd.itemID) {
					if (!this.mergeItemStack(stack, 0, 1, false)) { // Slot 0-0
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 1, 4, false)) { // Slot 1-3
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high && !this.mergeItemStack(stack, high, low, false)) {
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