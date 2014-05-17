package mariculture.core;

import java.util.ArrayList;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockFluidMari;
import mariculture.core.blocks.BlockGlass;
import mariculture.core.blocks.BlockGround;
import mariculture.core.blocks.BlockLimestone;
import mariculture.core.blocks.BlockMachine;
import mariculture.core.blocks.BlockMachineMulti;
import mariculture.core.blocks.BlockMetal;
import mariculture.core.blocks.BlockPearlBlock;
import mariculture.core.blocks.BlockRenderedMachine;
import mariculture.core.blocks.BlockRenderedMachineMulti;
import mariculture.core.blocks.BlockRock;
import mariculture.core.blocks.BlockTank;
import mariculture.core.blocks.BlockTicking;
import mariculture.core.blocks.BlockTransparent;
import mariculture.core.blocks.BlockWater;
import mariculture.core.blocks.BlockWood;
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.handlers.ClientFMLEvents;
import mariculture.core.handlers.EnvironmentHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.ModulesHandler;
import mariculture.core.handlers.OreDicHandler;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.handlers.ServerFMLEvents;
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
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.RockMeta;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileBookshelf;
import mariculture.core.tile.TileCrucible;
import mariculture.core.tile.TileIngotCaster;
import mariculture.core.tile.TileOyster;
import mariculture.core.tile.TileTankBlock;
import mariculture.core.tile.TileVat;
import mariculture.core.tile.TileVoidBottle;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.FluidMari;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Core extends RegistrationModule {	
	public static Block rocks;
	public static Block limestone;
	public static Block metals;
	public static Block pearlBlock;
	public static Block glass;
	public static Block woods;
	public static Block machines;
	public static Block multiMachines;
	public static Block renderedMachines;
	public static Block renderedMultiMachines;
	public static Block air;
	public static Block tanks;
	public static Block sands;
	public static Block ticking;
	public static Block transparent;
	public static Block water;

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
	public static Item can;
	public static Item bucket;
	
	@Override
	public void registerHandlers() {
		OreDicHandler.registerWildCards();
		MaricultureHandlers.smelter = new LiquifierHandler();
		MaricultureHandlers.casting = new IngotCastingHandler();
		MaricultureHandlers.vat = new VatHandler();
		MaricultureHandlers.anvil = new TileAnvil();
		MaricultureHandlers.upgrades = new UpgradeHandler();
		MaricultureHandlers.modules = new ModulesHandler();
		MaricultureHandlers.environment = new EnvironmentHandler();
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new WorldGenHandler(), 1);
		MinecraftForge.EVENT_BUS.register(new GuiItemToolTip());
		MinecraftForge.EVENT_BUS.register(new OreDicHandler());
		FMLCommonHandler.instance().bus().register(new ServerFMLEvents());
		FMLCommonHandler.instance().bus().register(new ClientFMLEvents());
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
		rocks = new BlockRock().setStepSound(Block.soundTypeStone).setResistance(2F).setBlockName("rocks");
		limestone = new BlockLimestone().setStepSound(Block.soundTypeStone).setResistance(1F).setBlockName("limestone");
		metals = new BlockMetal().setStepSound(Block.soundTypeMetal).setResistance(5F).setBlockName("metals");
		pearlBlock = new BlockPearlBlock("pearlBlock_").setStepSound(Block.soundTypeStone).setResistance(1.5F).setBlockName("pearl.block");
		
		machines = new BlockMachine().setStepSound(Block.soundTypeWood).setResistance(10F).setBlockName("machines.single");
		multiMachines = new BlockMachineMulti().setStepSound(Block.soundTypeStone).setResistance(20F).setBlockName("machines.multi");
		renderedMachines = new BlockRenderedMachine().setStepSound(Block.soundTypeMetal).setResistance(1F).setHardness(1F).setBlockName("machines.single.rendered");
		renderedMultiMachines = new BlockRenderedMachineMulti().setStepSound(Block.soundTypeMetal).setResistance(3F).setHardness(3F).setBlockName("machines.multi.rendered");
		glass = new BlockGlass().setStepSound(Block.soundTypeGlass).setResistance(5F).setBlockName("glass");
		air = new BlockAir().setBlockName("air");
		woods = new BlockWood().setStepSound(Block.soundTypeWood).setBlockName("woods").setHardness(2.0F);
		tanks = new BlockTank().setStepSound(Block.soundTypeGlass).setBlockName("tanks").setHardness(1F);
		sands = new BlockGround().setBlockName("sands").setHardness(1F);
		transparent = new BlockTransparent().setStepSound(Block.soundTypePiston).setBlockName("transparent").setHardness(1F);
		ticking = new BlockTicking().setStepSound(Block.soundTypeCloth).setHardness(0.05F).setBlockName("ticking");
		water = new BlockWater().setStepSound(Block.soundTypeSnow).setHardness(10F).setBlockName("water");
		RegistryHelper.registerBlocks(new Block[] { 
				rocks, limestone, water, metals, sands, woods, glass, transparent,  pearlBlock, 
				machines, multiMachines, renderedMultiMachines, renderedMachines, ticking, tanks, air });
		RegistryHelper.registerTiles(new Class[] { 
				TileAirPump.class, TileCrucible.class, TileBookshelf.class, TileTankBlock.class, TileVat.class, 
				TileAnvil.class, TileIngotCaster.class, TileVoidBottle.class, TileOyster.class});
	}
	
	ToolMaterial brick = EnumHelper.addToolMaterial("BRICK", 1, 1000, 3.0F, 1.2F, 12);
	@Override
	public void registerItems() {
		materials = new ItemMaterial().setUnlocalizedName("materials");
		crafting = new ItemCrafting().setUnlocalizedName("crafting");
		pearls = new ItemPearl().setUnlocalizedName("pearls");
		food = new ItemFood().setUnlocalizedName("food");
		upgrade = new ItemUpgrade().setUnlocalizedName("upgrade");
		bottles = new ItemFluidContainer().setUnlocalizedName("fluids");
		hammer = new ItemHammer(brick).setUnlocalizedName("hammer");
		ladle = new ItemFluidStorage(MetalRates.INGOT).setUnlocalizedName("ladle");
		bucket = new ItemFluidStorage(8000).setUnlocalizedName("bucket.titanium");
		batteryCopper = new ItemBattery(10000, 100, 250).setUnlocalizedName("battery.copper");
		batteryTitanium = new ItemBattery(100000, 1000, 2500).setUnlocalizedName("battery.titanium");
		worked = new ItemWorked().setUnlocalizedName("worked");
		RegistryHelper.registerItems(new Item[] { 
				materials, crafting, pearls, food, upgrade, bottles, hammer, 
				ladle, bucket, batteryCopper, batteryTitanium, worked });
	}
	
	@Override
	public void registerOther() {
		registerBiomes();
		registerEntities();
		registerLiquids();
		registerPearls();
		addToOreDictionary();
		MaricultureTab.tabMariculture.icon = new ItemStack(pearls, 1, PearlColor.WHITE);
	}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", EntityIds.FAKE_ITEM, Mariculture.instance, 80, 3, false);
	}

	private void registerLiquids() {
		highPressureWater = new FluidMari(Fluids.hp_water, -1);
        if (!FluidRegistry.registerFluid(highPressureWater))
        	highPressureWater = FluidRegistry.getFluid(Fluids.hp_water);
        highPressureWaterBlock = new BlockFluidMari(highPressureWater, Material.water).setBlockName("highPressureWater");
        GameRegistry.registerBlock(highPressureWaterBlock, "Mariculture_highPressureWaterBlock");
        highPressureWater.setBlock(highPressureWaterBlock);
        registerHeatBottle(Fluids.hp_water, 1000, FluidContainerMeta.BOTTLE_HP_WATER);
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
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.taiga, Salinity.FRESH, 5);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.taigaHills, Salinity.FRESH, 4);
		
		//1.7 Biomes
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.coldTaiga, Salinity.FRESH, 1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.coldTaigaHills, Salinity.FRESH, 0);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.coldBeach, Salinity.BRACKISH, 1);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.stoneBeach, Salinity.BRACKISH, 15);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.deepOcean, Salinity.SALINE, 2);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.extremeHillsPlus, Salinity.FRESH, 5);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.megaTaiga, Salinity.FRESH, 3);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.megaTaigaHills, Salinity.FRESH, 2);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.birchForest, Salinity.FRESH, 10);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.birchForestHills, Salinity.FRESH, 8);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.roofedForest, Salinity.FRESH, 11);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.savanna, Salinity.FRESH, 23);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.savannaPlateau, Salinity.FRESH, 22);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.mesa, Salinity.FRESH, 40);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.mesaPlateau_F, Salinity.FRESH, 38);
		MaricultureHandlers.environment.addEnvironment(BiomeGenBase.mesaPlateau, Salinity.FRESH, 39);
	}
	
	private void registerPearls() {
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLACK), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLUE), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BROWN), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GOLD), 5);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GREEN), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.ORANGE), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PINK), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PURPLE), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.RED), 10);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.SILVER), 6);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.WHITE), 7);
		PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.YELLOW), 6);
		PearlGenHandler.addPearl(new ItemStack(Blocks.sand), 15);
		if(Extra.GEN_ENDER_PEARLS) {
			PearlGenHandler.addPearl(new ItemStack(Items.ender_pearl), 1);
		}
	}

	private void addToOreDictionary() {	
		OreDicHelper.add("glass", new ItemStack(Blocks.glass));
		OreDicHelper.add("ingotIron", new ItemStack(Items.iron_ingot));
		OreDicHelper.add("ingotGold", new ItemStack(Items.gold_ingot));
		OreDicHelper.add("blockIron", new ItemStack(Blocks.iron_block));
		OreDicHelper.add("blockGold", new ItemStack(Blocks.gold_block));
		OreDicHelper.add("blockLapis", new ItemStack(Blocks.lapis_block));
		OreDicHelper.add("blockCoal", new ItemStack(Blocks.coal_block));
		OreDicHelper.add("blockRedstone", new ItemStack(Blocks.redstone_block));
		OreDicHelper.add("dustRedstone", new ItemStack(Items.redstone));
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(limestone, 1, LimestoneMeta.RAW));
		OreDictionary.registerOre("oreCopper", new ItemStack(rocks, 1, RockMeta.COPPER));
		OreDictionary.registerOre("oreAluminum", new ItemStack(rocks, 1, RockMeta.BAUXITE));
		OreDictionary.registerOre("oreRutile", new ItemStack(rocks, 1, RockMeta.RUTILE));
		
		OreDictionary.registerOre("blockAluminum", new ItemStack(metals, 1, MetalMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockCopper", new ItemStack(metals, 1, MetalMeta.COPPER_BLOCK));
		OreDictionary.registerOre("blockMagnesium", new ItemStack(metals, 1, MetalMeta.MAGNESIUM_BLOCK));
		OreDictionary.registerOre("blockRutile", new ItemStack(metals, 1, MetalMeta.RUTILE_BLOCK));
		OreDictionary.registerOre("blockTitanium", new ItemStack(metals, 1, MetalMeta.TITANIUM_BLOCK));
		
		OreDictionary.registerOre("foodSalt", new ItemStack(materials, 1, MaterialsMeta.DUST_SALT));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		OreDictionary.registerOre("ingotCopper", new ItemStack(materials, 1, MaterialsMeta.INGOT_COPPER));
		OreDictionary.registerOre("ingotMagnesium", new ItemStack(materials, 1, MaterialsMeta.INGOT_MAGNESIUM));
		OreDictionary.registerOre("ingotRutile", new ItemStack(materials, 1, MaterialsMeta.INGOT_RUTILE));
		OreDictionary.registerOre("ingotTitanium", new ItemStack(materials, 1, MaterialsMeta.INGOT_TITANIUM));
	}

	private void addFluids() {	
	//Normal Fluids
		Fluids.natural_gas = addFluid("gas.natural", naturalGas, 2000, FluidContainerMeta.BOTTLE_GAS);
		
	//Molten Mari + Vanilla Fluids
		Fluids.quicklime = addFluid(("quicklime"), quicklime, 1000, FluidContainerMeta.BOTTLE_QUICKLIME);
		Fluids.salt = addFluid("salt.molten", moltenSalt, 1000, FluidContainerMeta.BOTTLE_SALT);
		Fluids.glass = addFluid("glass.molten", moltenGlass, 1000, FluidContainerMeta.BOTTLE_GLASS);
		Fluids.aluminum = addFluid("aluminum.molten", moltenAluminum, MetalRates.ORE, FluidContainerMeta.BOTTLE_ALUMINUM);
		Fluids.magnesium = addFluid("magnesium.molten", moltenMagnesium, MetalRates.ORE, FluidContainerMeta.BOTTLE_MAGNESIUM);
		Fluids.titanium = addFluid("titanium.molten", moltenTitanium, MetalRates.ORE, FluidContainerMeta.BOTTLE_TITANIUM);
		Fluids.copper = addFluid("copper.molten", moltenCopper, MetalRates.ORE, FluidContainerMeta.BOTTLE_COPPER);
		Fluids.rutile = addFluid("rutile.molten", moltenRutile, MetalRates.ORE, FluidContainerMeta.BOTTLE_RUTILE);
		
	//Vanilla Fluids
		Fluids.iron = addFluid("iron.molten", moltenIron, MetalRates.ORE, FluidContainerMeta.BOTTLE_IRON);
		Fluids.gold = addFluid("gold.molten", moltenGold, MetalRates.ORE, FluidContainerMeta.BOTTLE_GOLD);

		//Modded Fluids
		Fluids.tin = addFluid("tin.molten", moltenTin, MetalRates.ORE, FluidContainerMeta.BOTTLE_TIN);
		Fluids.lead = addFluid("lead.molten", moltenLead, MetalRates.ORE, FluidContainerMeta.BOTTLE_LEAD);
		Fluids.silver = addFluid("silver.molten", moltenSilver, MetalRates.ORE, FluidContainerMeta.BOTTLE_SILVER);
		Fluids.nickel = addFluid("nickel.molten", moltenNickel, MetalRates.ORE, FluidContainerMeta.BOTTLE_NICKEL);
		Fluids.bronze = addFluid("bronze.molten", moltenBronze, MetalRates.ORE, FluidContainerMeta.BOTTLE_BRONZE);
		Fluids.steel =	addFluid("steel.molten", moltenSteel, MetalRates.ORE, FluidContainerMeta.BOTTLE_STEEL);
		Fluids.electrum = addFluid("electrum.molten", moltenElectrum, MetalRates.ORE, FluidContainerMeta.BOTTLE_ELECTRUM);
		
		registerVanillaBottle(Fluids.natural_gas, 1000, FluidContainerMeta.BOTTLE_NORMAL_GAS);
		registerHeatBottle("water", 2000, FluidContainerMeta.BOTTLE_WATER);
		registerHeatBottle("lava", 2000, FluidContainerMeta.BOTTLE_LAVA);
	}
	
	public static void registerHeatBottle(String fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(fluid, vol), 
				new ItemStack(Core.bottles, 1, meta), new ItemStack(Core.bottles, 1, FluidContainerMeta.BOTTLE_EMPTY));
	}
	 
	public static void registerVanillaBottle(String fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(fluid, vol), 
				new ItemStack(Core.bottles, 1, meta), new ItemStack(Items.glass_bottle));
	}
	
	public static String addFluid(String name, Fluid globalFluid, int volume, int bottleMeta) {
		if (!Fluids.instance.fluidExists(name)) {
			globalFluid = new FluidMari(name, bottleMeta).setUnlocalizedName(name);
			FluidRegistry.registerFluid(globalFluid);
			Fluids.instance.addFluid(name, globalFluid);
		}
		
		Fluid fluid = Fluids.getFluid(name);
		
		if(volume != -1) {
			registerHeatBottle(fluid.getName(), volume, bottleMeta);
		}
		
		return Fluids.getFluid(name).getName();
	}

	@Override
	public void registerRecipes() {
		addFluids();
		Recipes.add();
	}
	
	@Override
	public void postInit() {
		mariculture.core.helpers.FluidHelper.setup();
		RecipesSmelting.postAdd();
		super.postInit();
	}
}
