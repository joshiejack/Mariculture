package mariculture.fishery.blocks;

import mariculture.core.blocks.base.TileStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileSift extends TileStorage implements ISidedInventory {

	public TileSift() {
		inventory = new ItemStack[10];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
    }

	public int getSuitableSlot(ItemStack item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].itemID == item.itemID && (inventory[i].stackSize + item.stackSize) <= inventory[i]
					.getMaxStackSize())) {
				return i;
			}
		}

		return 10;
	}

	private static final int[] slots_all = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_all;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return this.blockMetadata > 1;
	}
}
