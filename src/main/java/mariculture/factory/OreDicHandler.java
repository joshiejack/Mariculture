package mariculture.factory;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Compatibility;
import mariculture.core.util.Text;
import mariculture.factory.gui.SlotDictionary;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//This handles all the OreDictionaryRelated things to do with the Automatic Dictionary Converter
public class OreDicHandler {
	public static HashMap<String, ArrayList<String>> entries;
	
	@SubscribeEvent
	public void onOreDictionaryRegistration(OreRegisterEvent event) {
		//Initialize all existing Entries
		if(entries == null) {
			entries = new HashMap();
			String[] ores = OreDictionary.getOreNames();
			for(String ore: ores) {
				ArrayList<ItemStack> stacks = OreDictionary.getOres(ore);
				for(ItemStack stack: stacks) {
					if(stack != null && ore != null && !ore.equals("")) add(stack, ore);
				}
			}
		}
		
		add(event.Ore, event.Name);
	}
	
	public static void add(ItemStack stack, String name) {
		String id = convert(stack);
		ArrayList<String> list = entries.containsKey(id)? entries.get(id): new ArrayList();
		list.add(name);
		entries.put(id, list);
	}
	
	public static String convert(ItemStack stack) {
		return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
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
		ArrayList<ItemStack> stacks = OreDictionary.getOres(name);
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
