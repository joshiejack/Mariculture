package mariculture.core;

import java.util.ArrayList;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.BlockFluidMari;
import mariculture.core.blocks.BlockGlass;
import mariculture.core.blocks.BlockGround;
import mariculture.core.blocks.BlockLimestone;
import mariculture.core.blocks.BlockMachine;
import mariculture.core.blocks.BlockMachineMulti;
import mariculture.core.blocks.BlockMetal;
import mariculture.core.blocks.BlockPearlBlock;
import mariculture.core.blocks.BlockRock;
import mariculture.core.blocks.BlockSingle;
import mariculture.core.blocks.BlockTank;
import mariculture.core.blocks.BlockTransparent;
import mariculture.core.blocks.BlockWater;
import mariculture.core.blocks.BlockWood;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileBookshelf;
import mariculture.core.blocks.TileCrucible;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileTankBlock;
import mariculture.core.blocks.TileVat;
import mariculture.core.blocks.TileVoidBottle;
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.guide.GuideHandler;
import mariculture.core.guide.Guides;
import mariculture.core.handlers.BiomeTypeHandler;
import mariculture.core.handlers.ClientFMLEvents;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.ModulesHandler;
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
import mariculture.core.items.ItemGuide;
import mariculture.core.items.ItemHammer;
import mariculture.core.items.ItemMaterial;
import mariculture.core.items.ItemPearl;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.RockMeta;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMari;
import mariculture.factory.OreDicHandler;
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

public class Core extends Module {	
	public static Block rocks;
	public static Block limestone;
	public static Block metals;
	public static Block pearlBlock;
	public static Block pearlBrick;
	public static Block glass;
	public static Block woods;
	public static Block machines;
	public static Block multiMachines;
	public static Block renderedMachines;
	public static Block renderedMultiMachines;
	public static Block air;
	public static Block tanks;
	public static Block sands;
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

	public static Item liquidContainers;
	public static Item materials;
	public static Item craftingItem;
	public static Item batteryTitanium;
	public static Item batteryCopper;
	public static Item food;
	public static Item upgrade;
	public static Item pearls;
	public static Item hammer;
	public static Item worked;
	public static Item guides;
	public static Item ladle;
	public static Item can;
	public static Item bucket;
	
