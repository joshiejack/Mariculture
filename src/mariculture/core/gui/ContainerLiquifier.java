package mariculture.core.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.ContainerInteger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ContainerLiquifier extends ContainerInteger {
	private TileLiquifier tile;

	public ContainerLiquifier(TileLiquifier tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		for (int i = 0; i < 2; i++) {
			addSlotToContainer(new Slot(tile, i, 21 + (i * 18), 17));
		}

		addSlotToContainer(new SlotFuel(tile, 2, 30, 55));
		addSlotToContainer(new SlotOutput(tile, 3, 110, 8));
		addSlotToContainer(new SlotFluidContainer(tile, 4, 110, 31));
		addSlotToContainer(new SlotOutput(tile, 5, 110, 62));

		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade(tile, i + 6, 148, 16 + (i * 18)));
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
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (EntityPlayer) crafters.get(i));
		}

	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		tile.getGUINetworkData(par1, par2);
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
				if (MaricultureHandlers.smelter.getResult(stack, -1) != null) {
					if (!this.mergeItemStack(stack, 0, 2, false)) { // Slot 0-1
						return null;
					}
				} else if (MaricultureHandlers.smelter.getBurnTemp(stack, false) > 0) {
					if (!this.mergeItemStack(stack, 2, 3, false)) { // Slot 2-2
						return null;
					}
				} else if (FluidHelper.isFluidOrEmpty(stack)) {
					if (!this.mergeItemStack(stack, 4, 5, false)) { // Slot 4-4
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 6, 9, false)) { // Slot 6-8
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