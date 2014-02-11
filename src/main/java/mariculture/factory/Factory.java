package mariculture.factory;


import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UtilMeta;
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
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.blocks.BlockItemCustomSlab;
import mariculture.factory.blocks.TileCustom;
import mariculture.factory.blocks.TileCustomPowered;
import mariculture.factory.blocks.TileDictionary;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TilePressureVessel;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.blocks.TileSluice;
import mariculture.factory.blocks.TileSponge;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemFilter;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.items.ItemRotor;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Factory extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
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
		customFlooring = new BlockCustomFlooring().setStepSound(Blocks.soundStoneFootstep)
				.setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("customFlooring").setLightOpacity(0);
		customBlock = new BlockCustomBlock(customBlock).setStepSound(Blocks.soundStoneFootstep).setHardness(1F)
				.setResistance(0.1F).setUnlocalizedName("customBlock");
		customStairs = new BlockCustomStairs(customStairs, customBlock, 0)
				.setStepSound(Blocks.soundStoneFootstep).setHardness(1F).setResistance(0.1F) .setUnlocalizedName("customStairs");
		customSlabs = new BlockCustomSlab(customSlabs, false).setUnlocalizedName("customSlabs");
		customSlabsDouble = new BlockCustomSlab(customSlabsDouble, true).setUnlocalizedName("customSlabsDouble");
		customFence = new BlockCustomFence(customFence).setUnlocalizedName("customFence");
		customGate = new BlockCustomGate(customGate).setUnlocalizedName("customGate");
		customWall = new BlockCustomWall(customWall, customBlock).setUnlocalizedName("customWall");
		customLight = new BlockCustomLight(customLight).setUnlocalizedName("customLight").setLightValue(1.0F);
		customRFBlock = new BlockCustomPower(customRFBlock).setUnlocalizedName("customRFBlock");

		Items.itemsList[customFlooring] = new BlockItemCustom(customFlooring - 256, customFlooring).setUnlocalizedName("customFlooring");
		Items.itemsList[customBlock] = new BlockItemCustom(customBlock - 256, customBlock).setUnlocalizedName("customBlock");
		Items.itemsList[customStairs] = new BlockItemCustom(customStairs - 256, customStairs).setUnlocalizedName("customStairs");
		Items.itemsList[customFence] = new BlockItemCustom(customFence - 256, customFence).setUnlocalizedName("customFence");
		Items.itemsList[customGate] = new BlockItemCustom(customGate - 256, customGate).setUnlocalizedName("customGate");
		Items.itemsList[customWall] = new BlockItemCustom(customWall - 256, customWall).setUnlocalizedName("customWall");
		Items.itemsList[customLight] = new BlockItemCustom(customLight - 256, customLight).setUnlocalizedName("customLight");
		Items.itemsList[customRFBlock] = new BlockItemCustom(customRFBlock - 256, customRFBlock).setUnlocalizedName("customRFBlock");
		BlockItemCustomSlab.setSlabs((BlockHalfSlab) customSlabs, (BlockHalfSlab) customSlabsDouble);

		GameRegistry.registerBlock(customSlabs, mariculture.factory.blocks.BlockItemCustomSlab.class, "Mariculture_customSlab");
		GameRegistry.registerBlock(customSlabsDouble, mariculture.factory.blocks.BlockItemCustomSlab.class, "Mariculture_customSlabDouble");

		GameRegistry.registerTileEntity(TileCustom.class, "tileEntityCustom");
		GameRegistry.registerTileEntity(TileCustomPowered.class, "tileEntityCustomRF");
		GameRegistry.registerTileEntity(TileSawmill.class, "tileEntitySawmill");
		GameRegistry.registerTileEntity(TileSluice.class, "tileEntitySluice");
		GameRegistry.registerTileEntity(TileTurbineWater.class, "tileEntityTurbine");
		GameRegistry.registerTileEntity(TileFLUDDStand.class, "tileEntityFLUDD");
		GameRegistry.registerTileEntity(TilePressureVessel.class, "tileEntityPressureVessel");
		GameRegistry.registerTileEntity(TileDictionary.class, "tileEntityDictionary");
		GameRegistry.registerTileEntity(TileTurbineGas.class, "tileEntityTurbineGas");
        GameRegistry.registerTileEntity(TileSponge.class, "tileEntitySponge");
        GameRegistry.registerTileEntity(TileTurbineHand.class, "tileEntityTurbineHand");
        GameRegistry.registerTileEntity(TileFishSorter.class, "tileFishSorter");
        GameRegistry.registerTileEntity(TileGeyser.class, "tileGeyser");

		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.SLUICE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.SPONGE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.TURBINE_WATER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.TURBINE_GAS, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.FISH_SORTER, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.FLUDD_STAND, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.TURBINE_HAND, "axe", 0);

		RegistryHelper.register(new Object[] { customFlooring, customBlock, customStairs, customSlabs, 
				customFence, customGate, customWall, customLight, customRFBlock, customSlabsDouble });
	}

	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
	}

	@Override
	public void registerItems() {
		plans = new ItemPlan(plans).setUnlocalizedName("plans");
		fludd = new ItemArmorFLUDD(fludd, armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
		paintbrush = new ItemPaintbrush(paintbrush, 128).setUnlocalizedName("paintbrush");
		filter = new ItemFilter(filter).setUnlocalizedName("filter");
		turbineCopper = new ItemRotor(turbineCopper, 900, 1).setUnlocalizedName("turbineCopper");
		turbineAluminum = new ItemRotor(turbineAluminum, 3600, 2).setUnlocalizedName("turbineAluminum");
		turbineTitanium = new ItemRotor(turbineTitanium, 28800, 3).setUnlocalizedName("turbineTitanium");
		RegistryHelper.register(new Object[] { plans, fludd, paintbrush, filter, turbineCopper, turbineAluminum, turbineTitanium });
	}

	@Override
	public void addRecipes() {
	//Blocks
		//Sawmill
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.SAWMILL), new Object[] {
			" A ", "DWD", "IMI",
			Character.valueOf('A'), Items.axeIron, 
			Character.valueOf('D'), "slabWood",
			Character.valueOf('M'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD),
			Character.valueOf('W'), "logWood", 
			Character.valueOf('I'), "ingotCopper"
		});
		
		//Autodictionary Converter
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.DICTIONARY), new Object[] {
			" B ", "FPF", "IMI",
			Character.valueOf('F'), Items.feather, 
			Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
			Character.valueOf('M'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD), 
			Character.valueOf('B'), Items.writableBook,
			Character.valueOf('I'), "ingotCopper"
		});
		
		if(Extra.ENDER_CONVERTER) {
			//Alternative for Converter
			RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.DICTIONARY), new Object[] {
				" B ", "FPF", "IMI",
				Character.valueOf('F'), Items.feather, 
				Character.valueOf('P'), Items.enderPearl, 
				Character.valueOf('M'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD), 
				Character.valueOf('B'), Items.writableBook,
				Character.valueOf('I'), "ingotCopper"
			});
		}
		
		//Mechanized Sponge
		ItemStack sponge = (Modules.world.isActive())? new ItemStack(Blocks.sponge): new ItemStack(Items.bucketWater);
		ItemStack water = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): new ItemStack(Items.potion, 1, 0);
		ItemStack fish = (Modules.fishery.isActive()) ? new ItemStack(Fishery.fishyFood, 1, OreDictionary.WILDCARD_VALUE): new ItemStack(Items.fishRaw);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.SPONGE), new Object[] {
			" D ", "ATA", "SCS",
			Character.valueOf('D'), fish, 
			Character.valueOf('S'), sponge, 
			Character.valueOf('C'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON),
			Character.valueOf('A'), water,
			Character.valueOf('T'), "ingotAluminum"
		});
		
		//Sluice
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 4, UtilMeta.SLUICE), new Object[] {
			" H ", "WBW", "IMI",
			Character.valueOf('H'), Blocks.hopperBlock, 
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 
			Character.valueOf('M'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), 
			Character.valueOf('B'), Blocks.fenceIron,
			Character.valueOf('I'), "ingotAluminum"
		});
		
		//Manual Turbine Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_HAND), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineCopper,
			Character.valueOf('I'), "ingotCopper", 
			Character.valueOf('B'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD), 
			Character.valueOf('S'), "slabWood",
			Character.valueOf('P'), Blocks.pistonBase
		});
		
		//Water Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_WATER), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineAluminum,
			Character.valueOf('I'), "ingotAluminum", 
			Character.valueOf('B'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), 
			Character.valueOf('S'), new ItemStack(Blocks.stoneSingleSlab, 1, 0),
			Character.valueOf('P'), Blocks.pistonBase
		});
		
		//Gas Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_GAS), new Object[] {
			" T ", "IBI", "SPS",
			Character.valueOf('T'), turbineTitanium,
			Character.valueOf('I'), "ingotTitanium", 
			Character.valueOf('B'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), 
			Character.valueOf('S'), new ItemStack(Blocks.stoneSingleSlab, 1, 7),
			Character.valueOf('P'), Blocks.pistonBase
		});
		
		//Pressure Vessel
		RecipeHelper.addShapedRecipe(new ItemStack(Core.doubleBlock, 1, DoubleMeta.PRESSURE_VESSEL), new Object[] {
			"WLW", "PTP", "PSP",
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL),
			Character.valueOf('L'), "blockLapis",
			Character.valueOf('P'), new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_SHEET),
			Character.valueOf('T'), new ItemStack(Core.tankBlocks, 1, TankMeta.TANK),
			Character.valueOf('S'), new ItemStack(Core.utilBlocks, 1, UtilMeta.SLUICE)
		});
		
		//Sorter
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.FISH_SORTER), new Object[] {
			"BPY", "GFA", "RCW",
			Character.valueOf('B'), "dyeBlack",
			Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('Y'), "dyeYellow",
			Character.valueOf('G'), "dyeGreen",
			Character.valueOf('F'), fish,
			Character.valueOf('A'), "dyeCyan",
			Character.valueOf('R'), "dyeRed",
			Character.valueOf('C'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD),
			Character.valueOf('W'), "dyeWhite"
		});
		
		//Geyser
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 16, SingleMeta.GEYSER), new Object[] {
			" W ", " G ", "RCR",
			Character.valueOf('W'), Items.bucketWater,
			Character.valueOf('G'), "glass",
			Character.valueOf('R'), Items.redstone,
			Character.valueOf('C'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON),
		});
		
	//Items
		//Machine Book
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.guides, 1, GuideMeta.MACHINES), new Object[] {
			Items.book, new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL)
		});
		
		//FLUDD
		ItemStack fludd = ((ItemArmorFLUDD)Factory.fludd).build();
		ItemStack tank = (Modules.diving.isActive())? new ItemStack(Diving.scubaTank, 1, 1): new ItemStack(Blocks.lever);
		RecipeHelper.addShapedRecipe(fludd, new Object[] {
			" E ", "PGP", "LUL",
			Character.valueOf('E'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), 
			Character.valueOf('P'), new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), 
			Character.valueOf('G'), new ItemStack(Core.transparentBlocks, 1, TransparentMeta.PLASTIC), 
			Character.valueOf('L'), tank, 
			Character.valueOf('U'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LIFE_CORE)
		});
		
		//Crafting of Life Core
		ItemStack bait = (Modules.fishery.isActive())? new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE): new ItemStack(Items.spiderEye);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LIFE_CORE), new Object[] {
			"DSR", "FHB", "PAC", 'D', Blocks.plantYellow, 'S', "treeSapling", 'R', Blocks.plantRed,
			'F', fish, 'H', new ItemStack(Items.potion, 1, 8229), 'B', bait, 'P', Items.potato, 'A', Items.bootsDiamond, 'C', Items.carrot
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
		ItemStack filterer = (Modules.fishery.isActive())? new ItemStack(Fishery.net): new ItemStack(Blocks.chest);
		RecipeHelper.addShapedRecipe(new ItemStack(filter), new Object[] {
			"W W", "WNW", " W ",
			Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER),
			Character.valueOf('N'), filterer
		});
		
		//Paintbrush
		RecipeHelper.addShapedRecipe(new ItemStack(paintbrush), new Object[] {
			" WW", " IW", "S  ",
			Character.valueOf('W'), new ItemStack(Blocks.cloth, 1, OreDictionary.WILDCARD_VALUE), 
			Character.valueOf('I'), "blockAluminum", 
			Character.valueOf('S'), new ItemStack(Core.utilBlocks, 1, UtilMeta.SAWMILL)
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
			Character.valueOf('S'), new ItemStack(Blocks.stoneSingleSlab, 1, 0)
		});	
		
		//Titanium Turbine
		RecipeHelper.addShapedRecipe(new ItemStack(turbineTitanium), new Object[] {
			" I ", "ISI", " I ",
			Character.valueOf('I'), "ingotTitanium",
			Character.valueOf('S'), new ItemStack(Blocks.stoneSingleSlab, 1, 7)
		});	
		
		MaricultureHandlers.turbine.add(FluidDictionary.natural_gas);
		MaricultureHandlers.turbine.add("gasCraft_naturalGas");
	}
}
