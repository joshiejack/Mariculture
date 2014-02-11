package mariculture.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mariculture.Mariculture;
import mariculture.api.core.EnumBiomeType;
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
import mariculture.core.blocks.TileBookshelf;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileTankBlock;
import mariculture.core.blocks.TileVat;
import mariculture.core.blocks.TileVoidBottle;
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.guide.GuideHandler;
import mariculture.core.guide.Guides;
import mariculture.core.handlers.BiomeTypeHandler;
import mariculture.core.handlers.CraftingHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.ModulesHandler;
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
import mariculture.core.items.ItemGuide;
import mariculture.core.items.ItemHammer;
import mariculture.core.items.ItemMaterial;
import mariculture.core.items.ItemPearl;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMari;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Core extends Module {
	public static Block oreBlocks;
	public static Block utilBlocks;
	public static Block singleBlocks;
	public static Block oysterBlock;
	public static Block pearlBrick;
	public static Block doubleBlock;
	public static Block glassBlocks;
	public static Block airBlocks;
	public static Block woodBlocks;
	public static Block tankBlocks;
	public static Block groundBlocks;
	public static Block transparentBlocks;

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
		MaricultureHandlers.biomeType = new BiomeTypeHandler();
		MaricultureHandlers.smelter = new LiquifierHandler();
		MaricultureHandlers.casting = new IngotCastingHandler();
		MaricultureHandlers.vat = new VatHandler();
		MaricultureHandlers.anvil = new TileAnvil();
		MaricultureHandlers.upgrades = new UpgradeHandler();
		MaricultureHandlers.modules = new ModulesHandler();
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new WorldGenHandler());
		MinecraftForge.EVENT_BUS.register(new GuiItemToolTip());
		GameRegistry.registerPlayerTracker(new PlayerTrackerHandler());
		GameRegistry.registerCraftingHandler(new CraftingHandler());
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
		oreBlocks = new BlockOre().setStepSound(Block.soundTypeStone).setResistance(5F).setBlockName("oreBlocks");
		utilBlocks = new BlockUtil().setStepSound(Block.soundTypeMetal).setHardness(2F).setResistance(5F).setBlockName("utilBlocks");
		doubleBlock = new BlockDouble().setStepSound(Block.soundTypeMetal).setResistance(3F).setBlockName("doubleBlocks").setHardness(3F);
		singleBlocks = new BlockSingle().setStepSound(Block.soundTypeMetal).setHardness(1F).setResistance(1F).setBlockName("customBlocks");
		oysterBlock = new BlockOyster().setStepSound(Block.soundTypeSand).setHardness(10F).setResistance(50F).setBlockName("oysterBlock").setLightValue(0.4F);
		pearlBrick = new BlockPearlBrick().setBlockName("pearlBrick");
		glassBlocks = new BlockGlass().setStepSound(Block.soundTypeGlass).setResistance(10F).setBlockName("glassBlocks");
		airBlocks = new BlockAir().setBlockUnbreakable().setBlockName("airBlocks");
		woodBlocks = new BlockWood().setStepSound(Block.soundTypeWood).setBlockName("woodBlocks").setHardness(2.0F);
		tankBlocks = new BlockTank().setStepSound(Block.soundTypeGlass).setBlockName("tankBlocks").setHardness(1F);
		groundBlocks = new BlockGround().setBlockName("groundBlocks").setHardness(1F);
		transparentBlocks = new BlockTransparent().setStepSound(Block.soundTypePiston).setBlockName("transparentBlocks").setHardness(1F);
		
		Items.itemsList[ores] = new BlockOreItem(ores - 256, oreBlocks).setUnlocalizedName("oreBlocks");
		Items.itemsList[util] = new BlockUtilItem(util - 256, utilBlocks).setUnlocalizedName("utilBlocks");
		Items.itemsList[singleBlocks] = new BlockSingleItem(singleBlocks - 256, singleBlocks).setUnlocalizedName("customBlocks");
		Items.itemsList[doubleBlocks] = new BlockDoubleItem(doubleBlocks - 256, doubleBlock).setUnlocalizedName("doubleBlocks");
		Items.itemsList[pearlBrick] = new BlockPearlBrickItem(pearlBrick - 256, Core.pearlBrick).setUnlocalizedName("pearlBrick");
		Items.itemsList[glassBlocks] = new BlockGlassItem(glassBlocks - 256, glassBlocks).setUnlocalizedName("glassBlocks");
		Items.itemsList[airBlocks] = new BlockAirItem(airBlocks - 256, airBlocks).setUnlocalizedName("airBlocks");
		Items.itemsList[woodBlocks] = new BlockWoodItem(woodBlocks - 256, woodBlocks).setUnlocalizedName("woodBlocks");
		Items.itemsList[tankBlocks] = new BlockTankItem(tankBlocks - 256, tankBlocks).setUnlocalizedName("tankBlocks");
		Items.itemsList[groundBlocks] = new BlockGroundItem(groundBlocks - 256, groundBlocks).setUnlocalizedName("groundBlocks");
		Items.itemsList[transparentBlocks] = new BlockTransparentItem(transparentBlocks - 256, transparentBlocks).setUnlocalizedName("transparentBlocks");

		GameRegistry.registerBlock(Core.oysterBlock, "Mariculture_oysterBlock");
		
		GameRegistry.registerTileEntity(TileAirPump.class, "tileEntityAirPump");
		GameRegistry.registerTileEntity(TileOyster.class, "tileEntityOyster");
		GameRegistry.registerTileEntity(TileLiquifier.class, "tileEntityLiquifier");
		GameRegistry.registerTileEntity(TileBookshelf.class, "tileBookshelf");
		GameRegistry.registerTileEntity(TileTankBlock.class, "tileTankBlock");
		GameRegistry.registerTileEntity(TileVat.class, "tileVatBlock");
		GameRegistry.registerTileEntity(TileAnvil.class, "tileBlacksmithAnvil");
		GameRegistry.registerTileEntity(TileIngotCaster.class, "tileIngotCaster");
		GameRegistry.registerTileEntity(TileVoidBottle.class, "tileVoidBottle");

		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.ALUMINUM_BLOCK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BAUXITE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.COPPER, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.COPPER_BLOCK, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.LIMESTONE, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.LIMESTONE_BRICK, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.LIMESTONE_CHISELED, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.LIMESTONE_SMOOTH, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.MAGNESIUM_BLOCK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.RUTILE, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.TITANIUM_BLOCK, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BASE_BRICK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BASE_IRON, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.LIQUIFIER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.ANVIL_1, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.ANVIL_2, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.ANVIL_3, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.ANVIL_4, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.AIR_PUMP, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(singleBlocks, SingleMeta.INGOT_CASTER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(doubleBlock, DoubleMeta.VAT, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.BOOKSHELF, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(groundBlocks, GroundMeta.BUBBLES, "shovel", 0);
		MinecraftForge.setBlockHarvestLevel(woodBlocks, WoodMeta.BASE_WOOD, "axe", 1);

		RegistryHelper.register(new Object[] { oreBlocks, pearlBrick, oysterBlock, utilBlocks, doubleBlock,
				singleBlocks, glassBlocks, airBlocks, woodBlocks, tankBlocks, groundBlocks, transparentBlocks });
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
		
		MinecraftForge.setToolClass(hammer, "pickaxe", 0);
		
		RegistryHelper.register(new Object[] { materials, craftingItem, batteryTitanium, food, upgrade, pearls, 
				liquidContainers, hammer, worked, batteryCopper, guides, ladle, bucket });
	}
	
	@Override
	public void registerOther() {
		registerBiomes();
		registerLiquids();
		addToOreDictionary();
		if(!WorldPlus.isActive && WorldGeneration.DEEP_OCEAN) {
			addDeepOcean();
		}

		MaricultureTab.tabMariculture.icon = new ItemStack(pearls, 1, PearlColor.WHITE);
	}
	
	private void addDeepOcean() {
		try {
			Field field = BiomeGenBase.class.getField("ocean");
			if(field == null)
				field = BiomeGenBase.class.getField("field_76771_b");
			Object newValue = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(new Height(-1.8F, 0.4F));
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
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(oreBlocks, 1, OresMeta.LIMESTONE));
		OreDictionary.registerOre("oreCopper", new ItemStack(oreBlocks, 1, OresMeta.COPPER));
		OreDictionary.registerOre("oreAluminum", new ItemStack(oreBlocks, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreRutile", new ItemStack(oreBlocks, 1, OresMeta.RUTILE));
		
		OreDictionary.registerOre("blockAluminum", new ItemStack(oreBlocks, 1, OresMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockCopper", new ItemStack(oreBlocks, 1, OresMeta.COPPER_BLOCK));
		OreDictionary.registerOre("blockMagnesium", new ItemStack(oreBlocks, 1, OresMeta.MAGNESIUM_BLOCK));
		OreDictionary.registerOre("blockTitanium", new ItemStack(oreBlocks, 1, OresMeta.TITANIUM_BLOCK));
		
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
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.beach, EnumBiomeType.OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.desert, EnumBiomeType.ARID);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.desertHills, EnumBiomeType.ARID);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.extremeHills, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.extremeHillsEdge, EnumBiomeType.COLD);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.forest, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.forestHills, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.frozenOcean, EnumBiomeType.FROZEN_OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.frozenRiver, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.hell, EnumBiomeType.HELL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.iceMountains, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.icePlains, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.jungle, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.jungleHills, EnumBiomeType.HOT);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mushroomIsland, EnumBiomeType.MUSHROOM);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.mushroomIslandShore, EnumBiomeType.MUSHROOM);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.ocean, EnumBiomeType.OCEAN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.plains, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.river, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.sky, EnumBiomeType.ENDER);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.swampland, EnumBiomeType.NORMAL);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.taiga, EnumBiomeType.FROZEN);
		MaricultureHandlers.biomeType.addBiome(BiomeGenBase.taigaHills, EnumBiomeType.FROZEN);
	}
	
	@Override
	public void addRecipes() {
		addFluids();
		Recipes.add();
	}
}
