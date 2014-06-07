package mariculture.core.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHelper {
	public static boolean areEqual(ItemStack stack, ItemStack stack2) {
		if(stack == null || stack2 == null) return false;
		else {
			if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
				return stack.getItem() == stack2.getItem();
			} else return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
		}
	}
}
