package mariculture.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockAirItem;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.BlockDoubleItem;
import mariculture.core.blocks.BlockFluids;
import mariculture.core.blocks.BlockLimestoneSlab;
import mariculture.core.blocks.BlockLimestoneSlabItem;
import mariculture.core.blocks.BlockMaricultureStairs;
import mariculture.core.blocks.BlockOre;
import mariculture.core.blocks.BlockOreItem;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.BlockPearlBrick;
import mariculture.core.blocks.BlockPearlBrickItem;
import mariculture.core.blocks.BlockPressuredWater;
import mariculture.core.blocks.BlockSingle;
import mariculture.core.blocks.BlockSingleItem;
import mariculture.core.blocks.BlockTransparent;
import mariculture.core.blocks.BlockTransparentItem;
import mariculture.core.blocks.BlockUtil;
import mariculture.core.blocks.BlockUtilItem;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileBookshelf;
import mariculture.core.blocks.TileConverter;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.blocks.TileOyster;
import mariculture.core.blocks.TileSettler;
import mariculture.core.handlers.BiomeTypeHandler;
import mariculture.core.handlers.ClientUpdateHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.ModulesHandler;
import mariculture.core.handlers.PlayerUpdatesHandler;
import mariculture.core.handlers.ServerUpdateHandler;
import mariculture.core.handlers.SettlerRecipeHandler;
import mariculture.core.handlers.UpgradeHandler;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.items.ItemCrafting;
import mariculture.core.items.ItemFluidContainer;
import mariculture.core.items.ItemFood;
import mariculture.core.items.ItemMaterial;
import mariculture.core.items.ItemPearl;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMariculture;
import mariculture.plugins.PluginTinkersConstruct;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class Core extends Module {
	public static Block oreBlocks;
	public static Block utilBlocks;
	public static Block singleBlocks;
	public static Block limestoneSlabs;
	public static Block limestoneSlabsDouble;
	public static Block limestoneStairs;
	public static Block limestoneBrickStairs;
	public static Block limestoneSmoothStairs;
	public static Block limestoneChiseledStairs;
	public static Block oysterBlock;
	public static Block pearlBrick;
	public static Block doubleBlock;
	public static Block glassBlocks;
	public static Block airBlocks;

	public static Fluid moltenAluminum;
	public static Block moltenAluminumBlock;
	public static Fluid moltenTitanium;
	public static Block moltenTitaniumBlock;
	public static Fluid moltenIron;
	public static Block moltenIronBlock;
	public static Fluid moltenGold;
	public static Block moltenGoldBlock;
	public static Fluid moltenCopper;
	public static Block moltenCopperBlock;
	public static Fluid moltenTin;
	public static Block moltenTinBlock;
	public static Fluid moltenMagnesium;
	public static Block moltenMagnesiumBlock;
	public static Fluid moltenBronze;
	public static Block moltenBronzeBlock;
	public static Fluid moltenLead;
	public static Block moltenLeadBlock;
	public static Fluid moltenSilver;
	public static Block moltenSilverBlock;
	public static Fluid moltenSteel;
	public static Block moltenSteelBlock;
	public static Fluid moltenNickel;
	public static Block moltenNickelBlock;
	public static Fluid moltenRutile;
	public static Block moltenRutileBlock;
	public static Fluid fishOil;
	public static Block fishOilBlock;
	public static Fluid fishFood;
	public static Block fishFoodBlock;
	public static Fluid highPressureWater;
	public static Block highPressureWaterBlock;
	public static Fluid moltenGlass;
	public static Block moltenGlassBlock;
	public static Fluid naturalGas;

	public static Item liquidContainers;
	public static Item materials;
	public static Item craftingItem;
	public static Item battery;
	public static Item food;
	public static Item upgrade;
	public static Item pearls;
	@Override
	public void registerHandlers() {
		TickRegistry.registerScheduledTickHandler(new ServerUpdateHandler(), Side.SERVER);
		TickRegistry.registerScheduledTickHandler(new ClientUpdateHandler(), Side.SERVER);
		TickRegistry.registerScheduledTickHandler(new PlayerUpdatesHandler(), Side.CLIENT);
		MaricultureHandlers.biomeType = new BiomeTypeHandler();
		MaricultureHandlers.smelter = new LiquifierHandler();
		MaricultureHandlers.freezer = new SettlerRecipeHandler();
		MaricultureHandlers.upgrades = new UpgradeHandler();
		MaricultureHandlers.modules = new ModulesHandler();
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new WorldGenHandler());
	}

	@Override
	public void registerBlocks() {
		oreBlocks = new BlockOre(BlockIds.ores).setStepSound(Block.soundStoneFootstep).setResistance(5F).setUnlocalizedName("oreBlocks");
		utilBlocks = new BlockUtil(BlockIds.util).setStepSound(Block.soundStoneFootstep).setHardness(2F).setResistance(5F).setUnlocalizedName("utilBlocks");
		doubleBlock = new BlockDouble(BlockIds.doubleBlocks).setStepSound(Block.soundMetalFootstep)
				.setResistance(3F).setUnlocalizedName("doubleBlocks").setHardness(3F);
		singleBlocks = new BlockSingle(BlockIds.singleBlocks).setStepSound(Block.soundStoneFootstep).setHardness(1F)
				.setResistance(1F).setUnlocalizedName("customBlocks");
		oysterBlock = new BlockOyster(BlockIds.oyster).setStepSound(Block.soundStoneFootstep).setHardness(10F)
				.setResistance(50F).setUnlocalizedName("oysterBlock").setLightValue(0.4F);
		pearlBrick = new BlockPearlBrick(BlockIds.pearlBrick).setUnlocalizedName("pearlBrick");
		glassBlocks = new BlockTransparent(BlockIds.glassBlocks).setStepSound(Block.soundPowderFootstep).setResistance(1F).setUnlocalizedName("glassBlocks");
		airBlocks = new BlockAir(BlockIds.airBlocks).setBlockUnbreakable().setUnlocalizedName("airBlocks");

		Item.itemsList[BlockIds.ores] = new BlockOreItem(BlockIds.ores - 256, oreBlocks).setUnlocalizedName("oreBlocks");
		Item.itemsList[BlockIds.util] = new BlockUtilItem(BlockIds.util - 256, utilBlocks).setUnlocalizedName("utilBlocks");
		Item.itemsList[BlockIds.singleBlocks] = new BlockSingleItem(BlockIds.singleBlocks - 256, singleBlocks).setUnlocalizedName("customBlocks");
		Item.itemsList[BlockIds.doubleBlocks] = new BlockDoubleItem(BlockIds.doubleBlocks - 256, doubleBlock).setUnlocalizedName("doubleBlocks");
		Item.itemsList[BlockIds.pearlBrick] = new BlockPearlBrickItem(BlockIds.pearlBrick - 256, Core.pearlBrick).setUnlocalizedName("pearlBrick");
		Item.itemsList[BlockIds.glassBlocks] = new BlockTransparentItem(BlockIds.glassBlocks - 256, glassBlocks).setUnlocalizedName("glassBlocks");
		Item.itemsList[BlockIds.airBlocks] = new BlockAirItem(BlockIds.airBlocks - 256, airBlocks).setUnlocalizedName("airBlocks");

		GameRegistry.registerBlock(Core.oysterBlock, "Mariculture_oysterBlock");
		
		GameRegistry.registerTileEntity(TileAirPump.class, "tileEntityAirPump");
		GameRegistry.registerTileEntity(TileOyster.class, "tileEntityOyster");
		GameRegistry.registerTileEntity(TileLiquifier.class, "tileEntityLiquifier");
		GameRegistry.registerTileEntity(TileSettler.class, "tileEntitySettler");
		GameRegistry.registerTileEntity(TileBookshelf.class, "tileBookshelf");
		GameRegistry.registerTileEntity(TileConverter.class, "tileConverter");

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
		MinecraftForge.setBlockHarvestLevel(oreBlocks, OresMeta.BASE_WOOD, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.LIQUIFIER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.SETTLER, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(utilBlocks, UtilMeta.PURIFIER, "pickaxe", 1);

        //TODO REMOVE: Legacy Stairs!
        if(BlockIds.limestoneStairs != 0) {
            limestoneStairs = new BlockMaricultureStairs(BlockIds.limestoneStairs, Block.blocksList[oreBlocks.blockID],
                    OresMeta.LIMESTONE).setUnlocalizedName("limestoneStairs").setHardness(1.5F);
            limestoneBrickStairs = new BlockMaricultureStairs(BlockIds.limestoneBrickStairs, Block.blocksList[oreBlocks.blockID],
                    OresMeta.LIMESTONE_BRICK).setUnlocalizedName("limestoneBrickStairs").setHardness(1.5F);
            limestoneSmoothStairs = new BlockMaricultureStairs(BlockIds.limestoneSmoothStairs, Block.blocksList[oreBlocks.blockID],
                    OresMeta.LIMESTONE_SMOOTH).setUnlocalizedName("limestoneSmoothStairs").setHardness(1.5F);
            limestoneChiseledStairs = new BlockMaricultureStairs(BlockIds.limestoneChiseledStairs, Block.blocksList[oreBlocks.blockID],
                    OresMeta.LIMESTONE_CHISELED).setUnlocalizedName("limestoneBorderedStairs").setHardness(1.5F);
            limestoneSlabs = new BlockLimestoneSlab(BlockIds.limestoneSlabs, false).setUnlocalizedName("limestoneSlabs");
            limestoneSlabsDouble = new BlockLimestoneSlab(BlockIds.limestoneDoubleSlabs, true).setUnlocalizedName("limestoneSlabs");

            BlockLimestoneSlabItem.setSlabs((BlockHalfSlab) limestoneSlabs, (BlockHalfSlab) limestoneSlabsDouble);
            GameRegistry.registerBlock(limestoneSlabs, mariculture.core.blocks.BlockLimestoneSlabItem.class, "Mariculture_limestoneSlab");
            GameRegistry.registerBlock(limestoneSlabsDouble, mariculture.core.blocks.BlockLimestoneSlabItem.class, "Mariculture_limestoneSlabDouble");
            GameRegistry.registerBlock(limestoneStairs, "Mariculture_limestoneStairs");
            GameRegistry.registerBlock(limestoneBrickStairs, "Mariculture_limestoneBrickStairs");
            GameRegistry.registerBlock(limestoneSmoothStairs, "Mariculture_limestoneSmoothStairs");
            GameRegistry.registerBlock(limestoneChiseledStairs, "Mariculture_limestoneChiseledStairs");

            MinecraftForge.setBlockHarvestLevel(limestoneStairs, "pickaxe", 0);
            MinecraftForge.setBlockHarvestLevel(limestoneBrickStairs, "pickaxe", 0);
            MinecraftForge.setBlockHarvestLevel(limestoneSmoothStairs, "pickaxe", 0);
            MinecraftForge.setBlockHarvestLevel(limestoneChiseledStairs, "pickaxe", 0);
            MinecraftForge.setBlockHarvestLevel(limestoneSlabs, "pickaxe", 0);
            MinecraftForge.setBlockHarvestLevel(limestoneSlabsDouble, "pickaxe", 0);

            RegistryHelper.register(new Object[] { limestoneStairs, limestoneBrickStairs,
                    limestoneSmoothStairs, limestoneChiseledStairs, limestoneSlabs });
        }

		RegistryHelper.register(new Object[] { oreBlocks, pearlBrick, oysterBlock, utilBlocks, doubleBlock,
				singleBlocks, glassBlocks, airBlocks });
	}

	@Override
	public void registerItems() {
		materials = new ItemMaterial(ItemIds.metals).setUnlocalizedName("materials");
		craftingItem = new ItemCrafting(ItemIds.items).setUnlocalizedName("craftingItems");
		battery = new ItemBattery(ItemIds.batteryFull, 50000, 1000, 2500).setUnlocalizedName("battery");
		food = new ItemFood(ItemIds.food).setUnlocalizedName("food");
		upgrade = new ItemUpgrade(ItemIds.upgrade).setUnlocalizedName("upgrade");
		pearls = new ItemPearl(ItemIds.pearl).setUnlocalizedName("pearls");
		liquidContainers = new ItemFluidContainer(ItemIds.liquidContainers).setUnlocalizedName("liquidContainers");

		RegistryHelper.register(new Object[] { materials, craftingItem, battery, food, upgrade, pearls, liquidContainers});
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

	private Fluid getFluid(String mine, String tc) {
		Fluid fluid = null;
		fluid = FluidRegistry.getFluid(tc);
		if (fluid == null) {
			fluid = FluidRegistry.getFluid(mine);
		}

		return fluid;
	}

	private void registerLiquids() {
		Fluid fluid;

		// FishOil
		fishOil = new FluidMariculture(FluidDictionary.fish_oil).setUnlocalizedName("fishOil");
		FluidRegistry.registerFluid(fishOil);
		fluid = FluidRegistry.getFluid(FluidDictionary.fish_oil);
		
		if (fluid.getBlockID() == -1) {
			fishOilBlock = new BlockFluids(BlockIds.fishOil, fluid, Material.water, false)
					.setUnlocalizedName("fishOil");
			GameRegistry.registerBlock(fishOilBlock, "Mariculture_fishOilBlock");
			fluid.setBlockID(fishOilBlock);
		}
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid.getID(),
				FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(liquidContainers, 1,
				FluidContainerMeta.BOTTLE_FISH_OIL), new ItemStack(Item.glassBottle));

		// Fish Food
		fishFood = new FluidMariculture(FluidDictionary.fish_food).setUnlocalizedName("fishFood");
		FluidRegistry.registerFluid(fishFood);
		fluid = FluidRegistry.getFluid(FluidDictionary.fish_food);
		
		if (fluid.getBlockID() == -1) {
			fishFoodBlock = new BlockFluids(BlockIds.fishFood, fluid, Material.water, false)
					.setUnlocalizedName("fishFood");
			GameRegistry.registerBlock(fishFoodBlock, "Mariculture_fishFoodBlock");
			fluid.setBlockID(fishFoodBlock);
		}

		highPressureWater = new FluidMariculture(FluidDictionary.hp_water).setDensity(500).setUnlocalizedName(
				"highPressureWater");
		FluidRegistry.registerFluid(highPressureWater);
		fluid = FluidRegistry.getFluid(FluidDictionary.hp_water);
		
		if (fluid.getBlockID() == -1) {
			highPressureWaterBlock = new BlockPressuredWater(BlockIds.highPressureWater, fluid, Material.water)
					.setUnlocalizedName("highPressureWater");
			GameRegistry.registerBlock(highPressureWaterBlock, "Mariculture_highPressureWaterBlock");
			fluid.setBlockID(highPressureWaterBlock);
		}
		
		naturalGas = new FluidMariculture(FluidDictionary.natural_gas).setDensity(-4).setUnlocalizedName("naturalGas").setGaseous(true);
		FluidRegistry.registerFluid(naturalGas);
		fluid = FluidRegistry.getFluid(FluidDictionary.natural_gas);
		if(fluid.getBlockID() == -1) {
			fluid.setBlockID(airBlocks);
		}
		
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid.getID(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(
				liquidContainers, 1, FluidContainerMeta.BOTTLE_GAS), new ItemStack(Item.glassBottle));
		
	}

	private void addToOreDictionary() {	
		DictionaryHelper.add("glass", new ItemStack(Block.glass));
		
		OreDictionary.registerOre("blockLimestone", new ItemStack(oreBlocks, 1, OresMeta.LIMESTONE));
		OreDictionary.registerOre("oreCopper", new ItemStack(oreBlocks, 1, OresMeta.COPPER));
		OreDictionary.registerOre("oreAluminum", new ItemStack(oreBlocks, 1, OresMeta.BAUXITE));
		OreDictionary.registerOre("oreTitanium", new ItemStack(oreBlocks, 1, OresMeta.RUTILE));
		
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
		if(Loader.isModLoaded("TConstruct")) {
			try {
				PluginTinkersConstruct.registerMoltenMetals();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//Mariculture Fluids
		FluidDictionary.glass = addFluid("moltenGlass", moltenGlass, moltenGlassBlock, BlockIds.moltenGlass, Material.lava, FluidContainerRegistry.BUCKET_VOLUME, FluidContainerMeta.BOTTLE_GLASS);
		FluidDictionary.aluminum = addFluid("moltenAluminum", moltenAluminum, moltenAluminumBlock, BlockIds.moltenAluminum, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_ALUMINUM);
		FluidDictionary.magnesium = addFluid("moltenMagnesium", moltenMagnesium, moltenMagnesiumBlock, BlockIds.moltenMagnesium, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_MAGNESIUM);
		FluidDictionary.titanium = addFluid("moltenTitanium", moltenTitanium, moltenTitaniumBlock, BlockIds.moltenTitanium, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_TITANIUM);
		FluidDictionary.copper = addFluid("moltenCopper", moltenCopper, moltenCopperBlock, BlockIds.moltenCopper, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_COPPER);
		FluidDictionary.rutile = addFluid("moltenRutile", moltenRutile, moltenRutileBlock, BlockIds.moltenRutile, Material.lava, 0, 0);
		
		//Vanilla Fluids
		FluidDictionary.iron = addFluid("moltenIron", moltenIron, moltenIronBlock, BlockIds.moltenIron, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_IRON);
		FluidDictionary.gold = addFluid("moltenGold", moltenGold, moltenGoldBlock, BlockIds.moltenGold, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_GOLD);

		//Modded Fluids
		FluidDictionary.tin = addFluid("moltenTin", moltenTin, moltenTinBlock, BlockIds.moltenTin, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_TIN);
		FluidDictionary.lead = addFluid("moltenLead", moltenLead, moltenLeadBlock, BlockIds.moltenLead, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_LEAD);
		FluidDictionary.silver = addFluid("moltenSilver", moltenSilver, moltenSilverBlock, BlockIds.moltenSilver, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_SILVER);
		FluidDictionary.nickel = addFluid("moltenNickel", moltenNickel, moltenNickelBlock, BlockIds.moltenNickel, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_NICKEL);
		FluidDictionary.bronze = addFluid("moltenBronze", moltenBronze, moltenBronzeBlock, BlockIds.moltenBronze, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_BRONZE);
		FluidDictionary.steel =	addFluid("moltenSteel", moltenSteel, moltenSteelBlock, BlockIds.moltenSteel, Material.lava, MetalRates.INGOT, FluidContainerMeta.BOTTLE_STEEL);
	}
	
	private String addFluid(String name, Fluid globalFluid, Block block, int id, Material mat, int volume, int bottleMeta) {
		if (!FluidDictionary.instance.metalExists(name)) {
			if(globalFluid == null) {
				globalFluid = new FluidMariculture(name).setUnlocalizedName(name);
			}
			FluidRegistry.registerFluid(globalFluid);
			FluidDictionary.instance.addFluid(name, globalFluid);
		}
		
		Fluid fluid = FluidDictionary.getFluid(name);
		if (fluid.getBlockID() == -1) {
			if(block == null) {
				block = new BlockFluids(id, fluid, mat, true).setUnlocalizedName(name);
			}
			String blockName = "Mariculture_" + name + "Block";
			GameRegistry.registerBlock(block, blockName);
			fluid.setBlockID(block);
		}
		
		if(volume > 0) {
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
