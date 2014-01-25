package mariculture.core.helpers;

import cpw.mods.fml.common.registry.GameRegistry;
import mariculture.core.lib.Compatibility;
import mariculture.fishery.items.ItemFishyFood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDicHelper {
	public static boolean isWhitelisted(ItemStack stack) {
		if(isInDictionary(stack)) {
			String ore = getDictionaryName(stack);
			if(Compatibility.ENABLE_WHITELIST) {
				for (int j = 0; j < Compatibility.WHITELIST.length; j++) {
					if(ore.startsWith(Compatibility.WHITELIST[j])) {
						return true;
					}
				}
				
				return false;
			} else {
				for(int j = 0; j < Compatibility.BLACKLIST.length; j++) {
					if(ore.equals(Compatibility.BLACKLIST[j])) {
						return false;
					}
				}
				
				return true;
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
		String name = "";
		
		ItemStack stack = null;
		Fluid fluid = null;
		
		if(object instanceof Block)
			stack = new ItemStack((Block)object);
		if(object instanceof Item)
			stack = new ItemStack((Item)object);
		if(object instanceof ItemStack)
			stack = (ItemStack) object;
		if(stack != null) {
			if(isInDictionary(stack))
				name = getDictionaryName(stack);
			else if(stack.isItemStackDamageable())
				name = stack.itemID + "|ignore";
			else
				name = stack.itemID + "|" + stack.getItemDamage();
		}
		
		if(object instanceof String)
			name = (String) object;

		return name;
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
