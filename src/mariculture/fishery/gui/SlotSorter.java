package mariculture.fishery.gui;

import mariculture.core.gui.SlotFake;
import mariculture.factory.blocks.TileFishSorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSorter extends SlotFake {
	IInventory tile;

	public SlotSorter(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
		this.tile = inv;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		if (this.getHasStack()) {
			Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}

	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {
		if (mouseButton > 0) {
			slot.putStack(null);
		} else if (mouseButton == 0) {
			ItemStack stack;
			InventoryPlayer playerInv = player.inventory;
			slot.onSlotChanged();
			ItemStack stackSlot = slot.getStack();
			ItemStack stackHeld = playerInv.getItemStack();

			if (stackSlot == null && stackHeld != null && slot.slotNumber != 22) {
				if (isItemValid(stackHeld)) {
					ItemStack copy = stackHeld.copy();
					copy.stackSize = 1;
					slot.putStack(copy);
				}
			}

			if (stackHeld == null) {
				if (tile instanceof TileFishSorter) {
					if(slot.slotNumber == 22)
						((TileFishSorter) tile).increaseDFT();
					else
						((TileFishSorter) tile).increaseSide(slot.slotNumber);
				}
			}
		}

		return null;
	}
}