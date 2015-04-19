package mariculture.core;

import static mariculture.api.core.MaricultureHandlers.anvil;
import static mariculture.api.core.MaricultureHandlers.casting;
import static mariculture.api.core.MaricultureHandlers.crucible;
import static mariculture.api.core.MaricultureHandlers.environment;
import static mariculture.api.core.MaricultureHandlers.upgrades;
import static mariculture.api.core.MaricultureHandlers.vat;
import static mariculture.core.helpers.FluidHelper.addFluid;
import static mariculture.core.helpers.FluidHelper.addGas;
import static mariculture.core.helpers.FluidHelper.registerBucket;
import static mariculture.core.helpers.FluidHelper.registerHeatBottle;
import static mariculture.core.helpers.FluidHelper.registerVanillaBottle;
import static mariculture.core.util.Fluids.getFluid;
import mariculture.Mariculture;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.BlockAir;
import mariculture.core.blocks.BlockGlass;
import mariculture.core.blocks.BlockGround;
import mariculture.core.blocks.BlockLimestone;
import mariculture.core.blocks.BlockMachine;
import mariculture.core.blocks.BlockMachineMulti;
import mariculture.core.blocks.BlockMetal;
import mariculture.core.blocks.BlockPearlBlock;
import mariculture.core.blocks.BlockPressurisedWater;
import mariculture.core.blocks.BlockRenderedMachine;
import mariculture.core.blocks.BlockRenderedMachineMulti;
import mariculture.core.blocks.BlockRock;
import mariculture.core.blocks.BlockTank;
import mariculture.core.blocks.BlockTicking;
import mariculture.core.blocks.BlockTransparent;
import mariculture.core.blocks.BlockWater;
import mariculture.core.blocks.BlockWood;
import mariculture.core.config.Gardening;
import mariculture.core.config.WorldGeneration.RetroGen;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.gui.GuiItemToolTip;
import mariculture.core.handlers.BucketHandler;
import mariculture.core.handlers.CastingHandler;
import mariculture.core.handlers.ClientFMLEvents;
import mariculture.core.handlers.CrucibleHandler;
import mariculture.core.handlers.EnvironmentHandler;
import mariculture.core.handlers.FuelHandler;
import mariculture.core.handlers.OreDicHandler;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.handlers.ServerFMLEvents;
import mariculture.core.handlers.UpgradeHandler;
import mariculture.core.handlers.VatHandler;
import mariculture.core.handlers.WorldEventHandler;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.items.ItemBottle;
import mariculture.core.items.ItemBuckets;
import mariculture.core.items.ItemCrafting;
import mariculture.core.items.ItemFluidStorage;
import mariculture.core.items.ItemFood;
import mariculture.core.items.ItemHammer;
import mariculture.core.items.ItemMaterial;
import mariculture.core.items.ItemPearl;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RockMeta;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileAutohammer;
import mariculture.core.tile.TileBlockCaster;
import mariculture.core.tile.TileBookshelf;
import mariculture.core.tile.TileCrucible;
import mariculture.core.tile.TileIngotCaster;
import mariculture.core.tile.TileNuggetCaster;
import mariculture.core.tile.TileOyster;
import mariculture.core.tile.TileTankAluminum;
import mariculture.core.tile.TileTankBlock;
import mariculture.core.tile.TileTankTitanium;
import mariculture.core.tile.TileVat;
import mariculture.core.tile.TileVoidBottle;
import mariculture.core.util.EntityFakeItem;
import mariculture.core.util.Fluids;
import mariculture.core.util.XPRegistry;
import mariculture.factory.tile.TileGasTank;
import mariculture.lib.helpers.RegistryHelper;
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
        addFluid("cobalt", "cobalt.molten", MetalRates.ORE, BottleMeta.COBALT, 144);
        addFluid("ardite", "ardite.molten", MetalRates.ORE, BottleMeta.ARDITE, 144);
        addFluid("osmium", "osmium.molten", MetalRates.ORE, BottleMeta.OSMIUM, 144);
        addFluid("platinum", "platinum.molten", MetalRates.ORE, BottleMeta.PLATINUM, 144);
        addFluid("zinc", "zinc.molten", MetalRates.ORE, BottleMeta.ZINC, 144);
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
        sands = new BlockGround().setStepSound(Block.soundTypeSand).setBlockName("sands").setHardness(1F);
        transparent = new BlockTransparent().setStepSound(Block.soundTypePiston).setBlockName("transparent").setHardness(1F);
        ticking = new BlockTicking().setStepSound(Block.soundTypeCloth).setHardness(0.05F).setBlockName("ticking");
        water = new BlockWater().setStepSound(Block.soundTypeSnow).setHardness(10F).setBlockName("water");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileAirPump.class, TileCrucible.class, TileBookshelf.class, TileTankBlock.class, TileVat.class, TileAnvil.class, 
                TileIngotCaster.class, TileVoidBottle.class, TileOyster.class, TileBlockCaster.class, TileNuggetCaster.class, TileAutohammer.class, TileTankAluminum.class, 
                TileTankTitanium.class, TileGasTank.class });
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
        /* PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLACK), 14);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BLUE), 18);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.BROWN), 25);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GOLD), 5);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.GREEN), 18);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.ORANGE), 22);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PINK), 20);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.PURPLE), 18);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.RED), 18);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.SILVER), 6);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.WHITE), 8);
        PearlGenHandler.addPearl(new ItemStack(Core.pearls, 1, PearlColor.YELLOW), 7);
        PearlGenHandler.addPearl(new ItemStack(Blocks.sand, 1, 0), 30);
        PearlGenHandler.addPearl(new ItemStack(Blocks.sand, 1, 1), 15); */
        if (Gardening.GEN_ENDER_PEARLS) {
            PearlGenHandler.addPearl(new ItemStack(Items.ender_pearl), 2);
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
