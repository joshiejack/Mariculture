package mariculture.fishery.gui;

import mariculture.core.gui.ContainerMariculture;
import mariculture.fishery.tile.TileSifter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSift extends ContainerMariculture {
	private TileSifter tile;
	public ContainerSift(TileSifter tile, InventoryPlayer playerInventory) {
		super(tile);
		this.tile = tile;

		for (int i = 0; i <= 4; i++) {
			addSlotToContainer(new Slot(tile, i, 42 + (i * 18), 26));
		}

		for (int i = 5; i <= 9; i++) {
			addSlotToContainer(new Slot(tile, i, 42 + ((i - 5) * 18), 44));
		}

		bindPlayerInventory(playerInventory);
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