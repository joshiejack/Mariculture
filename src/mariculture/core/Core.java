package mariculture.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.IAnvilHandler;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockAirItem;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.BlockDoubleItem;
import mariculture.core.blocks.BlockGround;
import mariculture.core.blocks.BlockGroundItem;
import mariculture.core.blocks.BlockOre;
import mariculture.core.blocks.BlockOreItem;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.BlockPearlBrick;
import mariculture.core.blocks.BlockPearlBrickItem;
import mariculture.core.blocks.BlockFluidMari;
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
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.handlers.BiomeTypeHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.GuideHandler;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.ModulesHandler;
import mariculture.core.handlers.UpgradeHandler;
import mariculture.core.handlers.VatHandler;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.items.ItemCrafting;
import mariculture.core.items.ItemFluidContainer;
import mariculture.core.items.ItemFood;
import mariculture.core.items.ItemGuide;
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
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMari;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
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
	
	public static Fluid fishFood;
	public static Fluid naturalGas;
	public static Fluid quicklime;
	public static Fluid fishOil;
	
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
	
	@Override
	public void registerHandlers() {
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
		oreBlocks = new BlockOre(BlockIds.ores).setStepSound(Block.soundStoneFootstep).setResistance(5F).setUnlocalizedName("oreBlocks");
		utilBlocks = new BlockUtil(BlockIds.util).setStepSound(Block.soundStoneFootstep).setHardness(2F).setResistance(5F).setUnlocalizedName("utilBlocks");
		doubleBlock = new BlockDouble(BlockIds.doubleBlocks).setStepSound(Block.soundMetalFootstep).setResistance(3F).setUnlocalizedName("doubleBlocks").setHardness(3F);
		singleBlocks = new BlockSingle(BlockIds.singleBlocks).setStepSound(Block.soundStoneFootstep).setHardness(1F).setResistance(1F).setUnlocalizedName("customBlocks");
		oysterBlock = new BlockOyster(BlockIds.oyster).setStepSound(Block.soundStoneFootstep).setHardness(10F).setResistance(50F).setUnlocalizedName("oysterBlock").setLightValue(0.4F);
		pearlBrick = new BlockPearlBrick(BlockIds.pearlBrick).setUnlocalizedName("pearlBrick");
		glassBlocks = new BlockTransparent(BlockIds.glassBlocks).setStepSound(Block.soundPowderFootstep).setResistance(1F).setUnlocalizedName("glassBlocks");
		airBlocks = new BlockAir(BlockIds.airBlocks).setBlockUnbreakable().setUnlocalizedName("airBlocks");
		woodBlocks = new BlockWood(BlockIds.woodBlocks).setUnlocalizedName("woodBlocks");
		tankBlocks = new BlockTank(BlockIds.tankBlocks).setUnlocalizedName("tankBlocks").setHardness(1F);
		groundBlocks = new BlockGround(BlockIds.groundBlocks).setUnlocalizedName("groundBlocks");

		Item.itemsList[BlockIds.ores] = new BlockOreItem(BlockIds.ores - 256, oreBlocks).setUnlocalizedName("oreBlocks");
		Item.itemsList[BlockIds.util] = new BlockUtilItem(BlockIds.util - 256, utilBlocks).setUnlocalizedName("utilBlocks");
		Item.itemsList[BlockIds.singleBlocks] = new BlockSingleItem(BlockIds.singleBlocks - 256, singleBlocks).setUnlocalizedName("customBlocks");
		Item.itemsList[BlockIds.doubleBlocks] = new BlockDoubleItem(BlockIds.doubleBlocks - 256, doubleBlock).setUnlocalizedName("doubleBlocks");
		Item.itemsList[BlockIds.pearlBrick] = new BlockPearlBrickItem(BlockIds.pearlBrick - 256, Core.pearlBrick).setUnlocalizedName("pearlBrick");
		Item.itemsList[BlockIds.glassBlocks] = new BlockTransparentItem(BlockIds.glassBlocks - 256, glassBlocks).setUnlocalizedName("glassBlocks");
		Item.itemsList[BlockIds.airBlocks] = new BlockAirItem(BlockIds.airBlocks - 256, airBlocks).setUnlocalizedName("airBlocks");
		Item.itemsList[BlockIds.woodBlocks] = new BlockWoodItem(BlockIds.woodBlocks - 256, woodBlocks).setUnlocalizedName("woodBlocks");
		Item.itemsList[BlockIds.tankBlocks] = new BlockTankItem(BlockIds.tankBlocks - 256, tankBlocks).setUnlocalizedName("tankBlocks");
		Item.itemsList[BlockIds.groundBlocks] = new BlockGroundItem(BlockIds.groundBlocks - 256, groundBlocks).setUnlocalizedName("groundBlocks");

		GameRegistry.registerBlock(Core.oysterBlock, "Mariculture_oysterBlock");
		
		GameRegistry.registerTileEntity(TileAirPump.class, "tileEntityAirPump");
		GameRegistry.registerTileEntity(TileOyster.class, "tileEntityOyster");
		GameRegistry.registerTileEntity(TileLiquifier.class, "tileEntityLiquifier");
		GameRegistry.registerTileEntity(TileBookshelf.class, "tileBookshelf");
		GameRegistry.registerTileEntity(TileTankBlock.class, "tileTankBlock");
		GameRegistry.registerTileEntity(TileVat.class, "tileVatBlock");
		GameRegistry.registerTileEntity(TileAnvil.class, "tileBlacksmithAnvil");
		GameRegistry.registerTileEntity(TileIngotCaster.class, "tileIngotCaster");

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
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.TITANIUM_BLOCK, "pickaxe", 20);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BASE_BRICK, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BASE_IRON, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.LIQUIFIER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(groundBlocks, GroundMeta.BUBBLES, "shovel", 0);

		RegistryHelper.register(new Object[] { oreBlocks, pearlBrick, oysterBlock, utilBlocks, doubleBlock,
				singleBlocks, glassBlocks, airBlocks, woodBlocks, tankBlocks, groundBlocks });
	}
	
	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", EntityIds.FAKE_ITEM, Mariculture.instance, 80, 3, false);
	}

	@Override
	public void registerItems() {
		materials = new ItemMaterial(ItemIds.metals).setUnlocalizedName("materials");
		craftingItem = new ItemCrafting(ItemIds.items).setUnlocalizedName("craftingItems");
		batteryCopper = new ItemBattery(ItemIds.batteryCopper, 10000, 100, 250).setUnlocalizedName("batteryCopper");
		batteryTitanium = new ItemBattery(ItemIds.batteryTitanium, 50000, 1000, 2500).setUnlocalizedName("batteryTitanium");
		food = new ItemFood(ItemIds.food).setUnlocalizedName("food");
		upgrade = new ItemUpgrade(ItemIds.upgrade).setUnlocalizedName("upgrade");
		pearls = new ItemPearl(ItemIds.pearl).setUnlocalizedName("pearls");
		liquidContainers = new ItemFluidContainer(ItemIds.liquidContainers).setUnlocalizedName("liquidContainers");
		hammer = new ItemHammer(ItemIds.hammer, 1000).setUnlocalizedName("hammer");
		worked = new ItemWorked(ItemIds.worked).setUnlocalizedName("worked");
		guides = new ItemGuide(ItemIds.guides).setUnlocalizedName("guide");

		RegistryHelper.register(new Object[] { materials, craftingItem, batteryTitanium, food, upgrade, pearls, 
				liquidContainers, hammer, worked, batteryCopper, guides });
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
        highPressureWater.setBlockID(highPressureWaterBlock);
	}

	private void addToOreDictionary() {	
		DictionaryHelper.add("glass", new ItemStack(Block.glass));
		DictionaryHelper.add("ingotIron", new ItemStack(Item.ingotIron));
		DictionaryHelper.add("ingotGold", new ItemStack(Item.ingotGold));
		DictionaryHelper.add("blockIron", new ItemStack(Block.blockIron));
		DictionaryHelper.add("blockGold", new ItemStack(Block.blockGold));
		DictionaryHelper.add("blockLapis", new ItemStack(Block.blockLapis));
		DictionaryHelper.add("blockCoal", new ItemStack(Block.coalBlock));
		DictionaryHelper.add("dustRedstone", new ItemStack(Item.redstone));
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(oreBlocks, 1, OresMeta.LIMESTONE));
		OreDictionary.registerOre("oreCopper", new ItemStack(oreBlocks, 1, OresMeta.COPPER));
		OreDictionary.registerOre("oreAluminum", new ItemStack(oreBlocks, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreRutile", new ItemStack(oreBlocks, 1, OresMeta.RUTILE));
		
		OreDictionary.registerOre("blockAluminum", new ItemStack(oreBlocks, 1, OresMeta.ALUMINUM_BLOCK));
		OreDictionary.registerOre("blockCopper", new ItemStack(oreBlocks, 1, OresMeta.COPPER_BLOCK));
		OreDictionary.registerOre("blockMagnesium", new ItemStack(oreBlocks, 1, OresMeta.MAGNESIUM_BLOCK));
		OreDictionary.registerOre("blockTitanium", new ItemStack(oreBlocks, 1, OresMeta.TITANIUM_BLOCK));
		
		OreDictionary.registerOre("ingotAluminum", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		OreDictionary.registerOre("ingotCopper", new ItemStack(materials, 1, MaterialsMeta.INGOT_COPPER));
		OreDictionary.registerOre("ingotMagnesium", new ItemStack(materials, 1, MaterialsMeta.INGOT_MAGNESIUM));
		OreDictionary.registerOre("ingotTitanium", new ItemStack(materials, 1, MaterialsMeta.INGOT_TITANIUM));
	}

	private void addFluids() {	
		//Mariculture Fluids
		FluidDictionary.quicklime = addFluid(("quicklime"), quicklime, 1000, FluidContainerMeta.BOTTLE_QUICKLIME);
		FluidDictionary.fish_food = addFluid("food.fish", fishFood, 256, FluidContainerMeta.BOTTLE_FISH_FOOD);
		FluidDictionary.fish_oil = addFluid("oil.fish", fishOil, 1000, FluidContainerMeta.BOTTLE_FISH_OIL);
		FluidDictionary.natural_gas = addFluid("gas.natural", naturalGas, 1000, FluidContainerMeta.BOTTLE_GAS);
		FluidDictionary.salt = addFluid("salt.molten", moltenSalt, 1000, FluidContainerMeta.BOTTLE_SALT);
		FluidDictionary.glass = addFluid("glass.molten", moltenGlass, 1000, FluidContainerMeta.BOTTLE_GLASS);
		FluidDictionary.aluminum = addFluid("aluminum.molten", moltenAluminum, MetalRates.INGOT, FluidContainerMeta.BOTTLE_ALUMINUM);
		FluidDictionary.magnesium = addFluid("magnesium.molten", moltenMagnesium, MetalRates.INGOT, FluidContainerMeta.BOTTLE_MAGNESIUM);
		FluidDictionary.titanium = addFluid("titanium.molten", moltenTitanium, MetalRates.INGOT, FluidContainerMeta.BOTTLE_TITANIUM);
		FluidDictionary.copper = addFluid("copper.molten", moltenCopper, MetalRates.INGOT, FluidContainerMeta.BOTTLE_COPPER);
		FluidDictionary.rutile = addFluid("rutile.molten", moltenRutile, MetalRates.INGOT, FluidContainerMeta.BOTTLE_RUTILE);
		
		//Vanilla Fluids
		FluidDictionary.iron = addFluid("iron.molten", moltenIron, MetalRates.INGOT, FluidContainerMeta.BOTTLE_IRON);
		FluidDictionary.gold = addFluid("gold.molten", moltenGold, MetalRates.INGOT, FluidContainerMeta.BOTTLE_GOLD);

		//Modded Fluids
		FluidDictionary.tin = addFluid("tin.molten", moltenTin, MetalRates.INGOT, FluidContainerMeta.BOTTLE_TIN);
		FluidDictionary.lead = addFluid("lead.molten", moltenLead, MetalRates.INGOT, FluidContainerMeta.BOTTLE_LEAD);
		FluidDictionary.silver = addFluid("silver.molten", moltenSilver, MetalRates.INGOT, FluidContainerMeta.BOTTLE_SILVER);
		FluidDictionary.nickel = addFluid("nickel.molten", moltenNickel, MetalRates.INGOT, FluidContainerMeta.BOTTLE_NICKEL);
		FluidDictionary.bronze = addFluid("bronze.molten", moltenBronze, MetalRates.INGOT, FluidContainerMeta.BOTTLE_BRONZE);
		FluidDictionary.steel =	addFluid("steel.molten", moltenSteel, MetalRates.INGOT, FluidContainerMeta.BOTTLE_STEEL);
	}
	
	private String addFluid(String name, Fluid globalFluid, int volume, int bottleMeta) {
		if (!FluidDictionary.instance.metalExists(name)) {
			globalFluid = new FluidMari(name, bottleMeta).setUnlocalizedName(name);
			FluidRegistry.registerFluid(globalFluid);
			FluidDictionary.instance.addFluid(name, globalFluid);
		}
		
		Fluid fluid = FluidDictionary.getFluid(name);
		
		if(volume != -1) {
			FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid.getID(), volume), 
					new ItemStack(liquidContainers, 1, bottleMeta), new ItemStack(Item.glassBottle));
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
