package mariculture.factory;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Compatibility;
import mariculture.core.util.Text;
import mariculture.plugins.Plugins;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import enchiridion.api.DisplayRegistry;

//This handles all the OreDictionaryRelated things to do with the Automatic Dictionary Converter
public class OreDicHandler {
	public static HashMap<String, ArrayList<String>> entries;
	public static HashMap<String, Integer[]> specials = new HashMap();
	public static HashMap<String, ArrayList<ItemStack>> items;
	
	public static void registerWildCards() {
		registerWildcard(new ItemStack(Blocks.planks), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.wooden_slab), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.log), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Blocks.log2), new Integer[] { 0, 1 });
		registerWildcard(new ItemStack(Blocks.sapling), new Integer[] { 0, 1, 2, 3, 4, 5 });
		registerWildcard(new ItemStack(Blocks.leaves), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Blocks.leaves2), new Integer[] { 0, 1 });
		
		for(Plugin plugin: Plugins.plugins) {
			plugin.registerWildcards();
		}
	}
	
	@SubscribeEvent
	public void onOreDictionaryRegistration(OreRegisterEvent event) {
		//Initialize all existing Entries
		if(entries == null) {
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
		
		if(event.Ore.getItemDamage() == OreDictionary.WILDCARD_VALUE) addSpecial(event.Ore, event.Name);
		else add(event.Ore, event.Name);
	}
	
	public static void registerWildcard(ItemStack stack, Integer[] metas) {
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
		ArrayList<String> list = entries.containsKey(id)? entries.get(id): new ArrayList();
		list.add(name);
		entries.put(id, list);
		ArrayList<ItemStack> stacks = items.get(name) != null? items.get(name): new ArrayList();
		stacks.add(stack);
		items.put(name, stacks);
		
		//Add Cycling
		try {
			DisplayRegistry.registerOreDictionaryCycling(name);
		} catch (Exception e) {
			e.printStackTrace();
			LogHandler.log(Level.WARN, "Mariculture attempted to add Ore Dictionary Cycling for the guide books but it found the required mod Enchridion was not installed");
		}
	}
	
	public static String convert(ItemStack stack) {
		try {
			return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		} catch (Exception e) {
			return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + OreDictionary.WILDCARD_VALUE;
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

	public static boolean isWhitelisted(ItemStack stack) {
		ArrayList<String> names = entries.get(convert(stack));
		if(names == null) return false;
		for(String name: names) {
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
		
		return false;
	}

	public static ItemStack getNextValidEntry(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag != null && tag.hasKey("display")) {
			NBTTagCompound display = tag.getCompoundTag("display");
			NBTTagList lore = display.getTagList("Lore", 8);
			String old = lore.getStringTagAt(0) != null? lore.getStringTagAt(0): "";
			if(old.startsWith("§")) old = old.substring(2);
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
				if(!ret.stackTagCompound.hasKey("display")) ret.stackTagCompound.setTag("display", new NBTTagCompound());
				ret.stackTagCompound.getCompoundTag("display").setTag("Lore", OreDicHandler.addAllTags(ret, new NBTTagList(), name));
				return ret;
			}
			
			found = convert(item).equals(converted);
		}
		
		ItemStack ret = stacks.get(0).copy();
		if(!ret.hasTagCompound()) ret.setTagCompound(new NBTTagCompound());
		if(!ret.stackTagCompound.hasKey("display")) ret.stackTagCompound.setTag("display", new NBTTagCompound());
		ret.stackTagCompound.getCompoundTag("display").setTag("Lore", OreDicHandler.addAllTags(ret, new NBTTagList(), name));
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

	public static NBTTagList addAllTags(ItemStack stack, NBTTagList lore, String name) {
		lore.appendTag(new NBTTagString(Text.ORANGE + name));
		
		String last = "";
		ArrayList<String> names = entries.get(convert(stack));
		if(names == null) return lore;
		for(String entry: names) {
			if(!entry.equals(name) && !last.equals(entry)) {
				lore.appendTag(new NBTTagString(Text.GREY + entry));
				last = entry;
			}
		}
		
		return lore;
	}
}
