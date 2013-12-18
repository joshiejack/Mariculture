package mariculture.core.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileSettler;
import mariculture.core.handlers.SettlerRecipeHandler;
import mariculture.core.helpers.FluidHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSettler extends ContainerMariculture {
	private TileSettler tile;

	public ContainerSettler(TileSettler tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		for (int i = 0; i < 2; i++) {
			addSlotToContainer(new SlotOutput(tile, i, 110, 28 + (i * 18)));
		}

		addSlotToContainer(new Slot(tile, 2, 14, 8));
		addSlotToContainer(new SlotFluidContainer(tile, 3, 14, 31));
		addSlotToContainer(new SlotOutput(tile, 4, 14, 62));

		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade(tile, i + 5, 148, 16 + (i * 18)));
		}

		bindPlayerInventory(playerInventory);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (EntityPlayer) crafters.get(i));
		}

	}

	@Override
	public void updateProgressBar(final int par1, final int par2) {
		tile.getGUINetworkData(par1, par2);
	}

	@Override
	public boolean canInteractWith(final EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = tile.getSizeInventory();
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
				if (MaricultureHandlers.freezer.getResult(stack, null) != null) {
					if (!this.mergeItemStack(stack, 2, 3, false)) { // Slot 2-2
						return null;
					}
				} else if (FluidHelper.isFluidOrEmpty(stack)) {
					if (!this.mergeItemStack(stack, 3, 4, false)) { // Slot 3-3
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 5, 8, false)) { // Slot 5-7
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
