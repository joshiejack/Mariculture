package mariculture.core.helpers;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import mariculture.core.lib.Compatibility;
import mariculture.core.util.Rand;
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
	
	public static ItemStack getNextValidEntry(ItemStack stack) {
		return getNextOreDicEntry(stack, true);
	}
	
	public static ItemStack getNextEntry(ItemStack stack) {
		return getNextOreDicEntry(stack, false);
	}
	
	public static ItemStack getNextOreDicEntry(ItemStack stack, boolean checkWhitelist) {
		if (OreDicHelper.isInDictionary(stack)) {
			if ((OreDicHelper.isWhitelisted(stack) && checkWhitelist) || !checkWhitelist) {
				String name = OreDicHelper.getDictionaryName(stack);
				int id = getOrePos(stack);
				if (OreDictionary.getOres(name).size() > 0) {
					id++;
					
					if (id >= OreDictionary.getOres(name).size()) {
						if(checkWhitelist) {
							ItemStack check = checkException(stack, name);
							stack = (check != null) ? check : stack;
						}

						id = 0;
					}
				}
				
				stack = OreDictionary.getOres(OreDicHelper.getDictionaryName(stack)).get(id).copy();
				
				if(!checkWhitelist) {
					if(stack.itemID == Block.planks.blockID || stack.itemID == Block.wood.blockID || stack.itemID == Block.woodSingleSlab.blockID) {
						stack.setItemDamage(Rand.rand.nextInt(4));
					}
				}
				
				return stack;
			}
		}
		
		return stack;
	}
	
	private static int getOrePos(ItemStack input) {
		int id = 0;
		String name = OreDicHelper.getDictionaryName(input);
		ArrayList<ItemStack> ores = OreDictionary.getOres(name);
		for (ItemStack item : ores) {
			if (OreDictionary.itemMatches(item, input, true)) {
				return id;
			}

			id++;
		}

		return id;
	}
	
	private static ItemStack checkException(ItemStack stack, String name) {
		if (!OreDicHelper.isWhitelisted(stack)) {
			return null;
		}

		for (int i = 0; i < Compatibility.EXCEPTIONS.length; i++) {
			String[] names = Compatibility.EXCEPTIONS[i].split("\\s*:\\s*");
			if (names[0].equals(name)) {
				return (OreDictionary.getOres(names[1]).size() > 0) ? OreDictionary.getOres(names[1]).get(0) : null;
			} else if (names[1].equals(name)) {
				return (OreDictionary.getOres(names[0]).size() > 0) ? OreDictionary.getOres(names[0]).get(0) : null;
			}
		}

		return null;
	}
}
