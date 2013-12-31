package mariculture.factory.gui;

import mariculture.core.gui.ContainerMachine;
import mariculture.factory.blocks.TileDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDictionary extends ContainerMachine {
	public ContainerDictionary(TileDictionary tile, InventoryPlayer playerInventory) {
		super(tile);

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotDictionary(tile, i, 8 + (i * 18), 18));
		}

		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				addSlotToContainer(new Slot(tile, 9 + (i + (j * 3)), 12 + (i * 18), 42 + (j * 18)));
			}
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				addSlotToContainer(new Slot(tile, 15 + (i + (j * 3)), 112 + (i * 18), 42 + (j * 18)));
			}
		}

		bindPlayerInventory(playerInventory, 10);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			stack = itemstack1.copy();

			if (par2 < 21) {
				if (!this.mergeItemStack(itemstack1, 21, 57, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 9, 15, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return stack;
	}
}
