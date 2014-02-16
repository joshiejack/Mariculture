package mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerHelper {
	public static ItemStack getArmor(EntityPlayer player, int slot, Item item) {
		ItemStack[] armor = player.inventory.armorInventory;

		if (slot > -1 && slot < 4) {
			if (armor[slot] != null) {
				if (armor[slot].getItem() == item) {
					return armor[slot];
				}
			}
		}
		
		return null;
	}
	
	public static Item getArmor(EntityPlayer player, int slot) {
		ItemStack[] armor = player.inventory.armorInventory;

		if (slot > -1 && slot < 4) {
			if (armor[slot] != null) {
				return armor[slot].getItem();
			}
		}
		
		return null;
	}
	
	public static boolean hasArmor(EntityPlayer player, int slot, Item item) {
		return getArmor(player, slot, item) != null;
	}

	public static int hasItem(EntityPlayer player, ItemStack match, boolean damageable) {
		for(int i = 0; i < player.inventory.mainInventory.length; i++) {
			ItemStack stack = player.inventory.mainInventory[i];
			if(stack != null) {
				if(stack.getItem() == match.getItem() && (stack.getItemDamage() == match.getItemDamage() || damageable)) {
					return i;
				}
			}
		}

		return -1;
	}
}
