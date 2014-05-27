package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.StackHelper;
import mariculture.core.lib.Compatibility;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//This handles all the OreDictionaryRelated things to do with the Automatic Dictionary Converter
public class OreDicHandler {
	public static HashMap<String, ArrayList<String>> all;
	public static HashMap<String, ArrayList<String>> entries;
	public static HashMap<String, Integer[]> specials = new HashMap();
	public static HashMap<String, ArrayList<ItemStack>> items;
	public static HashMap<String, String> remappings = new HashMap();
	
	public static void init() {
		remappings.put("limestone", "blockLimestone");
		remappings.put("glass", "blockGlass");
		
		registerWildcard(new ItemStack(Blocks.planks), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.wooden_slab), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.log), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Blocks.log2), new Integer[] { 0, 1 });
		registerWildcard(new ItemStack(Blocks.sapling), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.leaves), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Blocks.leaves2), new Integer[] { 0, 1 });
	}
	
	@SubscribeEvent
	public void onOreDictionaryRegistration(OreRegisterEvent event) {
		//Initialize all existing Entries
		if(entries == null) {
			all = new HashMap();
			items = new HashMap();
			entries = new HashMap();
			String[] ores = OreDictionary.getOreNames();
			for(String ore: ores) {
				ArrayList<ItemStack> stacks = OreDictionary.getOres(ore);
				for(ItemStack stack: stacks) {
					if(stack != null && ore != null && !ore.equals("")) {
						if(stack.getItem() == null || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
							addSpecial(stack, ore);
						} else {
							add(stack, ore);
						}
					}
				}
			}
		}
		
		if(event.Ore != null && !event.Name.equals("")) {
			if(event.Ore.getItemDamage() == OreDictionary.WILDCARD_VALUE) addSpecial(event.Ore, event.Name);
			else add(event.Ore, event.Name);
		} else if(event.Ore == null) {
			LogHandler.log(Level.ERROR, "A modder has been very silly and attempted to register a null item to the ore dictionary");
			new Exception().printStackTrace();
		} else {
			LogHandler.log(Level.ERROR, "A modder has been very silly and attempted to register an item with a blank name to the ore dictionary!");
			new Exception().printStackTrace();
		}
	}
	
	//Re-Register blocks as blockLimestone and blockGlass
	public static void syncOreDictionary(ItemStack stack, String string) {
		if(remappings.containsKey(string)) {
			OreDictionary.registerOre(remappings.get(string), stack);
		}
	}
	
	public static void registerWildcard(ItemStack stack, Integer[] metas) {
		if(stack == null || stack.getItem() == null || metas == null) return;
	//Get the wildcard and add it
		String name = Item.itemRegistry.getNameForObject(stack.getItem());
		specials.put(name, metas);
		LogHandler.log(Level.INFO, "Successfully registered wildcard for " + name + "(" + stack.toString() + ")");
	}
	
	private void addSpecial(ItemStack stack, String ore) {
		String name = Item.itemRegistry.getNameForObject(stack.getItem());
		Integer[] meta = specials.get(name);
		if(meta == null) return;
		for(Integer i: meta) {
			add(new ItemStack(stack.getItem(), 1, i), ore);
		}
	}

	public static void add(ItemStack stack, String name) {
		String id = convert(stack);
		//All
		ArrayList<String> alls = all.containsKey(id)? all.get(id): new ArrayList();
		alls.add(name);
		all.put(id, alls);
		
		if(!isWhitelisted(name)) return;
		for(String str: Compatibility.BLACKLIST_PREFIX_DEFAULT) {
			if(name.startsWith(str)) return;
		}
		
		for(String str: Compatibility.BLACKLIST_ITEMS) {
			ItemStack check = StackHelper.getStackFromString(str);
			if(check != null) {
				if(check.getItem() == stack.getItem() && check.getItemDamage() == stack.getItemDamage()) return;
			}
		}
		
		//Entries
		ArrayList<String> list = entries.containsKey(id)? entries.get(id): new ArrayList();
		list.add(name);
		entries.put(id, list);
		//Items
		ArrayList<ItemStack> stacks = items.get(name) != null? items.get(name): new ArrayList();
		stacks.add(stack);
		items.put(name, stacks);
		
		//After adding to the ore dictionary list, add entries for similar things
		syncOreDictionary(stack, name);
	}
	
	public static String convert(ItemStack stack) {
		try {
			return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		} catch (Exception e) {
			try {
				return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + OreDictionary.WILDCARD_VALUE;
			} catch (Exception e2) {
				return "";
			}
		}
	}
	
	public static boolean isInDictionary(ItemStack stack) {
		return OreDictionary.getOreID(stack) > 0;
	}
	
	public static boolean areEqual(ItemStack stack1, ItemStack stack2) {
		if(!isInDictionary(stack1) || !isInDictionary(stack2)) return false;
		ArrayList<String> oreNames1 = entries.get(convert(stack1)); 
		ArrayList<String> oreNames2 = entries.get(convert(stack2));
		if(oreNames1 == null || oreNames2 == null) return false;
		
		for(String ores1: oreNames1) {
			for(String ores2: oreNames2) {
				if(ores1.equals(ores2)) return true;
			}
		}
		
		return false;
	}
	
	public static boolean areEqual(ItemStack stack, String item) {
		if(!isInDictionary(stack)) return false;
		ArrayList<String> names = entries.get(convert(stack)); 
		for(String name: names) {
			if(name.equals(item)) return true;
		}
		
		return false;
	}

	public static boolean isWhitelisted(String name) {
		if(Compatibility.ENABLE_WHITELIST) {
			for(String whitelist: Compatibility.WHITELIST) {
				if(name.startsWith(whitelist)) return true;
			}
				
			return false;
		} else {
			for(String blacklist: Compatibility.BLACKLIST) {
				if(name.equals(blacklist)) return false;
			}
				
			return true;
		}
	}

	public static ItemStack getNextValidEntry(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag != null && tag.hasKey("OreDictionaryDisplay")) {
			NBTTagCompound display = tag.getCompoundTag("OreDictionaryDisplay");
			NBTTagList lore = display.getTagList("Lore", 8);
			String old = lore.getStringTagAt(0) != null? lore.getStringTagAt(0): "";
			if(!old.equals("")) {
				return getNextValidEntry(stack, old);
			}
		}
		
		return getNextValidEntry(stack, OreDicHelper.getDictionaryName(stack));
	}

	private static ItemStack getNextValidEntry(ItemStack stack, String name) {
		String converted = convert(stack);
		boolean found = false;
		ArrayList<ItemStack> stacks = items.get(name);
		if(stacks == null || stacks.size() < 1) return stack;
		for(ItemStack item: stacks) {
			if(found && item != null && !convert(item).equals(converted)) {
				ItemStack ret = item.copy();
				if(!ret.hasTagCompound()) ret.setTagCompound(new NBTTagCompound());
				if(!ret.stackTagCompound.hasKey("OreDictionaryDisplay")) ret.stackTagCompound.setTag("OreDictionaryDisplay", new NBTTagCompound());
				ret.stackTagCompound.getCompoundTag("OreDictionaryDisplay").setTag("Lore", OreDicHandler.addAllTags(ret, name));
				return ret;
			}
			
			found = convert(item).equals(converted);
		}
		
		ItemStack ret = stacks.get(0).copy();
		if(!ret.hasTagCompound()) ret.setTagCompound(new NBTTagCompound());
		if(!ret.stackTagCompound.hasKey("OreDictionaryDisplay")) ret.stackTagCompound.setTag("OreDictionaryDisplay", new NBTTagCompound());
		ret.stackTagCompound.getCompoundTag("OreDictionaryDisplay").setTag("Lore", OreDicHandler.addAllTags(ret, name));
		return ret;
	}

	public static String getNextString(ItemStack stack, String name) {
		boolean found = false;
		ArrayList<String> names = entries.get(convert(stack));
		if(names == null) return name;
		for(String entry: names) {
			if(found && !entry.equals(name)) return entry;
			found = entry.equals(name);
		}
		
		return names.get(0);
	}

	public static NBTTagList addAllTags(ItemStack stack, String name) {
		NBTTagList lore = new NBTTagList();
		lore.appendTag(new NBTTagString(name));
		
		String last = "";
		ArrayList<String> names = entries.get(convert(stack));
		if(names == null) return lore;
		for(String entry: names) {
			if(!entry.equals(name) && !last.equals(entry)) {
				lore.appendTag(new NBTTagString(entry));
				last = entry;
			}
		}
		
		return lore;
	}
}
