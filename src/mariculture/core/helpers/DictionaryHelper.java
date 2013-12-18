package mariculture.core.helpers;

import mariculture.fishery.items.ItemFishyFood;
import mariculture.core.lib.Compatibility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DictionaryHelper {
	public static boolean isWhitelisted(ItemStack stack) {
		if(isInDictionary(stack)) {
			String ore = getDictionaryName(stack);
			for (int j = 0; j < Compatibility.WHITELIST.length; j++) {
				if(ore.startsWith(Compatibility.WHITELIST[j])) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static boolean isInDictionary(ItemStack stack) {
		if(stack.getItem() instanceof ItemFishyFood) {
			return false;
		}
		
		if (OreDictionary.getOreID(stack) >= 0) {	
			return true;
		}

		return false;
	}

	public static String getDictionaryName(ItemStack stack) {
		return OreDictionary.getOreName(OreDictionary.getOreID(stack));
	}

	public static String convert(Object object) {
		String string = "";
		Object output = null;

		if (object instanceof ItemStack) {
			output = (DictionaryHelper.isInDictionary((ItemStack) object)) ? DictionaryHelper
					.getDictionaryName((ItemStack) object) : object;
		}

		if (object instanceof Block) {
			output = (DictionaryHelper.isInDictionary(new ItemStack((Block) object))) ? DictionaryHelper
					.getDictionaryName(new ItemStack((Block) object)) : object;
		}

		if (object instanceof Item) {
			if (!(object instanceof ItemFishyFood)) {
				output = (DictionaryHelper.isInDictionary(new ItemStack((Item) object))) ? DictionaryHelper
						.getDictionaryName(new ItemStack((Item) object)) : object;
			}
		}

		if (output == null) {
			output = object;
		}

		if (output instanceof Block) {
			output = new ItemStack((Block) output);
		}

		if (output instanceof Item) {
			output = new ItemStack((Item) output);
		}

		if (output instanceof ItemStack) {
			ItemStack stack = (ItemStack) output;

			if (!stack.isItemStackDamageable()) {
				string = string + ((ItemStack) output).itemID + ((ItemStack) output).getItemDamage();
			} else {
				string = string + ((ItemStack) output).itemID + 0;
			}
		}

		string = (output instanceof String) ? (String) output : string;

		return string;
	}
	
	public static void add(String name, ItemStack stack) {
		if(OreDictionary.getOres(name).size() < 1) {
			OreDictionary.registerOre(name, stack);
		}
	}

	public static boolean areEqual(ItemStack stack1, ItemStack stack2) {
		if(isInDictionary(stack1) && isInDictionary(stack2)) {
			if(getDictionaryName(stack1).equals(getDictionaryName(stack2))) {
				return true;
			}
			
			if(checkException(getDictionaryName(stack1), getDictionaryName(stack2))) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean checkException(String check, String name) {
		for(int i = 0; i < Compatibility.EXCEPTIONS.length; i++) {
			String[] names = Compatibility.EXCEPTIONS[i].split("\\s*:\\s*");
			if((check.equals(names[0]) && name.equals(names[1])) ||
					(check.equals(names[1]) && name.equals(names[0]))) {
				return true;
			}
		}
		
		return false;
	}
}
