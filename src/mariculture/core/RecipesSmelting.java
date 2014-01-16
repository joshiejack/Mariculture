package mariculture.core;

import java.util.ArrayList;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
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
	public static int electrum = 1000;
	
	public static void addRecipe(String fluid, int[] volume, Object[] items, int temperature, ItemStack output, int chance) {
		String origFluid = fluid;
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && volume[i] > 0) {
				Object item = items[i];
				ItemStack stack = null;
				if(item instanceof String) {
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
	 					RecipeHelper.addMelting(stack, temperature, FluidRegistry.getFluidStack(fluid, volume[i]), output, chance);
					} else {
						RecipeHelper.addMelting(stack, temperature, FluidRegistry.getFluidStack(fluid, volume[i]));
					}
				}
			}
		}
	}

	public static void add() {
		 addFuels();
		 addMetalRecipes();
	}
	
	public static void postAdd() {
		ItemStack sulfur = fetchItem(new String[] { "dustSulfur", "crystalSulfur" });
		ItemStack salt = new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SALT);
		ItemStack silicon = fetchItem(new String[] { "itemSilicon", "dustSiliconDioxide" });
		ItemStack platinum = fetchItem(new String[] { "dustPlatinum", "ingotPlatinum" });
		
		//Copperous Dust
		LinkedMetal[] coppers = new LinkedMetal[] {
				new LinkedMetal("ingotIron", FluidDictionary.iron, 4),
				new LinkedMetal("ingotSilver", FluidDictionary.silver, 7),
				new LinkedMetal("ingotGold", FluidDictionary.gold, 10),
				new LinkedMetal("ingotCobalt", FluidDictionary.cobalt, 15),
				new LinkedMetal("ingotNickel", FluidDictionary.nickel, 8),
				new LinkedMetal("ingotLead", FluidDictionary.lead, 7),
				new LinkedMetal("ingotTin", FluidDictionary.tin, 6)};
		addDust(MaterialsMeta.DUST_COPPEROUS, copper, sulfur, 10, coppers);
		
		//Golden Dust
		LinkedMetal[] golds = new LinkedMetal[] {
				new LinkedMetal("ingotElectrum", FluidDictionary.electrum, 3),
				new LinkedMetal("ingotSilver", FluidDictionary.silver, 7)};
		addDust(MaterialsMeta.DUST_GOLDEN, gold, null, 0, golds);
		
		//Ironic Dust
		LinkedMetal[] irons = new LinkedMetal[] {
				new LinkedMetal("ingotAluminum", FluidDictionary.aluminum, 3),
				new LinkedMetal("ingotTin", FluidDictionary.iron, 8),
				new LinkedMetal("ingotCopper", FluidDictionary.copper, 6)};
		addDust(MaterialsMeta.DUST_IRONIC, iron, silicon, 6, irons);
		
		//Leader Dust
		LinkedMetal[] leads = new LinkedMetal[] {
				new LinkedMetal("ingotSilver", FluidDictionary.silver, 3),
				new LinkedMetal("ingotIron", FluidDictionary.iron, 6),
				new LinkedMetal("ingotCopper", FluidDictionary.copper, 8),
				new LinkedMetal("ingotTin", FluidDictionary.tin, 10)};
		addDust(MaterialsMeta.DUST_LEADER, lead, null, 0, leads);
		
		//Magnesium Metal Dust!!!
		LinkedMetal[] magnesiums = new LinkedMetal[] {
				new LinkedMetal("ingotMagnesium", FluidDictionary.magnesium, 2),
				new LinkedMetal("ingotNickel", FluidDictionary.nickel, 3),
				new LinkedMetal("ingotCobalt", FluidDictionary.cobalt, 20), };
		addDust(MaterialsMeta.DUST_MAGNESITE, magnesium, salt, 1, magnesiums);
		
		//Silvery Dust
		LinkedMetal[] silvers = new LinkedMetal[] {
				new LinkedMetal("ingotLead", FluidDictionary.lead, 2),
				new LinkedMetal("ingotElectrum", FluidDictionary.electrum, 4) };
		addDust(MaterialsMeta.DUST_SILVERY, silver, sulfur, 5, silvers);
		
		//Tinnic Dust
		LinkedMetal[] tins = new LinkedMetal[] {
				new LinkedMetal("ingotCopper", FluidDictionary.copper, 3),
				new LinkedMetal("ingotIron", FluidDictionary.iron, 6),
				new LinkedMetal("ingotLead", FluidDictionary.lead, 8)};
		addDust(MaterialsMeta.DUST_TINNIC, tin, sulfur, 7, tins);
		
		addMetal(FluidDictionary.tin, "Tin", tin, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_TINNIC), 10);
		addMetal(FluidDictionary.copper, "Copper", copper, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_COPPEROUS), 10);
		addMetal(FluidDictionary.silver, "Silver", silver, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SILVERY), 10);
		addMetal(FluidDictionary.lead, "Lead", lead, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_LEADER), 10);
		addMetal(FluidDictionary.magnesium, "Magnesium", magnesium, null, 0);
		addMetal(FluidDictionary.nickel, "Nickel", nickel, platinum, 10);
		addMetal(FluidDictionary.bronze, "Bronze", bronze, null, 0);
		addMetal(FluidDictionary.steel, "Steel", steel, null, 0);
		addMetal(FluidDictionary.electrum, "Electrum", electrum, null, 0);
	}
	
	private static class LinkedMetal {
		public String ingot;
		public String fluid;
		public Integer chance;
		
		public LinkedMetal(String ingot, String fluid, Integer chance) {
			this.ingot = ingot;
			this.fluid = fluid;
			this.chance = chance;
		}
	}
	
	private static void addDust(int meta, int temp, ItemStack bonus, int chance, LinkedMetal[] metals) {
		ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
		ArrayList<Integer> chances = new ArrayList<Integer>();
		
		for(LinkedMetal metal: metals) {
			if(OreDictionary.getOres(metal.ingot).size() > 0 && get(metal.fluid) != null) {
				fluids.add(get(metal.fluid));
				chances.add(metal.chance);
			}
		}
		
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, meta),
				temp, fluids.toArray(new FluidStack[fluids.size()]), chances.toArray(new Integer[chances.size()]), bonus, chance));
	}

	private static ItemStack fetchItem(String[] array) {
		for(String arr: array) {
			if(OreDictionary.getOres(arr).size() > 0)
				return OreDictionary.getOres(arr).get(0);
		}
		
		return null;
	}

	private static void addFuels() {
		//Wood, Planks, Charcoal more useful for heating up, Coal for When ur heat is up
		RecipeHelper.addFuel("blockCoal", new FuelInfo(2000, 432, 10800));
		RecipeHelper.addFuel(new ItemStack(Item.coal, 1, 0), new FuelInfo(2000, 48, 1200));
		RecipeHelper.addFuel(new ItemStack(Item.coal, 1, 1), new FuelInfo(1600, 32, 320));
		RecipeHelper.addFuel("logWood", new FuelInfo(750, 8, 80));
		RecipeHelper.addFuel("plankWood", new FuelInfo(300, 4, 40));
		RecipeHelper.addFuel("stickWood", new FuelInfo(100, 2, 100));
		RecipeHelper.addFuel("lava", new FuelInfo(2000, 32, 100));
		RecipeHelper.addFuel(FluidDictionary.natural_gas, new FuelInfo(2000, 64, 1200));
		RecipeHelper.addFuel("oil", new FuelInfo(2000, 48, 800));
		RecipeHelper.addFuel("fuel", new FuelInfo(2000, 80, 3000));
		RecipeHelper.addFuel("pyrotheum", new FuelInfo(2000, 128, 256));
		RecipeHelper.addFuel("coal", new FuelInfo(2000, 8, 200));
	}
	
	public static void addFullSet(String fluid, Object[] items, int temp, ItemStack output, int chance) {
		addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
				items[0], items[1], items[2], items[3], items[4] }, temp, output, chance);
		
		addRecipe(fluid, MetalRates.TOOLS, new Object[] { 
				items[5], items[6], items[7], items[8], items[9] }, temp, new ItemStack(Item.stick), 1);
		
		addRecipe(fluid, MetalRates.ARMOR, new Object[] { 
				items[10], items[11], items[12], items[13] }, temp, null, 0);
	}
	
	public static void addMetal(String fluid, String metal, int temp, ItemStack bonus, int chance) {
		addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
				"ore" + metal, "nugget" + metal, "ingot" + metal, "block" + metal, "dust" + metal }, temp, bonus, chance);
		
		if(OreDictionary.getOres("ingot" + metal).size() > 0) {
			RecipeHelper.addIngotCasting(fluid, metal);
		}
	}

	
	public static void addMetalRecipes() {
		addFullSet(FluidDictionary.iron, new Object[] {
				"oreIron", "nuggetIron", "ingotIron", "blockIron", "dustIron",
				Item.pickaxeIron, Item.shovelIron, Item.axeIron, Item.swordIron, Item.hoeIron,
				Item.helmetIron, Item.plateIron, Item.legsIron, Item.bootsIron}, iron, 
				new ItemStack(Core.materials, 1, MaterialsMeta.DUST_IRONIC), 10);
		RecipeHelper.addIngotCasting(FluidDictionary.iron, "Iron");
		
		addFullSet(FluidDictionary.gold, new Object[] {
				"oreGold", "nugetGold", "ingotGold", "blockGold", "dustGold",
				Item.pickaxeGold, Item.shovelGold, Item.axeGold, Item.swordGold, Item.hoeGold,
				Item.helmetGold, Item.plateGold, Item.legsGold, Item.bootsGold}, gold, 
				new ItemStack(Core.materials, 1, MaterialsMeta.DUST_GOLDEN), 10);
		RecipeHelper.addIngotCasting(FluidDictionary.gold, "Gold");
		
		addRecipe(FluidDictionary.aluminum, MetalRates.MATERIALS, new Object[] { 
				"oreAluminum", "nuggetAluminum", "ingotAluminum", 
					"blockAluminum", "dustAluminum" }, aluminum, new ItemStack(Item.clay), 5);
		RecipeHelper.addIngotCasting(FluidDictionary.aluminum, "Aluminum");
		
		addRecipe(FluidDictionary.titanium, MetalRates.MATERIALS, new Object[] { 
				"oreRutile", "nuggetTitanium", "ingotTitanium", "blockTitanium", "dustTitanium" }, 
						titanium, new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		RecipeHelper.addIngotCasting(FluidDictionary.titanium, "Titanium");

		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.RUTILE), titanium, 
				FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.ORE), new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		
		FluidStack moltenRutile = FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.INGOT);
		FluidStack moltenMagnesium = FluidRegistry.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT);
		FluidStack moltenTitanium = FluidRegistry.getFluidStack(FluidDictionary.titanium, MetalRates.INGOT);
		//Making Titanium from Molten Rutile and Magnesium in a VAT takes 6 seconds
		RecipeHelper.addFluidAlloy(moltenRutile, moltenMagnesium, moltenTitanium, 6);
		
		//Melting Rutile and Magnesium Ingots = Molten Titanium
		RecipeHelper.addMeltingAlloy(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_MAGNESIUM), 
				new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_RUTILE), titanium, get(FluidDictionary.titanium));
		
		//Gold Back
		RecipeHelper.addMelting(new ItemStack(Block.pressurePlateGold), gold, gold(MetalRates.INGOT * 2));
		RecipeHelper.addMelting(new ItemStack(Item.pocketSundial), gold, gold(MetalRates.INGOT * 4), new ItemStack(Item.redstone), 2);
		RecipeHelper.addMelting(new ItemStack(Item.horseArmorGold), gold, gold(MetalRates.INGOT * 6), new ItemStack(Item.saddle), 4);
		
		//Iron Back
		RecipeHelper.addMelting(new ItemStack(Item.bucketEmpty), iron, FluidDictionary.iron, MetalRates.INGOT * 3);
		RecipeHelper.addMelting(new ItemStack(Item.doorIron), iron, FluidDictionary.iron, MetalRates.INGOT * 6);
		RecipeHelper.addMelting(new ItemStack(Block.fenceIron), iron, FluidDictionary.iron, (int) (MetalRates.INGOT * 0.25));
		RecipeHelper.addMelting(new ItemStack(Item.shears), iron, FluidDictionary.iron, MetalRates.INGOT * 2);
		RecipeHelper.addMelting(new ItemStack(Block.anvil, 1, 0), iron, FluidDictionary.iron, MetalRates.INGOT * 31);
		RecipeHelper.addMelting(new ItemStack(Block.anvil, 1, 1), iron, FluidDictionary.iron, MetalRates.INGOT * 22);
		RecipeHelper.addMelting(new ItemStack(Block.anvil, 1, 2), iron, FluidDictionary.iron, MetalRates.INGOT * 13);
		RecipeHelper.addMelting(new ItemStack(Block.pressurePlateIron), iron, FluidDictionary.iron, MetalRates.INGOT * 2);
		RecipeHelper.addMelting(new ItemStack(Item.compass), iron, iron(MetalRates.INGOT * 4), new ItemStack(Item.redstone), 2);	
		RecipeHelper.addMelting(new ItemStack(Block.hopperBlock), iron, iron(MetalRates.INGOT * 5), new ItemStack(Block.chest), 2);
		RecipeHelper.addMelting(new ItemStack(Item.flintAndSteel), iron, iron(MetalRates.INGOT));
		RecipeHelper.addMelting(new ItemStack(Item.horseArmorIron), iron, iron(MetalRates.INGOT * 6), new ItemStack(Item.saddle), 4);
		
		//Glass
		RecipeHelper.addMelting(new ItemStack(Block.sand), 1000, FluidDictionary.glass, 1000);
		RecipeHelper.addMelting(new ItemStack(Block.glass), 900, FluidDictionary.glass, 1000);
		RecipeHelper.addMelting(new ItemStack(Block.thinGlass), 500, FluidDictionary.glass, 375);
		
		//Ice/Snow > Water
		RecipeHelper.addMelting(new ItemStack(Block.ice), 1, "water", 1000);
		RecipeHelper.addMelting(new ItemStack(Block.blockSnow), 1, "water", 1000);
		
		//Glass > Plastic
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 
				FluidDictionary.glass, 30000, new ItemStack(Core.glassBlocks, 8, GlassMeta.PLASTIC), 5);
		
		//Water + Lava = Obsidian
		RecipeHelper.addFluidAlloyResultItem(FluidRegistry.getFluidStack("water", 1000), 
				FluidRegistry.getFluidStack("lava", 1000), new ItemStack(Block.obsidian), 2);
		
		//24 Parts Quicklime + 16 Parts Water = Unknown Metal Dust + 10 Parts Water (Takes 10 seconds)
		RecipeHelper.addFluidAlloyResultItemNFluid(FluidRegistry.getFluidStack("water", 16000), 
				FluidRegistry.getFluidStack(FluidDictionary.quicklime, 24000),
				FluidRegistry.getFluidStack("water", 10000), new ItemStack(Core.materials, 1, MaterialsMeta.DUST_MAGNESITE), 10);
		
		//Limestone > Quicklime
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 825, get(FluidDictionary.quicklime, 1000));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_BRICK), 825, get(FluidDictionary.quicklime, 1000));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_SMOOTH), 825, get(FluidDictionary.quicklime, 1100));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_CHISELED), 825, get(FluidDictionary.quicklime, 1100));
		
		//1500mB QuickLime + 1 Cobble = 2 Gravel
		RecipeHelper.addVatItemRecipe(new ItemStack(Block.cobblestone), FluidDictionary.quicklime, 1500, new ItemStack(Block.gravel, 2, 0), 10);
		
		//Salt > Molten Salt
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SALT), 801, get(FluidDictionary.salt, 20));
		
		//Rutile > Liquid
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_RUTILE), titanium, get(FluidDictionary.rutile));
		
		//Chainmail to steel
		addRecipe(FluidDictionary.steel, MetalRates.CHAIN, new Object[] { 
				new ItemStack(Item.helmetChain), new ItemStack(Item.plateChain), 
							new ItemStack(Item.legsChain), new ItemStack(Item.bootsChain) }, steel, null, 0);
		
		//1 Part Quicklime + 1 Nugget Aluminum + 1 Block Glass = 1 Heat Resistant Glass (15 Seconds)
		RecipeHelper.addFluidAlloyNItemResultItem(get(FluidDictionary.aluminum, MetalRates.NUGGET), 
				FluidRegistry.getFluidStack(FluidDictionary.quicklime, 1000),
				new ItemStack(Block.glass), new ItemStack(Core.glassBlocks, 1, GlassMeta.HEAT), 15);
	}
	
	public static FluidStack gold(int vol) {
		return FluidRegistry.getFluidStack(FluidDictionary.gold, vol);
	}
	
	public static FluidStack iron(int vol) {
		return FluidRegistry.getFluidStack(FluidDictionary.iron, vol);
	}
	
	public static FluidStack get(String name, int vol) {
		return FluidRegistry.getFluidStack(name, vol);
	}
	
	public static FluidStack get(String name) {
		return FluidRegistry.getFluidStack(name, MetalRates.INGOT);
	}
}
