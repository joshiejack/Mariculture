package mariculture.fishery.gui;

import cofh.api.energy.IEnergyContainerItem;
import mariculture.api.core.IItemUpgrade;
import mariculture.api.fishery.Fishing;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotOutput;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
						|| stack.itemID == Item.egg.itemID || stack.itemID == Block.dragonEgg.blockID) {
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
			} else if (stack.itemID == Item.egg.itemID) {
				return true;
			} else if (stack.itemID == Block.dragonEgg.blockID) {
				return true;
			}

			return false;
		}
	}
}

/*
public class ContainerIncubator extends Container {
	private TileIncubator tile;

	public ContainerIncubator(TileIncubator tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotFishEgg(tile, i + (j * 3), 16 + ((i) * 18), 17 + (j * 18)));
			}
		}

		for (int i = 9; i < 12; i++) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotFishEgg(tile, i + (j * 3), 102 + ((i - 9) * 18), 17 + (j * 18)));
			}
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
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (ICrafting) crafters.get(i));
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
				if ((stack.getItem() instanceof ItemFishy && Fishing.fishHelper.isEgg(stack))
						|| stack.itemID == Item.egg.itemID || stack.itemID == Block.dragonEgg.blockID) {
					if (!this.mergeItemStack(stack, 0, 9, false)) { // Slot 0-8
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
		private int field_75228_b;

		public SlotFishEgg(IInventory inventory, int par2, int par3, int par4) {
			super(inventory, par2, par3, par4);
		}

		@Override
		public boolean isItemValid(ItemStack itemstack) {
			if (itemstack.hasTagCompound()) {
				if (Fishing.fishHelper.isEgg(itemstack)) {
					return true;
				}
			}

			if (itemstack.itemID == Item.egg.itemID) {
				return true;
			}
			
			if (itemstack.itemID == Block.dragonEgg.blockID) {
				return true;
			}

			return false;
		}

		@Override
		public ItemStack decrStackSize(int par1) {
			if (this.getHasStack()) {
				this.field_75228_b += Math.min(par1, this.getStack().stackSize);
			}

			return super.decrStackSize(par1);
		}
	}
} */
