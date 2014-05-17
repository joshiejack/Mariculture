package mariculture.fishery.gui;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerScanner extends ContainerStorage {
	public ContainerScanner(IInventory inventory, InventoryStorage storage, World world) {
		super(inventory, storage, world, 30);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = storage.getSizeInventory();
		int low = size + 27;
		int high = low + 9;
		ItemStack newStack = null;
		final Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			newStack = stack.copy();

			if (slotID < size) {
				if (!this.mergeItemStack(stack, size, high, true)) {
					return null;
				}
			} else {
				if (!(stack.getItem() instanceof ItemStorage) && ((ItemStorage)player.getCurrentEquippedItem().getItem()).isItemValid(stack)) {
					if (!this.mergeItemStack(stack, 0, storage.getSizeInventory(), false)) {
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high && !this.mergeItemStack(stack, size, low, false)) {
					return null;
				}
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == newStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack);
		}

		return newStack;
	}
}
