package mariculture.core.gui;

import mariculture.fishery.blocks.TileSift;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSift extends Container {
	private TileSift tile;

	public ContainerSift(TileSift tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		for (int i = 0; i <= 4; i++) {
			addSlotToContainer(new Slot(tile, i, 42 + (i * 18), 26));
		}

		for (int i = 5; i <= 9; i++) {
			addSlotToContainer(new Slot(tile, i, 42 + ((i - 5) * 18), 44));
		}

		bindPlayerInventory(playerInventory);
	}

	private void bindPlayerInventory(final InventoryPlayer playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			stack = itemstack1.copy();

			if (par2 < 10) {
				if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 10, false)) {
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