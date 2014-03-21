package mariculture.factory.gui;

import mariculture.core.gui.ContainerMachine;
import mariculture.factory.tile.TileDictionaryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDictionary extends ContainerMachine {
	public ContainerDictionary(TileDictionaryItem tile, InventoryPlayer playerInventory) {
		super(tile);
		
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 6; i++) {
				addSlotToContainer(new SlotDictionary(tile, (i + (j * 6)), 12 + (i * 18), 15 + (j * 18)));
			}
		}
		
		addSlotToContainer(new Slot(tile, 24, 139, 20));
		
		for(int i = 0; i < 2; i ++) {
			for(int j = 0; j < 2; j++) {
				addSlotToContainer(new Slot(tile, 25 + i + (j * 2), 130 + (i * 18), 46 + (j * 18)));
			}
		}

		bindPlayerInventory(playerInventory, 14);
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
				if (!this.mergeItemStack(stack, 24, 25, false)) { // Slot 7-7
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
