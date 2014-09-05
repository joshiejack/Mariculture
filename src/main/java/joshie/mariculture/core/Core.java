package joshie.mariculture.core;

import static joshie.mariculture.api.core.MaricultureHandlers.anvil;
import static joshie.mariculture.api.core.MaricultureHandlers.casting;
import static joshie.mariculture.api.core.MaricultureHandlers.crucible;
import static joshie.mariculture.api.core.MaricultureHandlers.environment;
import static joshie.mariculture.api.core.MaricultureHandlers.upgrades;
import static joshie.mariculture.api.core.MaricultureHandlers.vat;
import static joshie.mariculture.core.helpers.FluidHelper.addFluid;
import static joshie.mariculture.core.helpers.FluidHelper.addGas;
import static joshie.mariculture.core.helpers.FluidHelper.registerBucket;
import static joshie.mariculture.core.helpers.FluidHelper.registerHeatBottle;
import static joshie.mariculture.core.helpers.FluidHelper.registerVanillaBottle;
import static joshie.mariculture.core.util.Fluids.getFluid;
import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.blocks.BlockAir;
import joshie.mariculture.core.blocks.BlockGlass;
import joshie.mariculture.core.blocks.BlockGround;
import joshie.mariculture.core.blocks.BlockLimestone;
import joshie.mariculture.core.blocks.BlockMachine;
import joshie.mariculture.core.blocks.BlockMachineMulti;
import joshie.mariculture.core.blocks.BlockMetal;
import joshie.mariculture.core.blocks.BlockPearlBlock;
import joshie.mariculture.core.blocks.BlockPressurisedWater;
import joshie.mariculture.core.blocks.BlockRenderedMachine;
import joshie.mariculture.core.blocks.BlockRenderedMachineMulti;
import joshie.mariculture.core.blocks.BlockRock;
import joshie.mariculture.core.blocks.BlockTank;
import joshie.mariculture.core.blocks.BlockTicking;
import joshie.mariculture.core.blocks.BlockTransparent;
import joshie.mariculture.core.blocks.BlockWater;
import joshie.mariculture.core.blocks.BlockWood;
import joshie.mariculture.core.config.Gardening;
import joshie.mariculture.core.config.WorldGeneration.RetroGen;
import joshie.mariculture.core.config.WorldGeneration.WorldGen;
import joshie.mariculture.core.gui.GuiItemToolTip;
import joshie.mariculture.core.handlers.BucketHandler;
import joshie.mariculture.core.handlers.CastingHandler;
import joshie.mariculture.core.handlers.ClientFMLEvents;
import joshie.mariculture.core.handlers.CrucibleHandler;
import joshie.mariculture.core.handlers.EnvironmentHandler;
import joshie.mariculture.core.handlers.FuelHandler;
import joshie.mariculture.core.handlers.OreDicHandler;
import joshie.mariculture.core.handlers.PearlGenHandler;
import joshie.mariculture.core.handlers.ServerFMLEvents;
import joshie.mariculture.core.handlers.UpgradeHandler;
import joshie.mariculture.core.handlers.VatHandler;
import joshie.mariculture.core.handlers.WorldEventHandler;
import joshie.mariculture.core.handlers.WorldGenHandler;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.core.items.ItemBottle;
import joshie.mariculture.core.items.ItemBuckets;
import joshie.mariculture.core.items.ItemCrafting;
import joshie.mariculture.core.items.ItemFluidStorage;
import joshie.mariculture.core.items.ItemFood;
import joshie.mariculture.core.items.ItemHammer;
import joshie.mariculture.core.items.ItemMaterial;
import joshie.mariculture.core.items.ItemPearl;
import joshie.mariculture.core.items.ItemUpgrade;
import joshie.mariculture.core.items.ItemWorked;
import joshie.mariculture.core.lib.BottleMeta;
import joshie.mariculture.core.lib.BucketMeta;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.EntityIds;
import joshie.mariculture.core.lib.LimestoneMeta;
import joshie.mariculture.core.lib.MaterialsMeta;
import joshie.mariculture.core.lib.MetalMeta;
import joshie.mariculture.core.lib.MetalRates;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.core.lib.RockMeta;
import joshie.mariculture.core.tile.TileAirPump;
import joshie.mariculture.core.tile.TileAnvil;
import joshie.mariculture.core.tile.TileAutohammer;
import joshie.mariculture.core.tile.TileBlockCaster;
import joshie.mariculture.core.tile.TileBookshelf;
import joshie.mariculture.core.tile.TileCrucible;
import joshie.mariculture.core.tile.TileIngotCaster;
import joshie.mariculture.core.tile.TileNuggetCaster;
import joshie.mariculture.core.tile.TileOyster;
import joshie.mariculture.core.tile.TileTankBlock;
import joshie.mariculture.core.tile.TileVat;
import joshie.mariculture.core.tile.TileVoidBottle;
import joshie.mariculture.core.util.EntityFakeItem;
import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.core.util.XPRegistry;
import joshie.maritech.items.ItemBattery;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Core extends RegistrationModule {
    public static Block air;
    public static Block glass;
    public static Block limestone;
    public static Block metals;
    public static Block pearlBlock;
    public static Block rocks;
    public static Block sands;
    public static Block transparent;
    public static Block woods;
    public static Block water;

    public static Block machines;
    public static Block machinesMulti;
    public static Block renderedMachines;
    public static Block renderedMachinesMulti;
    public static Block tanks;
    public static Block ticking;

    public static Item buckets;
    public static Item bottles;
    public static Item crafting;
    public static Item food;
    public static Item materials;
    public static Item pearls;
    public static Item upgrade;
    public static Item worked;

    public static Item batteryTitanium;
    public static Item batteryCopper;
    public static Item bucketTitanium;
    public static Item hammer;
    public static Item ladle;

    public WorldEventHandler worldGen;

    @Override
    public void registerHandlers() {
        anvil = new TileAnvil();
        casting = new CastingHandler();
        crucible = new CrucibleHandler();
        environment = new EnvironmentHandler();
        upgrades = new UpgradeHandler();
        vat = new VatHandler();

        OreDicHandler.init();
        GameRegistry.registerFuelHandler(new FuelHandler());
        GameRegistry.registerWorldGenerator(new WorldGenHandler(), 1);
        MinecraftForge.EVENT_BUS.register(new BucketHandler());
        MinecraftForge.EVENT_BUS.register(new GuiItemToolTip());
        MinecraftForge.EVENT_BUS.register(new OreDicHandler());
        FMLCommonHandler.instance().bus().register(new ServerFMLEvents());
        FMLCommonHandler.instance().bus().register(new ClientFMLEvents());

        if (WorldGen.ENABLE_REPLACEMENTS) {
            MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
        }

        BiomeGenBase.getBiomeGenArray()[BiomeGenBase.ocean.biomeID].setHeight(new Height((float) WorldGen.OCEAN_ROOT, (float) WorldGen.OCEAN_VARIATION));
        BiomeGenBase.getBiomeGenArray()[BiomeGenBase.deepOcean.biomeID].setHeight(new Height((float) WorldGen.OCEAN_DEEP_ROOT, (float) WorldGen.OCEAN_DEEP_VARIATION));

        if (RetroGen.ENABLED) {
            MinecraftForge.EVENT_BUS.register(new RetroGeneration());
        }
    }

    ToolMaterial brick = EnumHelper.addToolMaterial("BRICK", 1, 1000, 3.0F, 1.2F, 12);

    @Override
    public void registerItems() {
        materials = new ItemMaterial().setUnlocalizedName("materials");
        crafting = new ItemCrafting().setUnlocalizedName("crafting");
        pearls = new ItemPearl().setUnlocalizedName("pearls");
        food = new ItemFood().setUnlocalizedName("food");
        upgrade = new ItemUpgrade().setUnlocalizedName("upgrade");
        hammer = new ItemHammer(brick).setUnlocalizedName("hammer");
        ladle = new ItemFluidStorage(MetalRates.INGOT).setUnlocalizedName("ladle");
        bucketTitanium = new ItemFluidStorage(8000).setUnlocalizedName("bucket.titanium");
        bottles = new ItemBottle().setUnlocalizedName("fluids");
        buckets = new ItemBuckets().setUnlocalizedName("bucket");
        batteryCopper = new ItemBattery(10000, 100, 250).setUnlocalizedName("battery.copper");
        batteryTitanium = new ItemBattery(100000, 1000, 2500).setUnlocalizedName("battery.titanium");
        worked = new ItemWorked().setUnlocalizedName("worked");
    }

    @Override
    public void registerFluids() {
        Fluids.add("water", FluidRegistry.WATER, 1000);
        Fluids.add("lava", FluidRegistry.LAVA, 100);

        // Normal Fluids
        addGas("natural_gas", "gas.natural", 2000, BottleMeta.GAS);
        addFluid("hp_water", "fastwater", 1000, BottleMeta.HP_WATER, 250);

        // Molten Mariculture Metals + Vanilla Fluids
        addFluid("quicklime", 1000, BottleMeta.QUICKLIME, 900);
        addFluid("salt", "salt.molten", 1000, BottleMeta.SALT, 20);
        addFluid("glass", "glass.molten", 1000, BottleMeta.GLASS, 1000);
        addFluid("aluminum", "aluminum.molten", MetalRates.ORE, BottleMeta.ALUMINUM, 144);
        addFluid("magnesium", "magnesium.molten", MetalRates.ORE, BottleMeta.MAGNESIUM, 144);
        addFluid("titanium", "titanium.molten", MetalRates.ORE, BottleMeta.TITANIUM, 144);
        addFluid("copper", "copper.molten", MetalRates.ORE, BottleMeta.COPPER, 144);
        addFluid("rutile", "rutile.molten", MetalRates.ORE, BottleMeta.RUTILE, 144);
        addFluid("iron", "iron.molten", MetalRates.ORE, BottleMeta.IRON, 144);
        addFluid("gold", "gold.molten", MetalRates.ORE, BottleMeta.GOLD, 144);

        // Modded Fluids
        addFluid("tin", "tin.molten", MetalRates.ORE, BottleMeta.TIN, 144);
        addFluid("lead", "lead.molten", MetalRates.ORE, BottleMeta.LEAD, 144);
        addFluid("silver", "silver.molten", MetalRates.ORE, BottleMeta.SILVER, 144);
        addFluid("nickel", "nickel.molten", MetalRates.ORE, BottleMeta.NICKEL, 144);
        addFluid("bronze", "bronze.molten", MetalRates.ORE, BottleMeta.BRONZE, 144);
        addFluid("steel", "steel.molten", MetalRates.ORE, BottleMeta.STEEL, 144);
        addFluid("electrum", "electrum.molten", MetalRates.ORE, BottleMeta.ELECTRUM, 144);
        addFluid("xp", 100);
        XPRegistry.register("xp", 100);

        registerVanillaBottle(getFluid("natural_gas"), 1000, BottleMeta.GAS_BASIC);
        registerHeatBottle(FluidRegistry.WATER, 2000, BottleMeta.WATER);
        registerHeatBottle(FluidRegistry.LAVA, 2000, BottleMeta.LAVA);
        registerBucket(getFluid("hp_water"), 1000, BucketMeta.PRESSURE);
    }

    @Override
    public void registerBlocks() {
        FluidHelper.setBlock(BlockPressurisedWater.class, "hp_water", "highPressureWater");
        rocks = new BlockRock().setStepSound(Block.soundTypeStone).setResistance(2F).setBlockName("rocks");
        limestone = new BlockLimestone().setStepSound(Block.soundTypeStone).setResistance(1F).setBlockName("limestone");
        metals = new BlockMetal().setStepSound(Block.soundTypeMetal).setResistance(5F).setBlockName("metals");
        pearlBlock = new BlockPearlBlock("pearlBlock_").setStepSound(Block.soundTypeStone).setResistance(1.5F).setBlockName("pearl.block");
        machines = new BlockMachine().setStepSound(Block.soundTypeWood).setResistance(10F).setBlockName("machines.single");
        machinesMulti = new BlockMachineMulti().setStepSound(Block.soundTypeStone).setResistance(20F).setBlockName("machines.multi");
        renderedMachines = new BlockRenderedMachine().setStepSound(Block.soundTypeMetal).setResistance(1F).setHardness(1F).setBlockName("machines.single.rendered");
        renderedMachinesMulti = new BlockRenderedMachineMulti().setStepSound(Block.soundTypeMetal).setResistance(3F).setHardness(3F).setBlockName("machines.multi.rendered");
        glass = new BlockGlass().setStepSound(Block.soundTypeGlass).setResistance(5F).setBlockName("glass");
        air = new BlockAir().setBlockName("air");
        woods = new BlockWood().setStepSound(Block.soundTypeWood).setBlockName("woods").setHardness(2.0F);
        tanks = new BlockTank().setStepSound(Block.soundTypeGlass).setBlockName("tanks").setHardness(1F);
        sands = new BlockGround().setBlockName("sands").setHardness(1F);
        transparent = new BlockTransparent().setStepSound(Block.soundTypePiston).setBlockName("transparent").setHardness(1F);
        ticking = new BlockTicking().setStepSound(Block.soundTypeCloth).setHardness(0.05F).setBlockName("ticking");
        water = new BlockWater().setStepSound(Block.soundTypeSnow).setHardness(10F).setBlockName("water");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileAirPump.class, TileCrucible.class, TileBookshelf.class, TileTankBlock.class, TileVat.class, TileAnvil.class, TileIngotCaster.class, TileVoidBottle.class, TileOyster.class, TileBlockCaster.class, TileNuggetCaster.class, TileAutohammer.class });
    }

    @Override
    public void registerOther() {
        addToOreDictionary();
        registerBiomes();
        registerEntities();
        registerPearls();

        if (MaricultureTab.tabCore != null) {
            MaricultureTab.tabCore.setIcon(new ItemStack(pearls, 1, PearlColor.WHITE), true);
            MaricultureTab.tabFactory.setIcon(new ItemStack(crafting, 1, CraftingMeta.WHEEL), true);
            MaricultureTab.tabFishery.setIcon(new ItemStack(Items.fish), true);
            MaricultureTab.tabWorld.setIcon(new ItemStack(limestone, 1, 0), true);
        }
    }

    private void registerEntities() {
        EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", EntityIds.FAKE_ITEM, Mariculture.instance, 80, 3, false);
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

        // 1.7 Biomes
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
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLACK), 7);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLUE), 9);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BROWN), 12);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GOLD), 5);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GREEN), 9);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.ORANGE), 11);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PINK), 10);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PURPLE), 9);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.RED), 9);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.SILVER), 6);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.WHITE), 7);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.YELLOW), 6);
        PearlGenHandler.addPearl(new ItemStack(Blocks.sand), 15);
        if (Gardening.GEN_ENDER_PEARLS) {
            PearlGenHandler.addPearl(new ItemStack(Items.ender_pearl), 1);
        }
    }

    private void addToOreDictionary() {
        OreDictionary.registerOre("blockLimestone", new ItemStack(limestone, 1, LimestoneMeta.RAW));
        OreDictionary.registerOre("limestone", new ItemStack(limestone, 1, LimestoneMeta.RAW));
        OreDictionary.registerOre("oreCopper", new ItemStack(rocks, 1, RockMeta.COPPER));
        OreDictionary.registerOre("oreAluminum", new ItemStack(rocks, 1, RockMeta.BAUXITE));
        OreDictionary.registerOre("oreRutile", new ItemStack(rocks, 1, RockMeta.RUTILE));

        OreDictionary.registerOre("blockAluminum", new ItemStack(metals, 1, MetalMeta.ALUMINUM_BLOCK));
        OreDictionary.registerOre("blockCopper", new ItemStack(metals, 1, MetalMeta.COPPER_BLOCK));
        OreDictionary.registerOre("blockMagnesium", new ItemStack(metals, 1, MetalMeta.MAGNESIUM_BLOCK));
        OreDictionary.registerOre("blockRutile", new ItemStack(metals, 1, MetalMeta.RUTILE_BLOCK));
        OreDictionary.registerOre("blockTitanium", new ItemStack(metals, 1, MetalMeta.TITANIUM_BLOCK));

        OreDictionary.registerOre("dustMagnesium", new ItemStack(materials, 1, MaterialsMeta.DUST_MAGNESITE));
        OreDictionary.registerOre("foodSalt", new ItemStack(materials, 1, MaterialsMeta.DUST_SALT));
        OreDictionary.registerOre("ingotAluminum", new ItemStack(materials, 1, MaterialsMeta.INGOT_ALUMINUM));
        OreDictionary.registerOre("ingotCopper", new ItemStack(materials, 1, MaterialsMeta.INGOT_COPPER));
        OreDictionary.registerOre("ingotMagnesium", new ItemStack(materials, 1, MaterialsMeta.INGOT_MAGNESIUM));
        OreDictionary.registerOre("ingotRutile", new ItemStack(materials, 1, MaterialsMeta.INGOT_RUTILE));
        OreDictionary.registerOre("ingotTitanium", new ItemStack(materials, 1, MaterialsMeta.INGOT_TITANIUM));

        OreDictionary.registerOre("dyeYellow", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW));
        OreDictionary.registerOre("dyeRed", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED));
        OreDictionary.registerOre("dyeBrown", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN));
        OreDictionary.registerOre("dyeGreen", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN));
        OreDictionary.registerOre("dyeWhite", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_WHITE));
        OreDictionary.registerOre("dyeBlue", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BLUE));

        OreDictionary.registerOre("nuggetAluminum", new ItemStack(materials, 1, MaterialsMeta.NUGGET_ALUMINUM));
        OreDictionary.registerOre("nuggetCopper", new ItemStack(materials, 1, MaterialsMeta.NUGGET_COPPER));
        OreDictionary.registerOre("nuggetMagnesium", new ItemStack(materials, 1, MaterialsMeta.NUGGET_MAGNESIUM));
        OreDictionary.registerOre("nuggetRutile", new ItemStack(materials, 1, MaterialsMeta.NUGGET_RUTILE));
        OreDictionary.registerOre("nuggetTitanium", new ItemStack(materials, 1, MaterialsMeta.NUGGET_TITANIUM));
        OreDictionary.registerOre("nuggetIron", new ItemStack(materials, 1, MaterialsMeta.NUGGET_IRON));
    }

    @Override
    public void registerRecipes() {
        Recipes.add();
    }

    @Override
    public void postInit() {
        RecipesSmelting.postAdd();
        super.postInit();
    }
}
