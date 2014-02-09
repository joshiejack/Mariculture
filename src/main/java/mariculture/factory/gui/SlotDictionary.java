package mariculture.factory.gui;

import java.util.ArrayList;

import mariculture.core.gui.SlotFake;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Compatibility;
import mariculture.factory.items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotDictionary extends SlotFake {
	public SlotDictionary(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return OreDicHelper.isWhitelisted(stack) || stack.getItem() instanceof ItemFilter;
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
		boolean isFilter = false;

		ItemStack filterCheck = player.inventory.getItemStack();
		if (filterCheck != null) {
			if (filterCheck.getItem() instanceof ItemFilter) {
				isFilter = true;
			}
		}

		if (mouseButton > 0) {
			slot.putStack(null);
		} else if (mouseButton == 0) {
			ItemStack stack;
			InventoryPlayer playerInv = player.inventory;
			slot.onSlotChanged();
			ItemStack stackSlot = slot.getStack();
			ItemStack stackHeld = playerInv.getItemStack();

			if (stackSlot == null && stackHeld != null) {
				if (isFilter || OreDicHelper.isInDictionary(stackHeld)) {
					if (isFilter || OreDicHelper.isWhitelisted(stackHeld)) {
						ItemStack copy = stackHeld.copy();
						copy.stackSize = 1;
						slot.putStack(copy);
					}
				}
			}

			if (stackSlot != null && stackHeld == null) {
				slot.putStack(OreDicHelper.getNextValidEntry(stackSlot));
			}
		}

		return null;
	}
}