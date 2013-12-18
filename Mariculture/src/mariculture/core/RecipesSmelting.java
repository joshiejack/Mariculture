package mariculture.core;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeFreezer;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesSmelting {
	public static int iron = 1538;
	public static int gold = 1064;
	public static int tin = 232;
	public static int copper = 1085;
	public static int silver = 962;
	public static int lead = 328;
	public static int magnesium = 650;
	public static int nickel = 1455;
	public static int bronze = 950;
	public static int steel = 1370;
	public static int aluminum = 660;
	public static int titanium = 1662;
	
	public static void addRecipe(String fluid, int[] volume, Object[] items, int temperature, ItemStack output, int chance) {
		String origFluid = fluid;
		boolean isRutile = false;
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && volume[i] > 0) {
				Object item = items[i];
				ItemStack stack = null;
				if(item instanceof String) {
					isRutile = item.equals("oreTitanium");
					if(OreDictionary.getOres((String)item).size() > 0) {
						stack = OreDictionary.getOres((String) item).get(0);
					}
				} else if(item instanceof ItemStack) {
					stack = (ItemStack) item;
				} else if (item instanceof Item) {
					stack = new ItemStack((Item)item);
				} else if(item instanceof Block) {
					stack = new ItemStack((Block)item);
				}
				
				if(stack != null && FluidRegistry.getFluid(fluid) != null) {
					if(i == 0 || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemHoe) {
						if(isRutile) {
							fluid = FluidDictionary.rutile;
						} else {
							fluid = origFluid;
						}
						
	 					MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, temperature, 
								new SmelterOutput(FluidRegistry.getFluidStack(fluid, volume[i]), output, chance)));
					} else {
						MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, temperature, 
								new SmelterOutput(FluidRegistry.getFluidStack(fluid, volume[i]))));
					}
				}
			}
		}
	}

	public static void add() {
		 addFuels();
		 addMetalRecipes();
	}

	public static void addFuels() {
		MaricultureHandlers.smelter.addFuel(new ItemStack(Block.coalBlock, 1, 0), 432, 2000);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 0), 48, 2000);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 1), 32, 1500);
		MaricultureHandlers.smelter.addFuel("logWood", 8, 750);
		MaricultureHandlers.smelter.addFuel("plankWood", 4, 300);
	}
	
	public static void addFullSet(String fluid, Object[] items, int temp) {
		addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
				items[0], items[1], items[2], items[3], items[4] }, temp, new ItemStack(Block.stone), 2);
		
		addRecipe(fluid, MetalRates.TOOLS, new Object[] { 
				items[5], items[6], items[7], items[8], items[9] }, temp, new ItemStack(Item.stick), 1);
		
		addRecipe(fluid, MetalRates.ARMOR, new Object[] { 
				items[10], items[11], items[12], items[13] }, temp, null, 0);
	}

	
	public static void addMetalRecipes() {
		addFullSet(FluidDictionary.iron, new Object[] {
				Block.oreIron, "nuggetIron", Item.ingotIron, Block.blockIron, "dustIron",
				Item.pickaxeIron, Item.shovelIron, Item.axeIron, Item.swordIron, Item.hoeIron,
				Item.helmetIron, Item.plateIron, Item.legsIron, Item.bootsIron}, iron);
		
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT), 
				null, new ItemStack(Item.ingotIron)));
		
		addFullSet(FluidDictionary.gold, new Object[] {
				Block.oreGold, Item.goldNugget, Item.ingotGold, Block.blockGold, "dustGold",
				Item.pickaxeGold, Item.shovelGold, Item.axeGold, Item.swordGold, Item.hoeGold,
				Item.helmetGold, Item.plateGold, Item.legsGold, Item.bootsGold}, gold);
		
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.gold, MetalRates.INGOT), 
				null, new ItemStack(Item.ingotGold)));
		
		addRecipe(FluidDictionary.tin, MetalRates.MATERIALS, new Object[] { 
				"oreTin", "nuggetTin", "ingotTin", "blockTin", "dustTin" }, tin, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotTin").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.tin, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotTin").get(0)));
		}
		
		addRecipe(FluidDictionary.copper, MetalRates.MATERIALS, new Object[] { 
				"oreCopper", "nuggetCopper", "ingotCopper", "blockCopper", "dustCopper" }, copper, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotCopper").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.copper, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotCopper").get(0)));
		}
		
		addRecipe(FluidDictionary.silver, MetalRates.MATERIALS, new Object[] { 
				"oreSilver", "nuggetSilver", "ingotSilver", "blockSilver", "dustSilver" }, silver, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotSilver").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.silver, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotSilver").get(0)));
		}
		
		addRecipe(FluidDictionary.lead, MetalRates.MATERIALS, new Object[] { 
				"oreLead", "nuggetLead", "ingotLead", "blockLead", "dustLead" }, lead, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotLead").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.lead, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotLead").get(0)));
		}
		
		addRecipe(FluidDictionary.magnesium, MetalRates.MATERIALS, new Object[] { 
				"oreMagnesium", "nuggetMagnesium", "ingotMagnesium", "blockMagnesium", "dustMagnesium" }, magnesium, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotMagnesium").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotMagnesium").get(0)));
		}
		
		addRecipe(FluidDictionary.nickel, MetalRates.MATERIALS, new Object[] { 
				"oreNickel", "nuggetNickel", "ingotNickel", "blockNickel", "dustNickel" }, nickel, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotNickel").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.nickel, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotNickel").get(0)));
		}
		
		addRecipe(FluidDictionary.bronze, MetalRates.MATERIALS, new Object[] { 
				"oreBronze", "nuggetBronze", "ingotBronze", "blockBronze", "dustBronze" }, bronze, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotBronze").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.bronze, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotBronze").get(0)));
		}
		
		addRecipe(FluidDictionary.steel, MetalRates.MATERIALS, new Object[] { 
				"oreSteel", "nuggetSteel", "ingotSteel", "blockSteel", "dustSteel" }, steel, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingotSteel").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.steel, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotSteel").get(0)));
		}
		
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET), aluminum, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.aluminum, MetalRates.INGOT * 3), null, 0)));	
		addRecipe(FluidDictionary.aluminum, MetalRates.MATERIALS, new Object[] { 
				"oreAluminum", "nuggetAluminum", "ingotAluminum", 
					"blockAluminum", "dustAluminum" }, aluminum, new ItemStack(Item.clay), 5);
		
		if(OreDictionary.getOres("ingotAluminum").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.aluminum, MetalRates.INGOT), 
					null, OreDictionary.getOres("ingotAluminum").get(0)));
		}
		
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.pearls, 1, PearlColor.GOLD), gold, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.gold, MetalRates.NUGGET), null, 0)));	
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.pearls, 1, PearlColor.SILVER), silver, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.silver, MetalRates.NUGGET), null, 0)));	
		
		addRecipe(FluidDictionary.titanium, MetalRates.MATERIALS, new Object[] { 
				"oreTitanium", "nuggetTitanium", "ingotTitanium", "blockTitanium", "dustTitanium" }, 
						titanium, new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.INGOT), 
				new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_MAGNESIUM), new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM)));
		
		//Gold Back
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.pressurePlateGold), gold, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.gold, MetalRates.INGOT * 1), null, 0)));
		
		//Iron Back
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.bucketEmpty), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.NUGGET * 17), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.doorIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 4), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.fenceIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, (int) (MetalRates.INGOT * 0.25)), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.shears), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.NUGGET * 4), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 3), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.anvil), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 18), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.pressurePlateIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 1), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.hopperBlock), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 3), new ItemStack(Block.planks), 1)));
		
		//Glass
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.sand), 1000, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.glass), 900, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.thinGlass), 500, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, 375), null, 0)));
		
		//Ice > Water
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.ice), 1, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidRegistry.WATER.getName(), FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		
		//Water > Ice
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidRegistry.WATER.getName(), FluidContainerRegistry.BUCKET_VOLUME), 
				new ItemStack(Item.snowball), new ItemStack(Block.ice)));
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidRegistry.WATER.getName(), FluidContainerRegistry.BUCKET_VOLUME/4), 
				new ItemStack(Block.blockSnow), new ItemStack(Block.ice)));
		
		//Glass > Plastic
		MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.glass, FluidContainerRegistry.BUCKET_VOLUME * 32), 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), new ItemStack(Core.glassBlocks, 8, GlassMeta.PLASTIC)));
		
		//Plastic > Hard Plastic with mod plastic
		if(OreDictionary.getOres("sheetPlastic").size() > 0) {
			MaricultureHandlers.freezer.addRecipe(new RecipeFreezer(FluidRegistry.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT * 5),
					OreDictionary.getOres("sheetPlastic").get(0), new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC)));
		}
	}
}
