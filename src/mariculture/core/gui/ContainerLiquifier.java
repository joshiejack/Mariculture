package mariculture.core.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.helpers.FluidHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLiquifier extends ContainerMachine {	
	public ContainerLiquifier(TileLiquifier tile, InventoryPlayer inventory) {
		super(tile);

		for (int i = 0; i < 2; i++) {
			addSlotToContainer(new Slot(tile, i, 29 + (i * 18), 21));
		}

		addSlotToContainer(new SlotFuel(tile, 2, 38, 59));
		addSlotToContainer(new SlotOutput(tile, 3, 145, 13));
		addSlotToContainer(new SlotFluidContainer(tile, 4, 145, 36));
		addSlotToContainer(new SlotOutput(tile, 5, 145, 67));

		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade(tile, i + 6, 179, 14 + (i * 18)));
		}

		bindPlayerInventory(inventory, 10);
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