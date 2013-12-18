package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Stack {
	//Blocks
	public static final Stack sluice = new Stack(Core.utilBlocks, UtilMeta.SLUICE);
	public static final Stack tank = new Stack(Core.tankBlocks, TankMeta.TANK);
	
	//Crafting Items
	public static final Stack wheel = new Stack(Core.craftingItem, CraftingMeta.WHEEL);
	public static final Stack titaniumSheet = new Stack(Core.craftingItem, CraftingMeta.TITANIUM_SHEET);
	
	//Food
	public static final Stack calamari = new Stack(Core.food, FoodMeta.CALAMARI);
	
	//Raw Fish
	public static final Stack squid = new Stack(Fishery.fishyFood, Fishery.squid.fishID);
	
	public ItemStack stack;
	public Stack(Item item, int meta) {
		this.stack = new ItemStack(item, 1, meta);
	}
	
	public Stack(Block block, int meta) {
		this.stack = new ItemStack(block, 1, meta);
	}
	
	public ItemStack get() {
		return this.stack.copy();
	}
	
	public ItemStack get(int size) {
		ItemStack itemStack = stack.copy();
		itemStack.stackSize = size;
		return itemStack;
	}
}
