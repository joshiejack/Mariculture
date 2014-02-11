package mariculture.fishery.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.fishery.Fishing;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotOutput;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class ContainerIncubator extends ContainerMachine {
	public ContainerIncubator(TileIncubator tile, InventoryPlayer playerInventory) {
		super(tile);
		
		addUpgradeSlots(tile);
		addPowerSlot(tile);
				
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotFishEgg(tile, 4 + i + (j * 3), 31 + ((i) * 18), 23 + (j * 18)));
			}
		}

		for (int i = 9; i < 12; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotOutput(tile, 4 + i + (j * 3), 115 + ((i - 9) * 18), 23 + (j * 18)));
			}
		}

		bindPlayerInventory(playerInventory, 10);
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
				if ((stack.getItem() instanceof ItemFishy && Fishing.fishHelper.isEgg(stack))
						|| stack.itemID == Items.egg.itemID || stack.itemID == Blocks.dragonEgg.blockID) {
					if (!this.mergeItemStack(stack, 4, 13, false)) { // Slot 4-12
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 0, 3, false)) { // Slot 0-2
						return null;
					}
				} else if (stack.getItem() instanceof IEnergyContainerItem) {
					if (!this.mergeItemStack(stack, 3, 4, false)) { // Slot 3-3
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
	
	public class SlotFishEgg extends Slot {
		private EntityPlayer thePlayer;

		public SlotFishEgg(IInventory inventory, int par2, int par3, int par4) {
			super(inventory, par2, par3, par4);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if (stack.hasTagCompound()) {
				if (Fishing.fishHelper.isEgg(stack)) {
					return true;
				}
			} else if (stack.itemID == Items.egg.itemID) {
				return true;
			} else if (stack.itemID == Blocks.dragonEgg.blockID) {
				return true;
			}

			return false;
		}
	}
}
