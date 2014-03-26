package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.StackHelper;
import mariculture.core.lib.Compatibility;
import mariculture.core.lib.Extra;
import mariculture.plugins.Plugins;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

//This handles all the OreDictionaryRelated things to do with the Automatic Dictionary Converter
public class OreDicHandler {
	public static final HashMap<String, ArrayList<String>> entries = new HashMap();
	public static final HashMap<String, Integer[]> specials = new HashMap();
	public static final HashMap<String, ArrayList<ItemStack>> items = new HashMap();
	
	public static void registerWildCards() {
		registerWildcard(new ItemStack(Block.planks), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Block.woodSingleSlab), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Block.wood), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Block.sapling), new Integer[] { 0, 1, 2, 3 });
		registerWildcard(new ItemStack(Block.leaves), new Integer[] { 0, 1, 2, 3 });
		
		for(Plugin plugin: Plugins.plugins) {
			plugin.registerWildcards();
		}
	}
	
	@ForgeSubscribe
	public void onOreDictionaryRegistration(OreRegisterEvent event) {
		//Initialize all existing Entries
		if(entries.size() < 1) {
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
		String name = "" + stack.itemID;
		specials.put(name, metas);
		
		LogHandler.log(Level.INFO, "Successfully registered wildcard for " + name + "(" + stack.toString() + ")");
	}
	
	private void addSpecial(ItemStack stack, String ore) {
		String name = "" + stack.itemID;
		Integer[] meta = specials.get(name);
		if(meta == null) return;
		for(Integer i: meta) {
			add(new ItemStack(stack.getItem(), 1, i), ore);
		}
	}

	public static void add(ItemStack stack, String name) {
		if(!isWhitelisted(name)) return;
		for(String str: Compatibility.BLACKLIST_PREFIX_DEFAULT) {
			if(name.startsWith(str)) return;
		}
		
		for(String str: Compatibility.BLACKLIST_ITEMS) {
			ItemStack check = StackHelper.getStackFromString(str);
			if(check != null) {
				if(check.itemID == stack.itemID && check.getItemDamage() == stack.getItemDamage()) return;
			}
		}
		
		String id = convert(stack);
		ArrayList<String> list = entries.containsKey(id)? entries.get(id): new ArrayList();
		list.add(name);
		entries.put(id, list);
		ArrayList<ItemStack> stacks = items.get(name) != null? items.get(name): new ArrayList();
		stacks.add(stack);
		items.put(name, stacks);
		
		if(Extra.DEBUG_ON) {
			LogHandler.log(Level.INFO, stack.toString() + " registered for ore dic handling as " + name);
		}
	}
	
	public static String convert(ItemStack stack) {
		try {
			return stack.itemID + ":" + stack.getItemDamage();
		} catch (Exception e) {
			return stack.itemID + ":" + OreDictionary.WILDCARD_VALUE;
		}
	}
	
	public static boolean isInDictionary(ItemStack stack) {
		return entries.get(convert(stack)) != null;
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
			String old = "";
			NBTTagCompound display = tag.getCompoundTag("OreDictionaryDisplay");
			NBTTagList lore = display.getTagList("Lore");
			if(lore != null && lore.tagCount() > 0) {
				old = ((NBTTagString)lore.tagAt(0)).data;
			}
			
			if(old != null) {
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
		lore.appendTag(new NBTTagString("OreDicName1st", name));
		String last = "";
		ArrayList<String> names = entries.get(convert(stack));
		if(names == null) return lore;
		for(String entry: names) {
			if(!entry.equals(name) && !last.equals(entry)) {
				lore.appendTag(new NBTTagString("OreDicName2nd", entry));
				last = entry;
			}
		}
		
		return lore;
	}
}
