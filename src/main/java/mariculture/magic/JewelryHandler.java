package mariculture.magic;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.PearlColor;
import mariculture.magic.jewelry.ItemBracelet;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import mariculture.magic.jewelry.ItemNecklace;
import mariculture.magic.jewelry.ItemRing;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class JewelryHandler {	
	public static void addJewelry(JewelryType type, ItemStack result, ItemStack binding, ItemStack material, int hits) {
		if(type == JewelryType.RING) addRing(result, binding, material, hits);
		else if(type == JewelryType.BRACELET) addBracelet(result, binding, material, hits);
		else if(type == JewelryType.NECKLACE) addNecklace(result, binding, material, hits);
	}

	public static void addRing(ItemStack result, ItemStack binding, ItemStack material, int hits) {				
		//Anvil Recipes
		CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { 
			"ABA", "A A", "AAA", 'A', binding, 'B', material 
		}));
		
		//Swap Jewelry Recipe - Allow the swapping of jewelry pieces
		CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits/3), new Object[] { 
				material, result 
		}));
	}

	public static void addBracelet(ItemStack result, ItemStack binding, ItemStack material, int hits) {		
		//Anvil Recipes
		CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { 
			"A A", "B B", " B ", 'A', binding, 'B', material 
		}));
		
		//Swap Jewelry Recipe - Allow the swapping of jewelry pieces
		CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits/3), new Object[] { 
				material, result 
		}));
	}

	public static void addNecklace(ItemStack result, ItemStack binding, ItemStack material, int hits) {		
		//Anvil Recipes
		CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { 
			"BAB", "B B", "BBB", 'A', binding, 'B', material 
		}));
		
		//Swap Jewelry Recipe - Allow the swapping of jewelry pieces
		CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits/3), new Object[] { 
				material, result 
		}));
	}
	
	//Returns the jewelry binding this item has
	public static JewelryBinding getBinding(ItemStack stack) {
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(JewelryBinding.nbt)) {
			return JewelryBinding.list.get(stack.stackTagCompound.getString(JewelryBinding.nbt));
		} else return Magic.dummyBinding;
	}
	
	//Returns the material this item has
	public static JewelryMaterial getMaterial(ItemStack stack) {
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey(JewelryMaterial.nbt)) {
			return JewelryMaterial.list.get(stack.stackTagCompound.getString(JewelryMaterial.nbt));
		} else return Magic.dummyMaterial;
	}
	
	//Returns the item type based
	public static JewelryType getType(ItemStack stack) {
		return (stack.getItem() instanceof ItemJewelry)?((ItemJewelry)stack.getItem()).getType() : JewelryType.NONE;
	}
	
	//Returns the maximum level this item can go
	public static int getMaxLevel(ItemStack stack) {
		return (stack.getItem() instanceof ItemJewelry)?((ItemJewelry)stack.getItem()).getMaxLevel() : -1;
	}
	
	//Max durability
	public static int getMaxDurability(ItemStack stack) {
		return (stack.getItem() instanceof ItemJewelry)?((ItemJewelry)stack.getItem()).getMaxLevel() : -1;
	}
	
	public static enum SettingType {
		LEVEL, USED_LEVEL, DURABILITY, XP;
		
		public String getName() {
			return name().toLowerCase();
		}
	}
	
	//Creates a 'special' piece of jewelry
	public static ItemStack createSpecial(ItemJewelry item, JewelryType accepted, String special) {
		ItemStack stack = new ItemStack(item);
		if(getType(stack) == accepted) {
			stack.setTagCompound(new NBTTagCompound());
			stack.stackTagCompound.setString("Specials", special);
			stack.stackTagCompound.setInteger(SettingType.LEVEL.getName(), 0);
			stack.stackTagCompound.setInteger(SettingType.USED_LEVEL.getName(), 0);
			stack.stackTagCompound.setInteger(SettingType.DURABILITY.getName(), 0);
			stack.stackTagCompound.setInteger(SettingType.XP.getName(), 0);
			return stack;
		} else return null;
	}
	
	//Creates a 'new' piece of jewelry
	public static ItemStack createNewJewelry(ItemJewelry item, JewelryBinding binding, JewelryMaterial material) {
		return createJewelry(item,  binding, material, new Integer[] { 0, 0, 0, 0 });
	}
	
	//Crates the 'best' version of this jewelry
	public static ItemStack createBestJewelry(ItemJewelry item, JewelryBinding binding, JewelryMaterial material) {
		return createJewelry(item, binding, material, new Integer[] { item.getMaxLevel(), 0, item.getMaxDurability(), 0 });
	}
	
	//Creates a piece of jewelry with these specific settings
	public static ItemStack createJewelry(ItemJewelry item, JewelryBinding binding, JewelryMaterial material, Integer[] settings) {
		ItemStack stack = new ItemStack(item);
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setString(JewelryBinding.nbt, binding.getIdentifier());
		stack.stackTagCompound.setString(JewelryMaterial.nbt, material.getIdentifier());
		stack.stackTagCompound.setInteger(SettingType.LEVEL.getName(), settings[0]);
		stack.stackTagCompound.setInteger(SettingType.USED_LEVEL.getName(), settings[1]);
		stack.stackTagCompound.setInteger(SettingType.DURABILITY.getName(), settings[2]);
		stack.stackTagCompound.setInteger(SettingType.XP.getName(), settings[3]);
		return stack;
	}
	
	//Sets the jewelry level
	private static ItemStack setSetting(ItemStack stack, int level, SettingType type) {
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setInteger(type.getName(), level);
		return stack;
	}
	
	//Adjusts the jewelry levels
	public static void adjustSetting(ItemStack stack, int mod, SettingType type) {
		int adjust = getSetting(stack, type) + mod;
		int max = (type == SettingType.LEVEL)? getMaxLevel(stack): getMaxDurability(stack);
		if(type != SettingType.USED_LEVEL) {
			if(adjust >= max) adjust = max;
		}
		
		//if xp, check if it's above the leveling threshold if so, then increase the level
		if(type == SettingType.XP) {
			if(adjust >= 100) {
				adjustSetting(stack, +1, SettingType.LEVEL);
				adjust -= 100;
			}
		}
		
		setSetting(stack, adjust, type);
	}
	
	//Returns the level this jewelry is on
	public static int getSetting(ItemStack stack, SettingType type) {
		return stack.hasTagCompound()? stack.stackTagCompound.getInteger(type.getName()): 0;
	}

	public static ItemStack finishJewelry(ItemStack stack, ItemStack result, Random rand) {
		if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("EnchantmentList") && stack.stackTagCompound.hasKey("EnchantmentLevels")) {
			JewelryType type = getType(result);
			JewelryBinding binding = getBinding(result);
			int chance = binding.getKeepEnchantmentChance(type);
			int[] enchants = stack.stackTagCompound.getIntArray("EnchantmentList");
			int[] levels = stack.stackTagCompound.getIntArray("EnchantmentLevels");		
			int total = 0;
			for(int j = 0; j < enchants.length; j++) {
				if(rand.nextInt(100) < chance) {
					if(total < type.getMaximumEnchantments()) {
						total++;
					//Add the enchantment after increasing the enchants added
						Enchantment enchant = Enchantment.enchantmentsList[enchants[j]];
						int level = Math.min(levels[j], binding.getMaxEnchantmentLevel(type));
						int current = EnchantmentHelper.getEnchantmentLevel(enchant.effectId, result);
						if(current < enchant.getMaxLevel()) {
							int newLevel = Math.min(current + level, enchant.getMaxLevel());
							if(current != 0) {
						        NBTTagList tagList = result.stackTagCompound.getTagList("ench", 10);
						        for (int i = 0; i < tagList.tagCount(); i++) {
									NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
									int id = tag.getShort("id");
									if(id == enchant.effectId) {
										tag.setShort("lvl", (short) newLevel);
										tagList.removeTag(i);
										tagList.appendTag(tag);
									}
								}
							} else {
								result.addEnchantment(enchant, level);
							}
						}
					}
				}
			}
		}
		
		return result;
	}
}
