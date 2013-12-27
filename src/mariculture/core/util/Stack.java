package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.diving.Diving;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Stack {
	//Construct Blocks
	public static final Stack baseBrick = new Stack(Core.oreBlocks, OresMeta.BASE_BRICK);
	
	//Blocks
	public static final Stack crucible = new Stack(Core.utilBlocks, UtilMeta.LIQUIFIER);
	public static final Stack burntAnvil = new Stack(Core.singleBlocks, SingleMeta.ANVIL);
	public static final Stack sluice = new Stack(Core.utilBlocks, UtilMeta.SLUICE);
	public static final Stack tank = new Stack(Core.tankBlocks, TankMeta.TANK);
	public static final Stack bucketTank = new Stack(Core.singleBlocks, SingleMeta.BUCKET);
	public static final Stack forge = new Stack(Core.doubleBlock, DoubleMeta.FORGE);
	public static final Stack compressorBottom = new Stack(Core.doubleBlock, DoubleMeta.AIR_COMPRESSOR);
	public static final Stack compressorTop = new Stack(Core.doubleBlock, DoubleMeta.AIR_COMPRESSOR_POWER);
	
	//Crafting Items
	public static final Stack wheel = new Stack(Core.craftingItem, CraftingMeta.WHEEL);
	public static final Stack aluminumSheet = new Stack(Core.craftingItem, CraftingMeta.ALUMINUM_SHEET);
	public static final Stack titaniumSheet = new Stack(Core.craftingItem, CraftingMeta.TITANIUM_SHEET);
	public static final Stack glassLens = new Stack(Core.craftingItem, CraftingMeta.LENS_GLASS);
	public static final Stack burntBrick = new Stack(Core.craftingItem, CraftingMeta.BURNT_BRICK);
	public static final Stack pearl = new Stack(Core.pearls);
	public static final Stack cooling = new Stack(Core.craftingItem, CraftingMeta.COOLER);
	public static final Stack heating = new Stack(Core.craftingItem, CraftingMeta.HEATER);
	
	//Armor and tools
	public static final Stack snorkel = new Stack(Diving.snorkel);
	public static final Stack hammer = new Stack(Core.hammer);
	
	//Food
	public static final Stack calamari = new Stack(Core.food, FoodMeta.CALAMARI);
	public static final Stack fishMeal = new Stack(Core.materials, MaterialsMeta.FISH_MEAL);
	
	//Raw Fish
	public static final Stack squid = new Stack(Fishery.fishyFood, Fishery.squid.fishID);
	
	public ItemStack stack;
	
	public Stack(Item item) {
		this.stack = new ItemStack(item);
	}
	
	public Stack(Item item, int meta) {
		this.stack = new ItemStack(item, 1, meta);
	}
	
	public Stack(Block block) {
		this.stack = new ItemStack(block);
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
