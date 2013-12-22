package mariculture.core.util;

import mariculture.api.core.IUpgradable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrades extends Slot {
	private int slot;
	IUpgradable upgrades;
	
	public SlotUpgrades(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		upgrades = (IUpgradable) inventory;
		slot = index;
	}

	
	public ItemStack getStack() {
		return upgrades.getUpgrades()[slot];
    }
}
