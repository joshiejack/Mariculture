package mariculture.factory.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.SlotOutput;
import mariculture.core.gui.SlotUpgrade;
import mariculture.core.util.ContainerInteger;
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.blocks.BlockItemCustomSlabBase;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.items.ItemPlan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerSawmill extends ContainerInteger {
	private TileSawmill tile;

	public ContainerSawmill(TileSawmill tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		addSlotToContainer(new SlotPlan(tile, 0, 63, 61));
		addSlotToContainer(new SlotOutput(tile, 1, 111, 34));

		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade(tile, i + 2, 148, 16 + (i * 18)));
		}

		addSlotToContainer(new SlotBlock(tile, TileSawmill.TOP, 27, 16));
		addSlotToContainer(new SlotBlock(tile, TileSawmill.NORTH, 9, 34));
		addSlotToContainer(new SlotBlock(tile, TileSawmill.EAST, 27, 34));
		addSlotToContainer(new SlotBlock(tile, TileSawmill.SOUTH, 45, 34));
		addSlotToContainer(new SlotBlock(tile, TileSawmill.WEST, 63, 34));
		addSlotToContainer(new SlotBlock(tile, TileSawmill.BOTTOM, 27, 52));

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
				if (stack.getItem() instanceof ItemPlan) {
					if (!this.mergeItemStack(stack, 0, 1, false)) { // Slot 0-0
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 2, 5, false)) { // Slot 2-4
						return null;
					}
				} else if ((stack.getItem() instanceof ItemBlock
						&& !(stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase))
						|| stack.itemID == Item.feather.itemID) {
					if (!this.mergeItemStack(stack, 5, 11, false)) { // Slot
																		// 5-10
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

	public class SlotBlock extends Slot {
		private EntityPlayer thePlayer;
		private int field_75228_b;

		public SlotBlock(IInventory inv, int id, int x, int y) {
			super(inv, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if (stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase) {
				return false;
			}

			return stack.getItem() instanceof ItemBlock || stack.getItem().itemID == Item.feather.itemID;
		}

		@Override
		public ItemStack decrStackSize(final int par1) {
			if (this.getHasStack()) {
				this.field_75228_b += Math.min(par1, this.getStack().stackSize);
			}

			return super.decrStackSize(par1);
		}
	}

	public class SlotPlan extends Slot {
		private EntityPlayer thePlayer;
		private int field_75228_b;

		public SlotPlan(IInventory inv, int id, int x, int y) {
			super(inv, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() instanceof ItemPlan;
		}

		@Override
		public ItemStack decrStackSize(final int par1) {
			if (this.getHasStack()) {
				this.field_75228_b += Math.min(par1, this.getStack().stackSize);
			}

			return super.decrStackSize(par1);
		}
	}
}
