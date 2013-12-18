package mariculture.fishery.gui;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.gui.SlotOutput;
import mariculture.fishery.blocks.TileAutofisher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAutofisher extends Container {
	private TileAutofisher tile;

	public ContainerAutofisher(TileAutofisher tile, InventoryPlayer playerInventory) {
		this.tile = tile;
		addSlotToContainer(new SlotFishingRod(tile, 0, 34, 12));

		for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				addSlotToContainer(new SlotBait(tile, i + (j * 3), 16 + ((i - 1) * 18), 35 + (j * 18)));
			}
		}

		for (int i = 7; i < 10; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotOutput(tile, i + (j * 3), 102 + ((i - 7) * 18), 17 + (j * 18)));
			}
		}

		bindPlayerInventory(playerInventory);
	}

	private void bindPlayerInventory(InventoryPlayer playerInventory) {
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
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (ICrafting) crafters.get(i));
		}

	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		tile.getGUINetworkData(par1, par2);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
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
				if (stack.getItem() instanceof ItemBaseRod) {
					if (!this.mergeItemStack(stack, 0, 1, false)) { // Slot 0-0
						return null;
					}
				} else if (Fishing.bait.getEffectiveness(stack) != -1) {
					if (!this.mergeItemStack(stack, 1, 7, false)) { // Slot 1-6
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
