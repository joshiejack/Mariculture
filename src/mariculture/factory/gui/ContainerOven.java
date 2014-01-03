package mariculture.factory.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotFluidContainer;
import mariculture.core.gui.SlotOutput;
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.blocks.BlockItemCustomSlabBase;
import mariculture.factory.blocks.TileOven;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.gui.ContainerSawmill.SlotBlock;
import mariculture.factory.items.ItemPlan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerOven extends ContainerMachine {
	public TileOven tile;
	
	public ContainerOven(TileOven tile, InventoryPlayer playerInventory) {
		super(tile);
		this.tile = tile;

		addUpgradeSlots(tile);
		addSlotToContainer(new SlotFluidContainer(tile, 3, 35, 25));
		addSlotToContainer(new SlotOutput(tile, 4, 35, 56));
		
		for(int i = 5; i <= 8; i++) {
			addSlotToContainer(new Slot(tile, i, 58 + ((i - 5) * 18), 25));
		}
		
		for(int i = 9; i <= 11; i++) {
			addSlotToContainer(new Slot(tile, i, 67 + ((i - 9) * 18), 43));
		}
		
		for(int i = 12; i <= 14; i++) {
			addSlotToContainer(new Slot(tile, i, 147, 22 + ((i - 12) * 18)));
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
				if (stack.getItem() instanceof ItemPlan) {
					if (!this.mergeItemStack(stack, 3, 6, false)) { // Slot 3-5
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 0, 3, false)) { // Slot 0-2
						return null;
					}
				} else if ((stack.getItem() instanceof ItemBlock
						&& !(stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase))
						|| stack.itemID == Item.feather.itemID) {
					if (!this.mergeItemStack(stack, 6, 12, false)) { // Slot
																		// 6-11
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