	@Override
	public void registerHandlers() {
		Guides.instance = new GuideHandler();
		OreDicHandler.registerWildCards();
		MaricultureHandlers.biomeType = new BiomeTypeHandler();
		MaricultureHandlers.smelter = new LiquifierHandler();
		MaricultureHandlers.casting = new IngotCastingHandler();
		MaricultureHandlers.vat = new VatHandler();
		MaricultureHandlers.anvil = new TileAnvil();
		MaricultureHandlers.upgrades = new UpgradeHandler();
		MaricultureHandlers.modules = new ModulesHandler();
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
		pearlBrick = new BlockPearlBlock("pearlBrick_").setStepSound(Block.soundTypeStone).setResistance(2F).setBlockName("pearl.brick");
		machines = new BlockMachine().setStepSound(Block.soundTypeWood).setResistance(10F).setBlockName("machines.single");
		multiMachines = new BlockMachineMulti().setStepSound(Block.soundTypeStone).setResistance(20F).setBlockName("machines.multi");
		
		//TODO: Move Rendered machines over to the block basing
		renderedMachines = new BlockSingle().setStepSound(Block.soundTypeMetal).setResistance(1F).setHardness(1F).setBlockName("machines.single.rendered");
		renderedMultiMachines = new BlockDouble().setStepSound(Block.soundTypeMetal).setResistance(3F).setHardness(3F).setBlockName("machines.multi.rendered");
		
		glass = new BlockGlass().setStepSound(Block.soundTypeGlass).setResistance(5F).setBlockName("glass");
		air = new BlockAir().setBlockUnbreakable().setBlockName("air");
		woods = new BlockWood().setStepSound(Block.soundTypeWood).setBlockName("woods").setHardness(2.0F);
		tanks = new BlockTank().setStepSound(Block.soundTypeGlass).setBlockName("tanks").setHardness(1F);
		sands = new BlockGround().setBlockName("sands").setHardness(1F);
		transparent = new BlockTransparent().setStepSound(Block.soundTypePiston).setBlockName("transparent").setHardness(1F);
		water = new BlockWater().setStepSound(Block.soundTypeSnow).setHardness(10F).setBlockName("water");
		
		GameRegistry.registerTileEntity(TileAirPump.class, "TileAirPump");
		GameRegistry.registerTileEntity(TileCrucible.class, "TileLiquifier");
		GameRegistry.registerTileEntity(TileBookshelf.class, "TileBookshelf");
		GameRegistry.registerTileEntity(TileTankBlock.class, "TileTankBlock");
		GameRegistry.registerTileEntity(TileVat.class, "TileVat");
		GameRegistry.registerTileEntity(TileAnvil.class, "TileAnvil");
		GameRegistry.registerTileEntity(TileIngotCaster.class, "TileIngotCaster");
		GameRegistry.registerTileEntity(TileVoidBottle.class, "TileVoidBottle");
		GameRegistry.registerTileEntity(TileOyster.class, "TileOyster");

		RegistryHelper.register(new Object[] { rocks, limestone, water, metals, sands, woods, glass, transparent, 
				pearlBlock, pearlBrick, machines, multiMachines, renderedMultiMachines, renderedMachines, tanks, air });
	}
	
	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", EntityIds.FAKE_ITEM, Mariculture.instance, 80, 3, false);
	}
	
	ToolMaterial brick = EnumHelper.addToolMaterial("BRICK", 1, 1000, 3.0F, 1.2F, 12);

	@Override
	public void registerItems() {
		materials = new ItemMaterial().setUnlocalizedName("materials");
		craftingItem = new ItemCrafting().setUnlocalizedName("craftingItems");
		batteryCopper = new ItemBattery(10000, 100, 250).setUnlocalizedName("batteryCopper");
		batteryTitanium = new ItemBattery(100000, 1000, 2500).setUnlocalizedName("batteryTitanium");
		food = new ItemFood().setUnlocalizedName("food");
		upgrade = new ItemUpgrade().setUnlocalizedName("upgrade");
		pearls = new ItemPearl().setUnlocalizedName("pearls");
		liquidContainers = new ItemFluidContainer().setUnlocalizedName("liquidContainers");
		hammer = new ItemHammer(brick).setUnlocalizedName("hammer");
		worked = new ItemWorked().setUnlocalizedName("worked");
		guides = new ItemGuide().setUnlocalizedName("guide");
		ladle = new ItemFluidStorage(MetalRates.INGOT).setUnlocalizedName("ladle");
		bucket = new ItemFluidStorage(8000).setUnlocalizedName("titaniumBucket");
		
		RegistryHelper.register(new Object[] { materials, craftingItem, batteryTitanium, food, upgrade, pearls, 
				liquidContainers, hammer, worked, batteryCopper, guides, ladle, bucket });
	}
	
	@Override
	public void registerOther() {
		registerBiomes();
		registerLiquids();
		addToOreDictionary();
		MaricultureTab.tabMariculture.icon = new ItemStack(pearls, 1, PearlColor.WHITE);
	}

	private void registerLiquids() {
		highPressureWater = new FluidMari(FluidDictionary.hp_water, -1);
        if (!FluidRegistry.registerFluid(highPressureWater))
        	highPressureWater = FluidRegistry.getFluid(FluidDictionary.hp_water);
        highPressureWaterBlock = new BlockFluidMari(highPressureWater, Material.water).setBlockName("highPressureWater");
        GameRegistry.registerBlock(highPressureWaterBlock, "Mariculture_highPressureWaterBlock");
        highPressureWater.setBlock(highPressureWaterBlock);
        registerHeatBottle(FluidDictionary.hp_water, 1000, FluidContainerMeta.BOTTLE_HP_WATER);
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
				new ItemStack(Core.liquidContainers, 1, meta), new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_EMPTY));
	}
	 
	public static void registerVanillaBottle(String fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(fluid, vol), 
				new ItemStack(Core.liquidContainers, 1, meta), new ItemStack(Items.glass_bottle));
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
		//Frozen Biomes
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.iceMountains, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.icePlains, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.frozenRiver, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.coldTaiga, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.coldTaigaHills, EnumBiomeType.FROZEN);
		
		//Frozen Ocean
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.coldBeach, EnumBiomeType.FROZEN_OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.frozenOcean, EnumBiomeType.FROZEN_OCEAN);
		
		//Ocean
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.beach, EnumBiomeType.OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.ocean, EnumBiomeType.OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.deepOcean, EnumBiomeType.OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.stoneBeach, EnumBiomeType.OCEAN);
		
		//Cold
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.extremeHills, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.extremeHillsEdge, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.extremeHillsPlus, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.taiga, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.taigaHills, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.megaTaiga, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.megaTaigaHills, EnumBiomeType.COLD);
		
		//Normal
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.forest, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.forestHills, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.plains, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.river, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.swampland, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.birchForest, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.birchForestHills, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.roofedForest, EnumBiomeType.NORMAL);
		
		//Hot
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.jungle, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.jungleHills, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.savanna, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.savannaPlateau, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mesa, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mesaPlateau_F, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mesaPlateau, EnumBiomeType.HOT);
		
		//Arid
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.desert, EnumBiomeType.ARID);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.desertHills, EnumBiomeType.ARID);

		//Nether
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.hell, EnumBiomeType.HELL);
		
		//End
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.sky, EnumBiomeType.ENDER);
		
		//Mushroom
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mushroomIsland, EnumBiomeType.MUSHROOM);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mushroomIslandShore, EnumBiomeType.MUSHROOM);	
	}
	
	@Override
	public void addRecipes() {
		addFluids();
		Recipes.add();
	}
}
