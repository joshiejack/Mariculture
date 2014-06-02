package mariculture.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mariculture.Mariculture;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockAirItem;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.BlockDoubleItem;
import mariculture.core.blocks.BlockFluidMari;
import mariculture.core.blocks.BlockGlass;
import mariculture.core.blocks.BlockGlassItem;
import mariculture.core.blocks.BlockGround;
import mariculture.core.blocks.BlockGroundItem;
import mariculture.core.blocks.BlockOre;
import mariculture.core.blocks.BlockOreItem;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.BlockPearlBrick;
import mariculture.core.blocks.BlockPearlBrickItem;
import mariculture.core.blocks.BlockSingle;
import mariculture.core.blocks.BlockSingleItem;
import mariculture.core.blocks.BlockTank;
import mariculture.core.blocks.BlockTankItem;
import mariculture.core.blocks.BlockTransparent;
import mariculture.core.blocks.BlockTransparentItem;
import mariculture.core.blocks.BlockUtil;
import mariculture.core.blocks.BlockUtilItem;
import mariculture.core.blocks.BlockWood;
import mariculture.core.blocks.BlockWoodItem;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileBlockCaster;
import mariculture.core.blocks.TileBookshelf;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.blocks.TileNuggetCaster;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileTankBlock;
import mariculture.core.blocks.TileVat;
import mariculture.core.blocks.TileVoidBottle;
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.handlers.BiomeTypeHandler;
import mariculture.core.handlers.CastingHandler;
import mariculture.core.handlers.EnvironmentHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.ModulesHandler;
import mariculture.core.handlers.OreDicHandler;
import mariculture.core.handlers.PlayerTrackerHandler;
import mariculture.core.handlers.UpgradeHandler;
import mariculture.core.handlers.VatHandler;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.items.ItemCrafting;
import mariculture.core.items.ItemFluidContainer;
import mariculture.core.items.ItemFluidStorage;
import mariculture.core.items.ItemFood;
import mariculture.core.items.ItemHammer;
import mariculture.core.items.ItemMaterial;
import mariculture.core.items.ItemPearl;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.MultiMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RenderMeta;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.WoodMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMari;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Core extends RegistrationModule {
	public static Block ores;
	public static Block machines;
	public static Block rendered;
	public static Block oyster;
	public static Block pearl;
	public static Block multi;
	public static Block glass;
	public static Block air;
	public static Block wood;
	public static Block tanks;
	public static Block dirt;
	public static Block transparent;

	public static Fluid moltenAluminum;
	public static Fluid moltenTitanium;
	public static Fluid moltenIron;
	public static Fluid moltenGold;
	public static Fluid moltenCopper;
	public static Fluid moltenTin;
	public static Fluid moltenMagnesium;
	public static Fluid moltenBronze;
	public static Fluid moltenLead;
	public static Fluid moltenSilver;
	public static Fluid moltenSteel;
	public static Fluid moltenNickel;
	public static Fluid moltenRutile;
	public static Fluid moltenGlass;
	public static Fluid moltenSalt;
	public static Fluid moltenElectrum;
	public static Fluid naturalGas;
	public static Fluid quicklime;
	
	public static Fluid highPressureWater;
	public static Block highPressureWaterBlock;

	public static Item bottles;
	public static Item materials;
	public static Item crafting;
	public static Item batteryTitanium;
	public static Item batteryCopper;
	public static Item food;
	public static Item upgrade;
	public static Item pearls;
	public static Item hammer;
	public static Item worked;
	public static Item ladle;
	public static Item bucket;
	
	@Override
	public void registerHandlers() {
		OreDicHandler.registerWildCards();
		MaricultureHandlers.biomeType = new BiomeTypeHandler();
		MaricultureHandlers.smelter = new LiquifierHandler();
		MaricultureHandlers.casting = new CastingHandler();
		MaricultureHandlers.vat = new VatHandler();
		MaricultureHandlers.anvil = new TileAnvil();
		MaricultureHandlers.upgrades = new UpgradeHandler();
		MaricultureHandlers.modules = new ModulesHandler();
		MaricultureHandlers.environment = new EnvironmentHandler();
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new WorldGenHandler());
		MinecraftForge.EVENT_BUS.register(new GuiItemToolTip());
		MinecraftForge.EVENT_BUS.register(new OreDicHandler());
		GameRegistry.registerPlayerTracker(new PlayerTrackerHandler());
		if(RetroGeneration.ENABLED)
			MinecraftForge.EVENT_BUS.register(new RetroGen());
		
		//Initalise our Side Helper
		List<Integer> sides = new ArrayList<Integer>();
		for(int i = 0; i < 6; i++) {
			sides.add(i);
		}
		
		BlockTransferHelper.sides = sides;
	}

	@Override
	public void registerBlocks() {
		ores = new BlockOre(BlockIds.ores).setStepSound(Block.soundStoneFootstep).setResistance(5F).setUnlocalizedName("oreBlocks");
		machines = new BlockUtil(BlockIds.util).setStepSound(Block.soundStoneFootstep).setHardness(2F).setResistance(5F).setUnlocalizedName("utilBlocks");
		multi = new BlockDouble(BlockIds.doubleBlocks).setStepSound(Block.soundMetalFootstep).setResistance(3F).setUnlocalizedName("doubleBlocks").setHardness(3F);
		rendered = new BlockSingle(BlockIds.singleBlocks).setStepSound(Block.soundStoneFootstep).setHardness(1F).setResistance(1F).setUnlocalizedName("customBlocks");
		oyster = new BlockOyster(BlockIds.oyster).setStepSound(Block.soundStoneFootstep).setHardness(10F).setResistance(50F).setUnlocalizedName("oysterBlock").setLightValue(0.4F);
		pearl = new BlockPearlBrick(BlockIds.pearlBlock, "pearlBlock_").setUnlocalizedName("pearlBlock");
		glass = new BlockGlass(BlockIds.glassBlocks).setStepSound(Block.soundGlassFootstep).setResistance(10F).setUnlocalizedName("glassBlocks");
		air = new BlockAir(BlockIds.airBlocks).setUnlocalizedName("airBlocks");
		wood = new BlockWood(BlockIds.woodBlocks).setUnlocalizedName("woodBlocks").setHardness(2.0F);
		tanks = new BlockTank(BlockIds.tankBlocks).setUnlocalizedName("tankBlocks").setHardness(1F);
		dirt = new BlockGround(BlockIds.groundBlocks).setUnlocalizedName("groundBlocks").setHardness(1F);
		transparent = new BlockTransparent(BlockIds.transparentBlocks).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("transparentBlocks").setHardness(1F);
		

		Item.itemsList[BlockIds.ores] = new BlockOreItem(BlockIds.ores - 256, ores).setUnlocalizedName("oreBlocks");
		Item.itemsList[BlockIds.util] = new BlockUtilItem(BlockIds.util - 256, machines).setUnlocalizedName("utilBlocks");
		Item.itemsList[BlockIds.singleBlocks] = new BlockSingleItem(BlockIds.singleBlocks - 256, rendered).setUnlocalizedName("customBlocks");
		Item.itemsList[BlockIds.doubleBlocks] = new BlockDoubleItem(BlockIds.doubleBlocks - 256, multi).setUnlocalizedName("doubleBlocks");
		Item.itemsList[BlockIds.pearlBlock] = new BlockPearlBrickItem(BlockIds.pearlBlock - 256, Core.pearl).setUnlocalizedName("pearlBlock");
		
		Item.itemsList[BlockIds.glassBlocks] = new BlockGlassItem(BlockIds.glassBlocks - 256, glass).setUnlocalizedName("glassBlocks");
		Item.itemsList[BlockIds.airBlocks] = new BlockAirItem(BlockIds.airBlocks - 256, air).setUnlocalizedName("airBlocks");
		Item.itemsList[BlockIds.woodBlocks] = new BlockWoodItem(BlockIds.woodBlocks - 256, wood).setUnlocalizedName("woodBlocks");
		Item.itemsList[BlockIds.tankBlocks] = new BlockTankItem(BlockIds.tankBlocks - 256, tanks).setUnlocalizedName("tankBlocks");
		Item.itemsList[BlockIds.groundBlocks] = new BlockGroundItem(BlockIds.groundBlocks - 256, dirt).setUnlocalizedName("groundBlocks");
		Item.itemsList[BlockIds.transparentBlocks] = new BlockTransparentItem(BlockIds.transparentBlocks - 256, transparent).setUnlocalizedName("transparentBlocks");
		

		GameRegistry.registerBlock(Core.oyster, "Mariculture_oysterBlock");
		
		GameRegistry.registerTileEntity(TileAirPump.class, "tileEntityAirPump");
		GameRegistry.registerTileEntity(TileOyster.class, "tileEntityOyster");
		GameRegistry.registerTileEntity(TileLiquifier.class, "tileEntityLiquifier");
		GameRegistry.registerTileEntity(TileBookshelf.class, "tileBookshelf");
		GameRegistry.registerTileEntity(TileTankBlock.class, "tileTankBlock");
		GameRegistry.registerTileEntity(TileVat.class, "tileVatBlock");
		GameRegistry.registerTileEntity(TileAnvil.class, "tileBlacksmithAnvil");
		GameRegistry.registerTileEntity(TileIngotCaster.class, "tileIngotCaster");
		GameRegistry.registerTileEntity(TileVoidBottle.class, "tileVoidBottle");
		GameRegistry.registerTileEntity(TileNuggetCaster.class, "tileNuggetCaster");
		GameRegistry.registerTileEntity(TileBlockCaster.class, "tileBlockCaster");

		
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.ALUMINUM_BLOCK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.BAUXITE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.COPPER, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.COPPER_BLOCK, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.LIMESTONE, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.LIMESTONE_BRICK, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.LIMESTONE_CHISELED, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.LIMESTONE_SMOOTH, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.MAGNESIUM_BLOCK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.RUTILE, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.TITANIUM_BLOCK, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.BASE_BRICK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(ores, OresMeta.BASE_IRON, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(machines, MachineMeta.LIQUIFIER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.ANVIL_1, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.ANVIL_2, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.ANVIL_3, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.ANVIL_4, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.AIR_PUMP, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(rendered, RenderMeta.INGOT_CASTER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(multi, MultiMeta.VAT, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(machines, MachineMeta.BOOKSHELF, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(dirt, GroundMeta.BUBBLES, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(wood, WoodMeta.BASE_WOOD, "axe", 1);

		RegistryHelper.register(new Object[] { ores, pearl, oyster, machines, multi,
				rendered, glass, air, wood, tanks, dirt, transparent });
	}

	EnumToolMaterial brick = EnumHelper.addToolMaterial("BRICK", 1, 1000, 3.0F, 1.2F, 12);

	@Override
	public void registerItems() {
		materials = new ItemMaterial(ItemIds.metals).setUnlocalizedName("materials");
		crafting = new ItemCrafting(ItemIds.items).setUnlocalizedName("craftingItems");
		batteryCopper = new ItemBattery(ItemIds.batteryCopper, 10000, 100, 250).setUnlocalizedName("batteryCopper");
		batteryTitanium = new ItemBattery(ItemIds.batteryTitanium, 100000, 1000, 2500).setUnlocalizedName("batteryTitanium");
		food = new ItemFood(ItemIds.food).setUnlocalizedName("food");
		upgrade = new ItemUpgrade(ItemIds.upgrade).setUnlocalizedName("upgrade");
		pearls = new ItemPearl(ItemIds.pearl).setUnlocalizedName("pearls");
		bottles = new ItemFluidContainer(ItemIds.liquidContainers).setUnlocalizedName("liquidContainers");
		hammer = new ItemHammer(ItemIds.hammer, brick).setUnlocalizedName("hammer");
		worked = new ItemWorked(ItemIds.worked).setUnlocalizedName("worked");
		ladle = new ItemFluidStorage(ItemIds.ladle, MetalRates.INGOT).setUnlocalizedName("ladle");
		bucket = new ItemFluidStorage(ItemIds.bucket, 8000).setUnlocalizedName("titaniumBucket");
		
		MinecraftForge.setToolClass(hammer, "pickaxe", 0);
		
		RegistryHelper.register(new Object[] { materials, crafting, batteryTitanium, food, upgrade, pearls, 
				bottles, hammer, worked, batteryCopper, ladle, bucket });
	}
	
	@Override
	public void registerOther() {
		registerBiomes();
		registerEntities();
		registerLiquids();
		addToOreDictionary();
		if(!Modules.isActive(Modules.worldplus) && WorldGeneration.DEEP_OCEAN) {
			addDeepOcean();
		}

		MaricultureTab.tabMariculture.icon = new ItemStack(pearls, 1, PearlColor.WHITE);
	}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", EntityIds.FAKE_ITEM, Mariculture.instance, 80, 3, false);
	}
	
	private void addDeepOcean() {
		try {
			Field field = BiomeGenBase.class.getField("ocean");
			if(field == null) field = BiomeGenBase.class.getField("field_76771_b");
			Object newValue = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setMinMaxHeight(-1.8F, 0.4F);
		    field.setAccessible(true);
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    field.set(null, newValue);
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Mariculture failed to adjust the ocean depth");
		}
	}

	private void registerLiquids() {
		highPressureWater = new FluidMari(FluidDictionary.hp_water, -1);
        if (!FluidRegistry.registerFluid(highPressureWater))
        	highPressureWater = FluidRegistry.getFluid(FluidDictionary.hp_water);
        highPressureWaterBlock = new BlockFluidMari(BlockIds.highPressureWater, highPressureWater, Material.water).setUnlocalizedName("highPressureWater");
        GameRegistry.registerBlock(highPressureWaterBlock, "Mariculture_highPressureWaterBlock");
        if(highPressureWater.getBlockID() == -1)
        highPressureWater.setBlockID(highPressureWaterBlock);
        registerHeatBottle(FluidDictionary.hp_water, 1000, FluidContainerMeta.BOTTLE_HP_WATER);
	}

	private void addToOreDictionary() {	
		OreDicHelper.add("glass", new ItemStack(Block.glass));
		OreDicHelper.add("ingotIron", new ItemStack(Item.ingotIron));
		OreDicHelper.add("ingotGold", new ItemStack(Item.ingotGold));
		OreDicHelper.add("blockIron", new ItemStack(Block.blockIron));
		OreDicHelper.add("blockGold", new ItemStack(Block.blockGold));
		OreDicHelper.add("blockLapis", new ItemStack(Block.blockLapis));
		OreDicHelper.add("blockCoal", new ItemStack(Block.coalBlock));
		OreDicHelper.add("blockRedstone", new ItemStack(Block.blockRedstone));
		OreDicHelper.add("dustRedstone", new ItemStack(Item.redstone));
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(ores, 1, OresMeta.LIMESTONE));
		OreDictionary.registerOre("limestone", new ItemStack(ores, 1, OresMeta.LIMESTONE));
		OreDictionary.registerOre("oreCopper", new ItemStack(ores, 1, OresMeta.COPPER));
		OreDictionary.registerOre("oreAluminum", new ItemStack(ores, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreAluminium", new ItemStack(ores, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreNaturalAluminum", new ItemStack(ores, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreRutile", new ItemStack(ores, 1, OresMeta.RUTILE));
		
		OreDictionary.registerOre("blockAluminum", new ItemStack(ores, 1, OresMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockAluminium", new ItemStack(ores, 1, OresMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockNaturalAluminum", new ItemStack(ores, 1, OresMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockCopper", new ItemStack(ores, 1, OresMeta.COPPER_BLOCK));
		OreDictionary.registerOre("blockMagnesium", new ItemStack(ores, 1, OresMeta.MAGNESIUM_BLOCK));
		OreDictionary.registerOre("blockTitanium", new ItemStack(ores, 1, OresMeta.TITANIUM_BLOCK));
		OreDictionary.registerOre("blockRutile", new ItemStack(ores, 1, OresMeta.RUTILE_BLOCK));
		
		OreDictionary.registerOre("foodSalt", new ItemStack(materials, 1, MaterialsMeta.DUST_SALT));
		OreDictionary.registerOre("dustSalt", new ItemStack(materials, 1, MaterialsMeta.DUST_SALT));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		OreDictionary.registerOre("ingotAluminium", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		OreDictionary.registerOre("ingotNaturalAluminum", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		OreDictionary.registerOre("ingotCopper", new ItemStack(materials, 1, MaterialsMeta.INGOT_COPPER));
		OreDictionary.registerOre("ingotMagnesium", new ItemStack(materials, 1, MaterialsMeta.INGOT_MAGNESIUM));
		OreDictionary.registerOre("ingotRutile", new ItemStack(materials, 1, MaterialsMeta.INGOT_RUTILE));
		OreDictionary.registerOre("ingotTitanium", new ItemStack(materials, 1, MaterialsMeta.INGOT_TITANIUM));
		
		OreDictionary.registerOre("nuggetIron", new ItemStack(materials, 1, MaterialsMeta.NUGGET_IRON));
		OreDictionary.registerOre("nuggetAluminum", new ItemStack(materials, 1, MaterialsMeta.NUGGET_ALUMINUM));
		OreDictionary.registerOre("nuggetAluminium", new ItemStack(materials, 1, MaterialsMeta.NUGGET_ALUMINUM));
		OreDictionary.registerOre("nuggetNaturalAluminum", new ItemStack(materials, 1, MaterialsMeta.NUGGET_ALUMINUM));
		OreDictionary.registerOre("nuggetCopper", new ItemStack(materials, 1, MaterialsMeta.NUGGET_COPPER));
		OreDictionary.registerOre("nuggetMagnesium", new ItemStack(materials, 1, MaterialsMeta.NUGGET_MAGNESIUM));
		OreDictionary.registerOre("nuggetRutile", new ItemStack(materials, 1, MaterialsMeta.NUGGET_RUTILE));
		OreDictionary.registerOre("nuggetTitanium", new ItemStack(materials, 1, MaterialsMeta.NUGGET_TITANIUM));
		
		OreDictionary.registerOre("dyeYellow", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW));
		OreDictionary.registerOre("dyeRed", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED));
		OreDictionary.registerOre("dyeBrown", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN));
		OreDictionary.registerOre("dyeGreen", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN));
		OreDictionary.registerOre("dyeWhite", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_WHITE));
		OreDictionary.registerOre("dyeBlue", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BLUE));
	}

	private void addFluids() {	
	//Normal Fluids
		FluidDictionary.natural_gas = addFluid("gas.natural", naturalGas, 2000, FluidContainerMeta.BOTTLE_GAS);
		
	//Molten Mari + Vanilla Fluids
		FluidDictionary.quicklime = addFluid(("quicklime"), quicklime, 1000, FluidContainerMeta.BOTTLE_QUICKLIME);
		FluidDictionary.salt = addFluid("salt.molten", moltenSalt, 1000, FluidContainerMeta.BOTTLE_SALT);
		FluidDictionary.glass = addFluid("glass.molten", moltenGlass, 1000, FluidContainerMeta.BOTTLE_GLASS);
		FluidDictionary.aluminum = addFluid("aluminum.molten", moltenAluminum, MetalRates.ORE, FluidContainerMeta.BOTTLE_ALUMINUM);
		FluidDictionary.magnesium = addFluid("magnesium.molten", moltenMagnesium, MetalRates.ORE, FluidContainerMeta.BOTTLE_MAGNESIUM);
		FluidDictionary.titanium = addFluid("titanium.molten", moltenTitanium, MetalRates.ORE, FluidContainerMeta.BOTTLE_TITANIUM);
		FluidDictionary.copper = addFluid("copper.molten", moltenCopper, MetalRates.ORE, FluidContainerMeta.BOTTLE_COPPER);
		FluidDictionary.rutile = addFluid("rutile.molten", moltenRutile, MetalRates.ORE, FluidContainerMeta.BOTTLE_RUTILE);
		
	//Vanilla Fluids
		FluidDictionary.iron = addFluid("iron.molten", moltenIron, MetalRates.ORE, FluidContainerMeta.BOTTLE_IRON);
		FluidDictionary.gold = addFluid("gold.molten", moltenGold, MetalRates.ORE, FluidContainerMeta.BOTTLE_GOLD);

		//Modded Fluids
		FluidDictionary.tin = addFluid("tin.molten", moltenTin, MetalRates.ORE, FluidContainerMeta.BOTTLE_TIN);
		FluidDictionary.lead = addFluid("lead.molten", moltenLead, MetalRates.ORE, FluidContainerMeta.BOTTLE_LEAD);
		FluidDictionary.silver = addFluid("silver.molten", moltenSilver, MetalRates.ORE, FluidContainerMeta.BOTTLE_SILVER);
		FluidDictionary.nickel = addFluid("nickel.molten", moltenNickel, MetalRates.ORE, FluidContainerMeta.BOTTLE_NICKEL);
		FluidDictionary.bronze = addFluid("bronze.molten", moltenBronze, MetalRates.ORE, FluidContainerMeta.BOTTLE_BRONZE);
		FluidDictionary.steel =	addFluid("steel.molten", moltenSteel, MetalRates.ORE, FluidContainerMeta.BOTTLE_STEEL);
		FluidDictionary.electrum = addFluid("electrum.molten", moltenElectrum, MetalRates.ORE, FluidContainerMeta.BOTTLE_ELECTRUM);
		
		registerVanillaBottle(FluidDictionary.natural_gas, 1000, FluidContainerMeta.BOTTLE_NORMAL_GAS);
		registerHeatBottle("water", 2000, FluidContainerMeta.BOTTLE_WATER);
		registerHeatBottle("lava", 2000, FluidContainerMeta.BOTTLE_LAVA);
	}
	
	public static void registerHeatBottle(String fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(fluid, vol), 
				new ItemStack(Core.bottles, 1, meta), new ItemStack(Core.bottles, 1, FluidContainerMeta.BOTTLE_EMPTY));
	}
	 
	public static void registerVanillaBottle(String fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(fluid, vol), 
				new ItemStack(Core.bottles, 1, meta), new ItemStack(Item.glassBottle));
	}
	
	public static String addFluid(String name, Fluid globalFluid, int volume, int bottleMeta) {
		if (!FluidDictionary.instance.fluidExists(name)) {
			globalFluid = new FluidMari(name, bottleMeta).setUnlocalizedName(name);
			FluidRegistry.registerFluid(globalFluid);
			FluidDictionary.instance.addFluid(name, globalFluid);
		}
		
		Fluid fluid = FluidDictionary.getFluid(name);
		
		if(volume != -1) {
			registerHeatBottle(fluid.getName(), volume, bottleMeta);
		}
		
		return FluidDictionary.getFluid(name).getName();
	}

	private void registerBiomes() {
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.beach, Salinity.BRACKISH, 25);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.desert, Salinity.FRESH, 45);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.desertHills, Salinity.FRESH, 45);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.extremeHills, Salinity.FRESH, 5);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.extremeHillsEdge, Salinity.FRESH, 7);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.forest, Salinity.FRESH, 10);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.forestHills, Salinity.FRESH, 8);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.frozenOcean, Salinity.SALINE, -1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.frozenRiver, Salinity.FRESH, -1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.hell, Salinity.FRESH, 80);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.iceMountains, Salinity.FRESH, -1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.icePlains, Salinity.FRESH, -1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.jungle, Salinity.FRESH, 25);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.jungleHills, Salinity.FRESH, 24);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.mushroomIsland, Salinity.FRESH, 15);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.mushroomIslandShore, Salinity.BRACKISH, 20);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.ocean, Salinity.SALINE, 4);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.plains, Salinity.FRESH, 10);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.river, Salinity.FRESH, 10);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.sky, Salinity.FRESH, 3);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.swampland, Salinity.FRESH, 8);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.taiga, Salinity.FRESH, 1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.taigaHills, Salinity.FRESH, 0);
	}
	
	@Override
	public void registerRecipes() {
		addFluids();
		Recipes.add();
	}
	
	@Override
	public void postInit() {
		RecipesSmelting.postAdd();
		super.postInit();
		
		//Keep Chisel Compatibility
		oreBlocks = ores;
	}
	
	@Deprecated
	public static Block oreBlocks;
}
