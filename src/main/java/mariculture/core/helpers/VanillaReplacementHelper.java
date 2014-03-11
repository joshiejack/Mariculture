package mariculture.core.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import mariculture.core.handlers.LogHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;

import org.apache.logging.log4j.Level;

import com.google.common.collect.BiMap;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;

public class VanillaReplacementHelper {	
	//Code for blocks originally by chylex, adapted to work for items.
	public static boolean replaceItem(Item toReplace, Class<?extends Item> itemClass) {
		Field modifiersField = null;
		try {
			modifiersField=Field.class.getDeclaredField("modifiers");
    		modifiersField.setAccessible(true);
    		
    		for(Field field: Items.class.getDeclaredFields()) {
        		if (Item.class.isAssignableFrom(field.getType())) {
        			Item item = (Item)field.get(null);
    				if (item == toReplace){
    					String registryName = Item.itemRegistry.getNameForObject(item);
    					int id = Item.getIdFromItem(item);
    					LogHandler.log(Level.TRACE, "Replacing item - " + id + "/" + registryName);
    					
    					Item newItem = itemClass.newInstance();
    					FMLControlledNamespacedRegistry<Item> registry = GameData.itemRegistry;
    					registry.putObject(registryName, newItem);
    					
    					Field map = RegistryNamespaced.class.getDeclaredFields()[0];
    					map.setAccessible(true);
    					((ObjectIntIdentityMap)map.get(registry)).func_148746_a(newItem, id);
    					
    					map = FMLControlledNamespacedRegistry.class.getDeclaredField("namedIds");
    					map.setAccessible(true);
    					((BiMap)map.get(registry)).put(registryName, id);
    					
    					field.setAccessible(true);
    					int modifiers = modifiersField.getInt(field);
    					modifiers&=~Modifier.FINAL;
    					modifiersField.setInt(field, modifiers);
    					field.set(null, newItem);

    					LogHandler.log(Level.TRACE, "Check field: " + field.get(null).getClass());
						LogHandler.log(Level.TRACE, "Check item: "+(Item.itemRegistry.getObjectById(id)).getClass());
    				}
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//Code by chylex
	public static boolean replaceBlock(Block toReplace, Class<? extends Block> blockClass) {
		Field modifiersField=null;
    	try {
    		modifiersField=Field.class.getDeclaredField("modifiers");
    		modifiersField.setAccessible(true);
    		
    		for(Field field: Blocks.class.getDeclaredFields()) {
        		if (Block.class.isAssignableFrom(field.getType())) {
    				Block block = (Block)field.get(null);
    				if (block == toReplace){
    					String registryName = Block.blockRegistry.getNameForObject(block);
    					int id = Block.getIdFromBlock(block);
    					ItemBlock item = (ItemBlock)Item.getItemFromBlock(block);
    					LogHandler.log(Level.TRACE, "Replacing block - " + id + "/" + registryName);
    					
    					Block newBlock = blockClass.newInstance();
    					FMLControlledNamespacedRegistry<Block> registry = GameData.blockRegistry;
    					registry.putObject(registryName, newBlock);
    					
    					Field map = RegistryNamespaced.class.getDeclaredFields()[0];
    					map.setAccessible(true);
    					((ObjectIntIdentityMap)map.get(registry)).func_148746_a(newBlock, id);
    					
    					map=FMLControlledNamespacedRegistry.class.getDeclaredField("namedIds");
    					map.setAccessible(true);
    					((BiMap)map.get(registry)).put(registryName, id);
    					
    					field.setAccessible(true);
    					int modifiers = modifiersField.getInt(field);
    					modifiers&=~Modifier.FINAL;
    					modifiersField.setInt(field, modifiers);
    					field.set(null, newBlock);
    					
    					Field itemblock=ItemBlock.class.getDeclaredFields()[0];
    					itemblock.setAccessible(true);
    					modifiers = modifiersField.getInt(itemblock);
    					modifiers&=~Modifier.FINAL;
    					modifiersField.setInt(itemblock, modifiers);
    					itemblock.set(item,newBlock);
    					
    					LogHandler.log(Level.TRACE, "Check field: " + field.get(null).getClass());
						LogHandler.log(Level.TRACE, "Check registry: "+Block.blockRegistry.getObjectById(id).getClass());
						LogHandler.log(Level.TRACE, "Check item: "+((ItemBlock)Item.getItemFromBlock(newBlock)).field_150939_a.getClass());
    				}
        		}
        	}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    	
    	return true;
	}
}
