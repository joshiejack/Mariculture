package mariculture.factory;


import static mariculture.core.lib.Items.autodictionary;
import static mariculture.core.lib.Items.baseIron;
import static mariculture.core.lib.Items.baseWood;
import static mariculture.core.lib.Items.black;
import static mariculture.core.lib.Items.blue;
import static mariculture.core.lib.Items.bookAndQuill;
import static mariculture.core.lib.Items.cyan;
import static mariculture.core.lib.Items.enderPearl;
import static mariculture.core.lib.Items.feather;
import static mariculture.core.lib.Items.filterer;
import static mariculture.core.lib.Items.fish;
import static mariculture.core.lib.Items.fishSorter;
import static mariculture.core.lib.Items.gasTurbine;
import static mariculture.core.lib.Items.goldPlastic;
import static mariculture.core.lib.Items.green;
import static mariculture.core.lib.Items.handTurbine;
import static mariculture.core.lib.Items.hopper;
import static mariculture.core.lib.Items.ironAxe;
import static mariculture.core.lib.Items.ironBars;
import static mariculture.core.lib.Items.ironWheel;
import static mariculture.core.lib.Items.life;
import static mariculture.core.lib.Items.mechSponge;
import static mariculture.core.lib.Items.paper;
import static mariculture.core.lib.Items.pearls;
import static mariculture.core.lib.Items.piston;
import static mariculture.core.lib.Items.plan;
import static mariculture.core.lib.Items.plasticLens;
import static mariculture.core.lib.Items.pressureVessel;
import static mariculture.core.lib.Items.quartzSlab;
import static mariculture.core.lib.Items.red;
import static mariculture.core.lib.Items.redstone;
import static mariculture.core.lib.Items.sawmill;
import static mariculture.core.lib.Items.scubaTank;
import static mariculture.core.lib.Items.sluice;
import static mariculture.core.lib.Items.sponge;
import static mariculture.core.lib.Items.stoneSlab;
import static mariculture.core.lib.Items.tank;
import static mariculture.core.lib.Items.titaniumSheet;
import static mariculture.core.lib.Items.transparent;
import static mariculture.core.lib.Items.water;
import static mariculture.core.lib.Items.waterBucket;
import static mariculture.core.lib.Items.waterTurbine;
import static mariculture.core.lib.Items.white;
import static mariculture.core.lib.Items.wicker;
import static mariculture.core.lib.Items.wool;
import static mariculture.core.lib.Items.yellow;
import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.RenderMeta;
import mariculture.core.util.FluidDictionary;
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
import mariculture.factory.blocks.TileHDFPV;
import mariculture.factory.blocks.TilePressureVessel;
import mariculture.factory.blocks.TileSawmill;
import mariculture.factory.blocks.TileSluice;
import mariculture.factory.blocks.TileSponge;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemChalk;
import mariculture.factory.items.ItemFilter;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.items.ItemRotor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
	public static Item chalk;
	public static Item plans;
	public static Item fludd;
	public static Item paintbrush;
	public static Item filter;
	public static Item turbineAluminum;
	public static Item turbineTitanium;
	public static Item turbineCopper;

	private static EnumArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

	@Override
	public void registerHandlers() {
		MaricultureHandlers.turbine = new TileTurbineGas();
		MinecraftForge.EVENT_BUS.register(new FactoryEvents());
	}

	@Override
	public void registerBlocks() {
		customFlooring = new BlockCustomFlooring(BlockIds.customFlooring).setStepSound(Block.soundStoneFootstep)
				.setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("customFlooring").setLightOpacity(0);
		customBlock = new BlockCustomBlock(BlockIds.customBlock).setStepSound(Block.soundStoneFootstep).setHardness(1F)
				.setResistance(0.1F).setUnlocalizedName("customBlock");
		customStairs = new BlockCustomStairs(BlockIds.customStairs, customBlock, 0)
				.setStepSound(Block.soundStoneFootstep).setHardness(1F).setResistance(0.1F) .setUnlocalizedName("customStairs");
		customSlabs = new BlockCustomSlab(BlockIds.customSlabs, false).setUnlocalizedName("customSlabs");
		customSlabsDouble = new BlockCustomSlab(BlockIds.customSlabsDouble, true).setUnlocalizedName("customSlabsDouble");
		customFence = new BlockCustomFence(BlockIds.customFence).setUnlocalizedName("customFence");
		customGate = new BlockCustomGate(BlockIds.customGate).setUnlocalizedName("customGate");
		customWall = new BlockCustomWall(BlockIds.customWall, customBlock).setUnlocalizedName("customWall");
		customLight = new BlockCustomLight(BlockIds.customLight).setUnlocalizedName("customLight").setLightValue(1.0F);
		customRFBlock = new BlockCustomPower(BlockIds.customRFBlock).setUnlocalizedName("customRFBlock");

		Item.itemsList[BlockIds.customFlooring] = new BlockItemCustom(BlockIds.customFlooring - 256, customFlooring).setUnlocalizedName("customFlooring");
		Item.itemsList[BlockIds.customBlock] = new BlockItemCustom(BlockIds.customBlock - 256, customBlock).setUnlocalizedName("customBlock");
		Item.itemsList[BlockIds.customStairs] = new BlockItemCustom(BlockIds.customStairs - 256, customStairs).setUnlocalizedName("customStairs");
		Item.itemsList[BlockIds.customFence] = new BlockItemCustom(BlockIds.customFence - 256, customFence).setUnlocalizedName("customFence");
		Item.itemsList[BlockIds.customGate] = new BlockItemCustom(BlockIds.customGate - 256, customGate).setUnlocalizedName("customGate");
		Item.itemsList[BlockIds.customWall] = new BlockItemCustom(BlockIds.customWall - 256, customWall).setUnlocalizedName("customWall");
		Item.itemsList[BlockIds.customLight] = new BlockItemCustom(BlockIds.customLight - 256, customLight).setUnlocalizedName("customLight");
		Item.itemsList[BlockIds.customRFBlock] = new BlockItemCustom(BlockIds.customRFBlock - 256, customRFBlock).setUnlocalizedName("customRFBlock");
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
        GameRegistry.registerTileEntity(TileHDFPV.class, "tileHDFPV");

		MinecraftForge.setBlockHarvestLevel(Core.machines, MachineMeta.SLUICE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.machines, MachineMeta.SPONGE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.rendered, RenderMeta.TURBINE_WATER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.rendered, RenderMeta.TURBINE_GAS, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(Core.machines, MachineMeta.FISH_SORTER, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(Core.rendered, RenderMeta.FLUDD_STAND, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.rendered, RenderMeta.TURBINE_HAND, "axe", 0);

		RegistryHelper.register(new Object[] { customFlooring, customBlock, customStairs, customSlabs, customFence, customGate, customWall, customLight, customRFBlock, customSlabsDouble });
	}

	@Override
	public void registerItems() {
		chalk = new ItemChalk(ItemIds.chalk, 64).setUnlocalizedName("chalk");
		plans = new ItemPlan(ItemIds.plans).setUnlocalizedName("plans");
		fludd = new ItemArmorFLUDD(ItemIds.fludd, armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
		paintbrush = new ItemPaintbrush(ItemIds.paintbrush, 128).setUnlocalizedName("paintbrush");
		filter = new ItemFilter(ItemIds.filter).setUnlocalizedName("filter");
		turbineCopper = new ItemRotor(ItemIds.turbineCopper, 900, 1).setUnlocalizedName("turbineCopper");
		turbineAluminum = new ItemRotor(ItemIds.turbineAluminum, 3600, 2).setUnlocalizedName("turbineAluminum");
		turbineTitanium = new ItemRotor(ItemIds.turbineTitanium, 28800, 3).setUnlocalizedName("turbineTitanium");
		RegistryHelper.register(new Object[] { chalk, plans, fludd, paintbrush, filter, turbineCopper, turbineAluminum, turbineTitanium });
	}
	
	@Override
	public void registerOther() {
		EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
	}

	@Override
	public void registerRecipes() {
		registerBlockRecipes();
		registerItemRecipes();
		MaricultureHandlers.turbine.add(FluidDictionary.natural_gas);
		MaricultureHandlers.turbine.add("gasCraft_naturalGas");
	}
	
	private void registerBlockRecipes() {
		ItemStack geyser16 = new ItemStack(Core.rendered, 16, RenderMeta.GEYSER);
		ItemStack sluice4 = new ItemStack(Core.machines, 4, MachineMeta.SLUICE);
		RecipeHelper.addShapedRecipe(geyser16, new Object[] {" W ", " G ", "RCR", 'W', waterBucket, 'G', "glass", 'R', redstone, 'C', baseIron});
		RecipeHelper.addShapedRecipe(sluice4, new Object[] {" H ", "WBW", "IMI", 'H', hopper, 'W', ironWheel, 'M', baseIron, 'B', ironBars, 'I', "ingotAluminum"});
		RecipeHelper.addShapedRecipe(mechSponge, new Object[] {" D ", "ATA", "SCS", 'D', fish, 'S', sponge, 'C', baseIron, 'A', water, 'T', "ingotAluminum"});
		RecipeHelper.addShapedRecipe(handTurbine, new Object[] {" T ", "IBI", "SPS", 'T', turbineCopper, 'I', "ingotCopper", 'B', baseWood, 'S', "slabWood", 'P', piston});
		RecipeHelper.addShapedRecipe(waterTurbine, new Object[] {" T ", "IBI", "SPS", 'T', turbineAluminum, 'I', "ingotAluminum", 'B', baseIron, 'S', stoneSlab, 'P', piston});
		RecipeHelper.addShapedRecipe(gasTurbine, new Object[] {" T ", "IBI", "SPS", 'T', turbineTitanium, 'I', "ingotTitanium", 'B', baseIron, 'S', quartzSlab, 'P', piston});
		RecipeHelper.addShapedRecipe(pressureVessel, new Object[] {"WLW", "PTP", "PSP", 'W', ironWheel, 'L', "blockLapis", 'P', titaniumSheet, 'T', tank, 'S', sluice});
		RecipeHelper.addShapedRecipe(sawmill, new Object[] {" A ", "DWD", "IMI", 'A', ironAxe, 'D', "slabWood", 'M', baseWood, 'W', "logWood",  'I', "ingotCopper"});
		RecipeHelper.addShapedRecipe(fishSorter, new Object[] {"BPY", "GFA", "RCW", 'B', black, 'P', pearls, 'Y', yellow, 'G', green, 'F', fish, 'A', cyan, 'R', red, 'C', baseWood, 'W', white});
		RecipeHelper.addShapedRecipe(autodictionary, new Object[] {" B ", "FPF", "IMI", 'F', feather,  'P', pearls, 'M', baseWood, 'B', bookAndQuill, 'I', "ingotCopper"});
		if(Extra.ENDER_CONVERTER) RecipeHelper.addShapedRecipe(autodictionary, new Object[] {" B ", "FPF", "IMI", 'F', feather,  'P', enderPearl, 'M', baseWood, 'B', bookAndQuill, 'I', "ingotCopper"});
	}
	
	private void registerItemRecipes() {
		RecipeHelper.addMelting(new ItemStack(chalk), 825, FluidDictionary.getFluidStack(FluidDictionary.quicklime, 2500));
		RecipeHelper.addShapedRecipe(((ItemArmorFLUDD)fludd).build(), new Object[] {" E ", "PGP", "LUL", 'E', plasticLens, 'P', goldPlastic, 'G', transparent, 'L', scubaTank, 'U', life });
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {"LLN", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {"L  ", "L  ", "N  ", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {" L ", " L ", " N ", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {" N ", " L ", " L ", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {"N  ", "L  ", "L  ", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {"N  ", " L ", "  L", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(chalk), new Object[] {"L  ", " L ", "  N", 'L', "blockLimestone", 'N', white});
		RecipeHelper.addShapedRecipe(new ItemStack(filter), new Object[] {"W W", "WNW", " W ", 'W', wicker, 'N', filterer});
		RecipeHelper.addShapedRecipe(new ItemStack(paintbrush), new Object[] {" WW", " IW", "S  ", 'W', wool, 'I', "blockAluminum", 'S', sawmill});
		RecipeHelper.addShapedRecipe(new ItemStack(turbineCopper), new Object[] {" I ", "ISI", " I ", 'I', "ingotCopper", 'S', "slabWood"});
		RecipeHelper.addShapedRecipe(new ItemStack(turbineAluminum), new Object[] {" I ", "ISI", " I ", 'I', "ingotAluminum", 'S', stoneSlab});	
		RecipeHelper.addShapedRecipe(new ItemStack(turbineTitanium), new Object[] {" I ", "ISI", " I ", 'I', "ingotTitanium", 'S', quartzSlab});	
		RecipeHelper.addShapelessRecipe(plan, new Object[] {blue, black, paper, blue});
	}
}
