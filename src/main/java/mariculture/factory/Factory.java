package mariculture.factory;


import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.diving.Diving;
import mariculture.factory.blocks.BlockCustomBlock;
import mariculture.factory.blocks.BlockCustomFence;
import mariculture.factory.blocks.BlockCustomFlooring;
import mariculture.factory.blocks.BlockCustomGate;
import mariculture.factory.blocks.BlockCustomLight;
import mariculture.factory.blocks.BlockCustomPower;
import mariculture.factory.blocks.BlockCustomSlab;
import mariculture.factory.blocks.BlockCustomStairs;
import mariculture.factory.blocks.BlockCustomWall;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemFilter;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.items.ItemRotor;
import mariculture.factory.tile.TileCustom;
import mariculture.factory.tile.TileCustomPowered;
import mariculture.factory.tile.TileDictionaryFluid;
import mariculture.factory.tile.TileDictionaryItem;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.factory.tile.TileFishSorter;
import mariculture.factory.tile.TileGeyser;
import mariculture.factory.tile.TilePressureVessel;
import mariculture.factory.tile.TileSawmill;
import mariculture.factory.tile.TileSluice;
import mariculture.factory.tile.TileSponge;
import mariculture.factory.tile.TileTurbineGas;
import mariculture.factory.tile.TileTurbineHand;
import mariculture.factory.tile.TileTurbineWater;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Factory extends RegistrationModule {	
	public static Block customFlooring;
	public static Block customBlock;
	public static Block customStairs;
	public static Block customSlabs;
	public static Block customSlabsDouble;
	public static Block customFence;
	public static Block customGate;
	public static Block customWall;
	public static Block customLight;
	public static Block customRFBlock;
	public static Item plans;
	public static Item fludd;
	public static Item paintbrush;
	public static Item filter;
	public static Item turbineAluminum;
	public static Item turbineTitanium;
	public static Item turbineCopper;

	private static ArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

	@Override
	public void registerHandlers() {
		MaricultureHandlers.turbine = new TileTurbineGas();
		MinecraftForge.EVENT_BUS.register(new FactoryEvents());
	}

	@Override
	public void registerBlocks() {
		customFlooring = new BlockCustomFlooring().setStepSound(Block.soundTypePiston).setBlockName("customFlooring");
		customBlock = new BlockCustomBlock().setStepSound(Block.soundTypePiston).setBlockName("customBlock");
		customStairs = new BlockCustomStairs(0).setStepSound(Block.soundTypePiston).setBlockName("customStairs");
		customSlabs = new BlockCustomSlab(false).setStepSound(Block.soundTypePiston).setBlockName("customSlabs");
		customSlabsDouble = new BlockCustomSlab(true).setStepSound(Block.soundTypePiston).setBlockName("customSlabsDouble");
		customFence = new BlockCustomFence().setStepSound(Block.soundTypePiston).setBlockName("customFence");
		customGate = new BlockCustomGate().setStepSound(Block.soundTypePiston).setBlockName("customGate");
		customWall = new BlockCustomWall().setStepSound(Block.soundTypePiston).setBlockName("customWall");
		customLight = new BlockCustomLight().setStepSound(Block.soundTypePiston).setBlockName("customLight").setLightLevel(1.0F);
		customRFBlock = new BlockCustomPower().setStepSound(Block.soundTypePiston).setBlockName("customRFBlock");
		
		RegistryHelper.registerBlocks(new Block[] { 
				customFlooring, customBlock, customStairs, customSlabs,  customFence, customGate, customWall, customLight, customRFBlock, customSlabsDouble });
		
		RegistryHelper.registerTiles(new Class[] { 
				TileCustom.class, TileCustomPowered.class, TileSawmill.class, TileSluice.class, TileTurbineWater.class, 
				TileFLUDDStand.class, TilePressureVessel.class, TileDictionaryItem.class, TileTurbineGas.class, 
				TileSponge.class, TileTurbineHand.class, TileFishSorter.class, TileGeyser.class, TileDictionaryFluid.class });
	}

	@Override
	public void registerItems() {
		plans = new ItemPlan().setUnlocalizedName("plans");
		fludd = new ItemArmorFLUDD(armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
		paintbrush = new ItemPaintbrush(128).setUnlocalizedName("paintbrush");
		filter = new ItemFilter().setUnlocalizedName("filter");
		turbineCopper = new ItemRotor(900, 1).setUnlocalizedName("turbine.copper");
		turbineAluminum = new ItemRotor(3600, 2).setUnlocalizedName("turbine.aluminum");
		turbineTitanium = new ItemRotor(28800, 3).setUnlocalizedName("turbine.titanium");
		RegistryHelper.registerItems(new Item[] { plans, paintbrush, fludd, filter, turbineCopper, turbineAluminum, turbineTitanium });
	}
	
	@Override
	public void registerOther() {
		EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
		FluidDicHandler.register("water", "water", 2000);
		FluidDicHandler.register("xp", "xpjuice", 20);
		FluidDicHandler.register("xp", "immibis.liquidxp", 100);
		FluidDicHandler.register("xp", "mobessence", 66);
	}

	@Override
	public void registerRecipes() {
	//Blocks
		//Sawmill
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.SAWMILL), new Object[] {
			" A ", "DWD", "IMI",
			Character.valueOf('A'), Items.iron_axe, 
			Character.valueOf('D'), "slabWood",
			Character.valueOf('M'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD),
			Character.valueOf('W'), "logWood", 
			Character.valueOf('I'), "ingotCopper"
		});
		
		//Autodictionary Converter
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.DICTIONARY_ITEM), new Object[] {
			" B ", "FPF", "IMI",
			Character.valueOf('F'), Items.feather, 
			Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
			Character.valueOf('M'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD), 
			Character.valueOf('B'), Items.writable_book,
			Character.valueOf('I'), "ingotCopper"
		});
		
		if(Extra.ENDER_CONVERTER) {
			//Alternative for Converter
			RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.DICTIONARY_ITEM), new Object[] {
				" B ", "FPF", "IMI",
				Character.valueOf('F'), Items.feather, 
				Character.valueOf('P'), Items.ender_pearl, 
				Character.valueOf('M'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD), 
				Character.valueOf('B'), Items.writable_book,
				Character.valueOf('I'), "ingotCopper"
			});
		}
		
		//Mechanized Sponge
		ItemStack sponge = (Modules.isActive(Modules.worldplus))? new ItemStack(Blocks.sponge): new ItemStack(Items.water_bucket);
		ItemStack water = (Modules.isActive(Modules.fishery))? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): new ItemStack(Items.potionitem, 1, 0);
		ItemStack fish = new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.SPONGE), new Object[] {
			" D ", "ATA", "SCS",
			Character.valueOf('D'), fish, 
			Character.valueOf('S'), sponge, 
			Character.valueOf('C'), new ItemStack(Core.metals, 1, MetalMeta.BASE_IRON),
			Character.valueOf('A'), water,
			Character.valueOf('T'), "ingotAluminum"
		});
		
		//Sluice
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 4, MachineMeta.SLUICE), new Object[] {
			" H ", "WBW", "IMI",
			Character.valueOf('H'), Blocks.hopper, 
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 
			Character.valueOf('M'), new ItemStack(Core.metals, 1, MetalMeta.BASE_IRON), 
			Character.valueOf('B'), Blocks.iron_bars,
			Character.valueOf('I'), "ingotAluminum"
		});
		
		//Manual Turbine Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.TURBINE_HAND), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineCopper,
			Character.valueOf('I'), "ingotCopper", 
			Character.valueOf('B'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD), 
			Character.valueOf('S'), "slabWood",
			Character.valueOf('P'), Blocks.piston
		});
		
		//Water Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.TURBINE_WATER), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineAluminum,
			Character.valueOf('I'), "ingotAluminum", 
			Character.valueOf('B'), new ItemStack(Core.metals, 1, MetalMeta.BASE_IRON), 
			Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 0),
			Character.valueOf('P'), Blocks.piston
		});
		
		//Gas Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.TURBINE_GAS), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineTitanium,
			Character.valueOf('I'), "ingotTitanium", 
			Character.valueOf('B'), new ItemStack(Core.metals, 1, MetalMeta.BASE_IRON), 
			Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 7),
			Character.valueOf('P'), Blocks.piston
		});
		
		//Pressure Vessel
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMultiMachines, 1, MachineRenderedMultiMeta.PRESSURE_VESSEL), new Object[] {
			"WLW", "PTP", "PSP",
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL),
			Character.valueOf('L'), "blockLapis",
			Character.valueOf('P'), new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_SHEET),
			Character.valueOf('T'), new ItemStack(Core.tanks, 1, TankMeta.TANK),
			Character.valueOf('S'), new ItemStack(Core.machines, 1, MachineMeta.SLUICE)
		});
		
		//Sorter
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.FISH_SORTER), new Object[] {
			"BPY", "GFA", "RCW",
			Character.valueOf('B'), "dyeBlack",
			Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('Y'), "dyeYellow",
			Character.valueOf('G'), "dyeGreen",
			Character.valueOf('F'), fish,
			Character.valueOf('A'), "dyeCyan",
			Character.valueOf('R'), "dyeRed",
			Character.valueOf('C'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD),
			Character.valueOf('W'), "dyeWhite"
		});
		
		//Geyser
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 16, MachineRenderedMeta.GEYSER), new Object[] {
			" W ", " G ", "RCR",
			Character.valueOf('W'), Items.water_bucket,
			Character.valueOf('G'), "glass",
			Character.valueOf('R'), Items.redstone,
			Character.valueOf('C'), new ItemStack(Core.metals, 1, MetalMeta.BASE_IRON),
		});
		
	//Items		
		//FLUDD
		ItemStack fludd = ((ItemArmorFLUDD)Factory.fludd).build();
		ItemStack tank = (Modules.isActive(Modules.diving))? new ItemStack(Diving.scubaTank, 1, 1): new ItemStack(Blocks.lever);
		RecipeHelper.addShapedRecipe(fludd, new Object[] {
			" E ", "PGP", "LUL",
			Character.valueOf('E'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), 
			Character.valueOf('P'), new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), 
			Character.valueOf('G'), new ItemStack(Core.transparent, 1, TransparentMeta.PLASTIC), 
			Character.valueOf('L'), tank, 
			Character.valueOf('U'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LIFE_CORE)
		});
		
		//Crafting of Life Core
		ItemStack bait = (Modules.isActive(Modules.fishery))? new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE): new ItemStack(Items.spider_eye);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LIFE_CORE), new Object[] {
			"DSR", "FHB", "PAC", 'D', Blocks.yellow_flower, 'S', "treeSapling", 'R', Blocks.red_flower,
			'F', fish, 'H', new ItemStack(Items.potionitem, 1, 8229), 'B', bait, 'P', Items.potato, 'A', Items.diamond_boots, 'C', Items.carrot
		});
		
		//Planning Chalk
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			"LLN", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		//Chalk Vertically x 3
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			"L  ", "L  ", "N  ", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			" L ", " L ", " N ", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			" N ", " L ", " L ", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			"N  ", "L  ", "L  ", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			"N  ", " L ", "  L", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] {
			"L  ", " L ", "  N", 'L', "blockLimestone", 'N', "dyeWhite"
		});
		
		RecipeHelper.addMelting(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), 825, 
				FluidRegistry.getFluidStack(FluidDictionary.quicklime, 2500));
		
		//Blank Plan
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.BLANK_PLAN), new Object[] {
			"dyeBlue", "dyeBlack", Items.paper, "dyeBlue"
		});
		
		//Item Filter
		ItemStack filterer = (Modules.isActive(Modules.fishery))? new ItemStack(Fishery.net): new ItemStack(Blocks.chest);
		RecipeHelper.addShapedRecipe(new ItemStack(filter), new Object[] {
			"W W", "WNW", " W ",
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER),
			Character.valueOf('N'), filterer
		});
		
		//Paintbrush
		RecipeHelper.addShapedRecipe(new ItemStack(paintbrush), new Object[] {
			" WW", " IW", "S  ",
			Character.valueOf('W'), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 
			Character.valueOf('I'), "blockAluminum", 
			Character.valueOf('S'), new ItemStack(Core.machines, 1, MachineMeta.SAWMILL)
		});
		
		//Copper Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(turbineCopper), new Object[] {
			" I ", "ISI", " I ",
			Character.valueOf('I'), "ingotCopper",
			Character.valueOf('S'), "slabWood"
		});
		
		//Aluminum Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(turbineAluminum), new Object[] {
			" I ", "ISI", " I ",
			Character.valueOf('I'), "ingotAluminum",
			Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 0)
		});	
		
		//Titanium Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(turbineTitanium), new Object[] {
			" I ", "ISI", " I ",
			Character.valueOf('I'), "ingotTitanium",
			Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 7)
		});	
		
		MaricultureHandlers.turbine.add(FluidDictionary.natural_gas);
		MaricultureHandlers.turbine.add("gascraft_naturalgas");
	}
}
