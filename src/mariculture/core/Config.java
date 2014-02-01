package mariculture.core;

import java.io.File;
import java.util.logging.Level;

import mariculture.core.lib.BlockIds;
import mariculture.core.lib.Compatibility;
import mariculture.core.lib.EnchantIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.lib.config.Category;
import mariculture.core.lib.config.Comment;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

public class Config {
    public static void init(String dir) {
        initOther(new Configuration(new File(dir, "other.cfg")));
        initIDs(new Configuration(new File(dir, "ids.cfg")));
        initMachines(new Configuration(new File(dir, "mechanics.cfg")));
        initModules(new Configuration(new File(dir, "modules.cfg")));
        initWorld(new Configuration(new File(dir, "worldgen.cfg")));
    }

    private static void initOther(Configuration config) {
        try {
            config.load();
               
            Extra.SPAWN_BOOKS = config.get(Category.EXTRA, "Spawn Books on First Action", true).getBoolean(true);
            Extra.JEWELRY_TICK_RATE = config.get(Category.EXTRA, "Jewelry Tick Rate", 60, Comment.JEWELRY_TICK_RATE).getInt();
            Extra.HARDCORE_DIVING = config.get(Category.DIFF, "Hardcore Diving Setting", 0, Comment.HARDCORE).getInt();
            Extra.REFRESH_CLIENT_RATE = config.get(Category.EXTRA, "Server-Client Refresh Rate", 30, Comment.REFRESH).getInt();
            Extra.DEBUG_ON = config.get(Category.EXTRA, "Debug Mode Enabled", false).getBoolean(false);
            Extra.METAL_RATE = config.get(Category.EXTRA, "Molten Metal Nugget mB Value", 16, Comment.METAL).getInt();
            Extra.FLUDD_WATER_ON = config.get(Category.CLIENT, "Enable FLUDD Animations", true, Comment.FLUDD).getBoolean(true);
            Extra.ENABLE_ENDER_SPAWN = config.get(Category.EXTRA, "Enable Ender Dragon Spawning", true, Comment.ENDERDRAGON).getBoolean(true);
            Extra.DROP_JEWELRY = config.get(Category.EXTRA, "Jewelry Drops on Death", false).getBoolean(false);
            Extra.MOB_MAGNET = config.get(Category.EXTRA, "Mob Magnet Crafting Enabled", true).getBoolean(true);
            Extra.PERCENT_NEEDED = config.get(Category.EXTRA, "Percentage Needed for Timelord Enchant", 5).getInt();
            
            Compatibility.ENABLE_WHITELIST = config.get(Category.DICTIONARY, "AutoDictionary > Use Whitelist", false).getBoolean(false);
            Compatibility.BLACKLIST = config.get(Category.DICTIONARY, "AutoDictionary > Blacklist", Compatibility.BLACKLIST_DEFAULT, Comment.BLACKLIST).getStringList();
            Compatibility.WHITELIST = config.get(Category.DICTIONARY, "AutoDictionary > Whitelist", Compatibility.WHITELIST_DEFAULT, Comment.WHITELIST).getStringList();
            Compatibility.EXCEPTIONS = config.get(Category.DICTIONARY, "AutoDictionary > Exceptions", Compatibility.EXCEPTIONS_DEFAULT, Comment.EXCEPTIONS).getStringList();
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Mariculture had a problem loading the other settings");
        } finally {
            config.save();
        }
    }

    private static void initWorld(Configuration config) {
        try {
            config.load();
            config.addCustomCategoryComment(Category.ORE, Comment.ORE);
            config.addCustomCategoryComment(Category.RETRO, Comment.RETRO);

            Extra.RIVER_FORCE = config.get(Category.BIOME, "River Biomes - Only Config", false).getBoolean(false);
            Extra.RIVER_BIOMES = config.get(Category.BIOME, "River Biomes", Extra.RIVERS_DEFAULT, Comment.RIVER).getIntList();
            Extra.OCEAN_FORCE = config.get(Category.BIOME, "Ocean Biomes - Only Config", false).getBoolean(false);
            Extra.OCEAN_BIOMES = config.get(Category.BIOME, "Ocean Biomes", Extra.OCEANS_DEFAULT, Comment.OCEAN).getIntList();

            OreGeneration.BAUXITE_ON = config.get(Category.ORE, "Bauxite > Generation", true).getBoolean(true);
            OreGeneration.BAUXITE_TOTAL = config.get(Category.ORE, "Bauxite > Number of Veins", 16).getInt();
            OreGeneration.BAUXITE_VEIN = config.get(Category.ORE, "Bauxite > Maximum Vein Size", 8).getInt();
            OreGeneration.BAUXITE_MIN = config.get(Category.ORE, "Bauxite > Minimum Y Height", 60).getInt();
            OreGeneration.BAUXITE_MAX = config.get(Category.ORE, "Bauxite > Maximum Y Height", 256).getInt();
            OreGeneration.COPPER_ON = config.get(Category.ORE, "Copper > Generation", false).getBoolean(false);
            OreGeneration.COPPER_TOTAL = config.get(Category.ORE, "Copper > Number of Veins", 8).getInt();
            OreGeneration.COPPER_VEIN = config.get(Category.ORE, "Copper > Maximum Vein Size", 3).getInt();
            OreGeneration.COPPER_MIN = config.get(Category.ORE, "Copper > Minimum Y Height", 1).getInt();
            OreGeneration.COPPER_MAX = config.get(Category.ORE, "Copper > Maximum Y Height", 64).getInt();
            OreGeneration.RUTILE = config.get(Category.ORE, "Rutile > Generation", true).getBoolean(true);
            OreGeneration.RUTILE_CHANCE = config.get(Category.ORE, "Rutile > 1 Vein Per This Many Limestone", 250).getInt();
            OreGeneration.LIMESTONE = config.get(Category.ORE, "Limestone > Generation", true).getBoolean(true);
            OreGeneration.LIMESTONE_CHANCE = config.get(Category.ORE, "Limestone > Number of Chances Per Chunk to Generate", 3).getInt();
            OreGeneration.LIMESTONE_VEIN = config.get(Category.ORE, "Limestone > Maximum Vein Size", 32).getInt();
            OreGeneration.LIMESTONE_MAX_DEPTH = config.get(Category.ORE, "Limestone > Maximum Depth (Y Height)", 40).getInt();
            OreGeneration.NATURAL_GAS_ON = config.get(Category.ORE, "Natural Gas > Generation", true).getBoolean(true);
            OreGeneration.NATURAL_GAS_CHANCE = config.get(Category.ORE, "Natural Gas > 1 Pocket Per This Many Chunks", 20).getInt();
            OreGeneration.NATURAL_GAS_VEIN = config.get(Category.ORE, "Natural Gas > Maximum Vein Size", 48).getInt();
            OreGeneration.NATURAL_GAS_MIN = config.get(Category.ORE, "Natural Gas > Minimum Y Height", 16).getInt();
            OreGeneration.NATURAL_GAS_MAX = config.get(Category.ORE, "Natural Gas > Maximum Y Height", 26).getInt();

            WorldGeneration.CORAL_BIOMESOP = config.get(Category.WORLD, "Coral > BiomesOPlenty Forced", false, Comment.BIOMESOP_CORAL).getBoolean(false);
            WorldGeneration.CORAL_ENABLED = config.get(Category.WORLD, "Coral > Generation", true).getBoolean(true);
            WorldGeneration.CORAL_CHANCE = config.get(Category.WORLD, "Coral > 1 Reef Per this Many Chunks", 32).getInt();
            WorldGeneration.CORAL_DEPTH = config.get(Category.WORLD, "Coral > Maximum Depth", 25).getInt();
            WorldGeneration.KELP_CHANCE = config.get(Category.WORLD, "Kelp > 1 Forest Per This Many Chunks", 400).getInt();
            WorldGeneration.KELP_DEPTH = config.get(Category.WORLD, "Kelp > Maximum Depth", 35).getInt();
            WorldGeneration.KELP_HEIGHT = config.get(Category.WORLD, "Kelp > Maximum World Gen Height", 25).getInt();
            WorldGeneration.KELP_PATCH_ENABLED = config.get(Category.WORLD, "Kelp > Single Generation", true).getBoolean(true);
            WorldGeneration.KELP_PATCH_DENSITY = config.get(Category.WORLD, "Kelp > Single Density", 10).getInt();
            WorldGeneration.KELP_FOREST_ENABLED = config.get(Category.WORLD, "Kelp > Forest Generation", true).getBoolean(true);
            WorldGeneration.KELP_FOREST_DENSITY = config.get(Category.WORLD, "Kelp > Forest Density", 25).getInt();
            WorldGeneration.KELP_CHEST_CHANCE = config.get(Category.WORLD, "Kelp > 1 Treasure Chest Per This Many Kelp", 1024).getInt();
            WorldGeneration.KELP_BIOMESOP = config.get(Category.WORLD, "Kelp > (Forest) BiomesOPlenty Forced", false, Comment.BIOMESOP_CORAL).getBoolean(false);
            WorldGeneration.DEEP_OCEAN = config.get(Category.WORLD, "Deep Oceans", false).getBoolean(false);
            WorldGeneration.WATER_CAVES = config.get(Category.WORLD, "Water Filled Caves in Oceans", false).getBoolean(false);
            WorldGeneration.WATER_RAVINES = config.get(Category.WORLD, "Water Filled Ravines in Oceans", false).getBoolean(false);
            WorldGeneration.RAVINE_CHANCE = config.get(Category.WORLD, "Water Ravine Chance (Lower = More Common)", 25).getInt();
            WorldGeneration.NO_MINESHAFTS = config.get(Category.WORLD, "Remove Mineshafts in Oceans", false).getBoolean(false);
            WorldGeneration.OYSTER_ENABLED = config.get(Category.WORLD, "Pearl Oyster > Generation", true).getBoolean(true);
            WorldGeneration.OYSTER_PER_CHUNK = config.get(Category.WORLD, "Pearl Oyster > Number Chances to Gen Per Chunk", 3).getInt(3);
            WorldGeneration.OYSTER_CHANCE = config.get(Category.WORLD, "Pearl Oyster > 1 Oyster per This Many Blocks Per Chunk", 12).getInt();
            WorldGeneration.OYSTER_PEARL_CHANCE = config.get(Category.WORLD, "Pearl Oyster > 1 Natural Pearl Per this Many Oysters", 3).getInt();
            
            RetroGeneration.ENABLED = config.get(Category.RETRO, "Enable Retro-Gen", false).getBoolean(false);
            RetroGeneration.KEY = config.get(Category.RETRO, "Key", 555, Comment.RETRO_KEY).getInt();
            RetroGeneration.ALL = config.get(Category.RETRO, "All", true).getBoolean(true);
            RetroGeneration.BAUXITE = config.get(Category.RETRO, "Bauxite", false).getBoolean(false);
            RetroGeneration.COPPER = config.get(Category.RETRO, "Copper", false).getBoolean(false);
            RetroGeneration.CORALREEF = config.get(Category.RETRO, "Coral Reef", false).getBoolean(false);
            RetroGeneration.GAS = config.get(Category.RETRO, "Natural Gas", false).getBoolean(false);
            RetroGeneration.KELPFOREST = config.get(Category.RETRO, "Kelp Forest", false).getBoolean(false);
            RetroGeneration.KELPPATCH = config.get(Category.RETRO, "Kelp Patch", false).getBoolean(false);
            RetroGeneration.LIMESTONE = config.get(Category.RETRO, "Limestone", false).getBoolean(false);
            RetroGeneration.OYSTER = config.get(Category.RETRO, "Oysters", false).getBoolean(false);
            RetroGeneration.RUTILE = config.get(Category.RETRO, "Rutile", false).getBoolean(false);
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Oh dear, there was a problem with Mariculture loading it's world configuration");
        } finally {
            config.save();
        }
    }

    private static void initModules(Configuration config) {
        try {
            config.load();
            Modules.core.setActive(true);
            Modules.diving.setActive(config.get(Category.MODULES, "Diving", true).getBoolean(true));
            Modules.factory.setActive(config.get(Category.MODULES, "Factory", true).getBoolean(true));
            Modules.fishery.setActive(config.get(Category.MODULES, "Fishery", true).getBoolean(true));
            Modules.magic.setActive(config.get(Category.MODULES, "Magic", true).getBoolean(true));
            Modules.sealife.setActive(false);
            Modules.transport.setActive(config.get(Category.MODULES, "Transport", true).getBoolean(true));
            Modules.world.setActive(config.get(Category.MODULES, "World Plus", true).getBoolean(true));
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Problem with Mariculture when copying over the module data");
        } finally {
            config.save();
        }
    }

    private static void initMachines(Configuration config) {
        try {
            config.load();

            MachineSpeeds.autofisher = config.get(Category.SPEED, "Automatic Fisher", 2500).getInt();
            MachineSpeeds.dictionary = config.get(Category.SPEED, "Autodictionary", 1).getInt();
            MachineSpeeds.feeder = config.get(Category.SPEED, "Fish Feeder", 200).getInt();
            MachineSpeeds.incubator = config.get(Category.SPEED, "Incubator", 400).getInt();
            MachineSpeeds.liquifier = config.get(Category.SPEED, "Industrial Smelter", 40000).getInt();
            MachineSpeeds.net = config.get(Category.SPEED, "Fishing Net", 350).getInt();
            MachineSpeeds.sawmill = config.get(Category.SPEED, "Sawmill", 650).getInt();
            MachineSpeeds.settler = config.get(Category.SPEED, "Industrial Freezer", 60000).getInt();
            MachineSpeeds.oven = config.get(Category.SPEED, "Gas Oven", 450).getInt();

            Extra.GEYSER_ANIM = config.get(Category.CLIENT, "Geyser - Enable Particles", true).getBoolean(true);
            Extra.FLUDD_BLOCK_ANIM = config.get(Category.CLIENT, "FLUDD - Enable Particles", true).getBoolean(true);
            Extra.TURBINE_ANIM = config.get(Category.CLIENT, "Turbines - Enable Rotation", true).getBoolean(true);
            
            Extra.TURBINE_RATE = config.get(Category.EXTRA, "Turbines - Ticks between Packet Updates", 20).getInt();
            Extra.PURITY = config.get(Category.EXTRA, "Crucible Furnace > Nuggets Per Purity Upgrade Level", 2).getInt();
            Extra.ENDER_CONVERTER = config.get(Category.EXTRA, "Autodictionary > Enable Ender Pearl for Recipe", false).getBoolean(false);
            Extra.CAN_WORK_TICK = config.get(Category.EXTRA, "Can Work Tick", 20, Comment.CAN_WORK_TICK).getInt();
            Extra.DRAGON_EGG_ETHEREAL = config.get(Category.EXTRA, "Incubator > Dragon Egg Chance - Ethereal", 48000, Comment.DRAGON_EGG_ETHEREAL).getInt();
            Extra.DRAGON_EGG_BASE = config.get(Category.EXTRA, "Incubator > Dragon Egg Chance", 64000, Comment.DRAGON_EGG_BASE).getInt();
            Extra.DEATH_TICKER = config.get(Category.EXTRA, "Fish Feeder > Death Tick", 20, Comment.DEATH_TICK).getInt();
            Extra.EFFECT_TICK = config.get(Category.EXTRA, "Fish Feeder > Effect Tick", 20, Comment.EFFECT_TICK).getInt();
            Extra.FISH_FOOD_TICK = config.get(Category.EXTRA, "Fish Feeder > Fish Food Tick Rate", 25, Comment.FISH_FOOD_TICK).getInt();
            Extra.TANK_UPDATE = config.get(Category.EXTRA, "Fish Feeder > Tank Update", 5, Comment.TANK_UPDATE).getInt();
            Extra.ACTIVATE_PUMP = config.get(Category.EXTRA, "Air Pump > Manual Power Enabled", true, Comment.PUMP_MANUAL).getBoolean(true);
            Extra.REDSTONE_PUMP = config.get(Category.EXTRA, "Air Pump > Redstone Power Enabled", false, Comment.PUMP_REDSTONE).getBoolean(false);
            Extra.BUILDCRAFT_PUMP = config.get(Category.EXTRA, "Air Pump > MJ/RF Power Enabled", true, Comment.PUMP_RF).getBoolean(true);
            Extra.OVERWORLD = config.get(Category.EXTRA, "Crucible Furnace > Enable Overworld Burnt Brick Recipe", true).getBoolean(true);

            Extra.bait0 = config.get(Category.PROD, "Bait Quality 0 Chance", 20, "Ant - " + Comment.BAIT).getInt();
            Extra.bait1 = config.get(Category.PROD, "Bait Quality 1 Chance", 16, "Bread - " + Comment.BAIT).getInt();
            Extra.bait2 = config.get(Category.PROD, "Bait Quality 2 Chance", 11, "Maggot/Grasshopper - " + Comment.BAIT).getInt();
            Extra.bait3 = config.get(Category.PROD, "Bait Quality 3 Chance", 6, "Worm - " + Comment.BAIT).getInt();
            Extra.bait4 = config.get(Category.PROD, "Bait Quality 4 Chance", 3, Comment.BAIT).getInt();
            Extra.bait5 = config.get(Category.PROD, "Bait Quality 5 Chance", 1, "Minnow - " + Comment.BAIT).getInt();
            Extra.CORAL_SPREAD_CHANCE = config.get(Category.PROD, "Coral Spread Chance", 75, Comment.CORAL_SPREAD).getInt();
            Extra.KELP_SPREAD_CHANCE = config.get(Category.PROD, "Kelp Spread Moss Chance", 65, Comment.KELP_SPREAD).getInt();
            Extra.KELP_GROWTH_CHANCE = config.get(Category.PROD, "Kelp Growth Chance", 200, Comment.KELP_GROWTH).getInt();
            Extra.GEN_ENDER_PEARLS = config.get(Category.EXTRA, "Pearl Oyster > Generate Ender Pearls", true).getBoolean(true);
            Extra.PEARL_GEN_CHANCE = config.get(Category.PROD, "Pearl Oyster > Pearl Generation Chance", 32, Comment.PEARL_CHANCE).getInt();
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "There was an issue with Mariculture when adjusting machine settings");
        } finally {
            config.save();
        }
    }

    private static void initIDs(Configuration config) {
        try {
            config.load();
            /** BEGIN BLOCK IDS **/
            //Basic Blocks

            BlockIds.ores = config.getBlock("Ore Blocks", 750).getInt();
            BlockIds.util = config.getBlock("Machine Blocks", 751).getInt();
            BlockIds.singleBlocks = config.getBlock("Single Blocks", 752).getInt();
            BlockIds.doubleBlocks = config.getBlock("Double Blocks", 753).getInt();
            BlockIds.oyster = config.getBlock("Oyster", 754).getInt();
            BlockIds.sift = config.getBlock("Sifter", 755).getInt();
            BlockIds.woodBlocks = config.getBlock("Wooden Blocks", 756).getInt();
            BlockIds.tankBlocks = config.getBlock("Tank Blocks", 757).getInt();
            BlockIds.groundBlocks = config.getBlock("Earthy Blocks", 758).getInt();
            //FREEID: 758 > 761
            BlockIds.lampsOn = config.getBlock("Neon Lamps On", 762).getInt();
            BlockIds.lampsOff = config.getBlock("Neon Lamps Off", 763).getInt();
            BlockIds.pearlBrick = config.getBlock("Pearl Bricks", 764).getInt();
            BlockIds.glassBlocks = config.getBlock("Glass Blocks", 765).getInt();
            BlockIds.coral = config.getBlock("Coral & Kelp", 766).getInt();
            BlockIds.airBlocks = config.getBlock("Air Blocks", 767).getInt();
            BlockIds.transparentBlocks = config.getBlock("Transparent Blocks", 768).getInt();

            //FREEID: 769 > 779

            //Custom Blocks
            BlockIds.customFlooring = config.getBlock("Custom Flooring", 780).getInt();
            BlockIds.customBlock = config.getBlock("Custom Blocks", 781).getInt();
            BlockIds.customStairs = config.getBlock("Custom Stairs", 782).getInt();
            BlockIds.customSlabs = config.getBlock("Custom Slabs Single", 783).getInt();
            BlockIds.customSlabsDouble = config.getBlock("Custom Slabs Double", 784).getInt();
            BlockIds.customFence = config.getBlock("Custom Fence", 785).getInt();
            BlockIds.customGate = config.getBlock("Custom Gate", 786).getInt();
            BlockIds.customWall = config.getBlock("Custom Wall", 787).getInt();
            BlockIds.customLight = config.getBlock("Custom Light", 788).getInt();
            BlockIds.customRFBlock = config.getBlock("Custom RF Block", 789).getInt();

            //FREEID: 790 > 799
            BlockIds.highPressureWater = config.getBlock("High Pressure Water", 800).getInt();

            /** END BLOCK IDS, BEGIN ITEM IDS **/
            ItemIds.metals = config.getItem("Materials", 29000).getInt();
            ItemIds.items = config.getItem("General Items", 29001).getInt();
            ItemIds.basicMirror = config.getItem("Basic Mirror", 29002).getInt();
            ItemIds.magicMirror = config.getItem("Magic Mirror", 29003).getInt();
            ItemIds.celestialMirror = config.getItem("Celestial Mirror", 29004).getInt();
            ItemIds.pearl = config.getItem("Pearls", 29005).getInt();
            ItemIds.batteryTitanium = config.getItem("Battery", 29006).getInt();
            ItemIds.food = config.getItem("Food", 29007).getInt();
            ItemIds.bait = config.getItem("Bait", 29008).getInt();
            ItemIds.rodReed = config.getItem("Fishing Rod - Reed", 29009).getInt();
            ItemIds.rodWood = config.getItem("Fishing Rod - Wood", 29010).getInt();
            ItemIds.rodTitanium = config.getItem("Fishing Rod - Titanium", 29011).getInt();
            ItemIds.fishy = config.getItem("Fish - Living", 29012).getInt();
            ItemIds.fishyFood = config.getItem("Fish - Raw", 29013).getInt();
            ItemIds.speedBoat = config.getItem("Speed Boat", 29014).getInt();
            ItemIds.aquaBPT1 = config.getItem("Aquatic Backpack T1", 29015).getInt();
            ItemIds.aquaBPT2 = config.getItem("Aquatic Backpack T2", 29016).getInt();
            ItemIds.ring = config.getItem("Rings", 29017).getInt();
            ItemIds.bracelet = config.getItem("Bracelets", 29018).getInt();
            ItemIds.necklace = config.getItem("Necklaces", 29019).getInt();
            ItemIds.divingHelmet = config.getItem("Diving - Helmet", 29020).getInt();
            ItemIds.divingTop = config.getItem("Diving - Breathing Tube", 29021).getInt();
            ItemIds.divingPants = config.getItem("Diving - Suit", 29022).getInt();
            ItemIds.divingBoots = config.getItem("Diving - Boots", 29023).getInt();
            ItemIds.scubaMask = config.getItem("Scuba - Mask", 29024).getInt();
            ItemIds.scubaTank = config.getItem("Scuba - Tank", 29025).getInt();
            ItemIds.scubaSuit = config.getItem("Scuba - Wetsuit", 29026).getInt();
            ItemIds.swimfin = config.getItem("Scuba - Flippers", 29027).getInt();
            ItemIds.liquidContainers = config.getItem("Liquid Containers", 29028).getInt();
            ItemIds.upgrade = config.getItem("Upgrades", 29029).getInt();
            ItemIds.net = config.getItem("Fishing Net", 29030).getInt();
            ItemIds.plans = config.getItem("Plans", 29031).getInt();
            ItemIds.fludd = config.getItem("FLUDD", 29032).getInt();
            ItemIds.paintbrush = config.getItem("Paintbrush", 29033).getInt();
           
            //Titanium Parts
            ItemIds.titanium_part_1 = config.getItem("TiC Titanium - Arrowhead", 29034).getInt();
            ItemIds.titanium_part_2 = config.getItem("TiC Titanium - Axe Head", 29035).getInt();
            ItemIds.titanium_part_3 = config.getItem("TiC Titanium - Sign Head", 29036).getInt();
            ItemIds.titanium_part_4 = config.getItem("TiC Titanium - Binding", 29037).getInt();
            ItemIds.titanium_part_5 = config.getItem("TiC Titanium - Chisel Head", 29038).getInt();
            ItemIds.titanium_part_6 = config.getItem("TiC Titanium - Shard", 29039).getInt();
            ItemIds.titanium_part_7 = config.getItem("TiC Titanium - Crossbar", 29040).getInt();
            ItemIds.titanium_part_8 = config.getItem("TiC Titanium - Excavator Head", 29041).getInt();
            ItemIds.titanium_part_9 = config.getItem("TiC Titanium - Pan", 29042).getInt();
            ItemIds.titanium_part_10 = config.getItem("TiC Titanium - Full Guard", 29043).getInt();
            ItemIds.titanium_part_11 = config.getItem("TiC Titanium - Hammer Head", 29044).getInt();
            ItemIds.titanium_part_12 = config.getItem("TiC Titanium - Knife Blade", 29045).getInt();
            ItemIds.titanium_part_13 = config.getItem("TiC Titanium - Wide Guard", 29046).getInt();
            ItemIds.titanium_part_14 = config.getItem("TiC Titanium - Large Sword Blade", 29047).getInt();
            ItemIds.titanium_part_15 = config.getItem("TiC Titanium - Large Plate", 29048).getInt();
            ItemIds.titanium_part_16 = config.getItem("TiC Titanium - Broad Axe Head", 29049).getInt();
            ItemIds.titanium_part_17 = config.getItem("TiC Titanium - Hand Guard", 29050).getInt();
            ItemIds.titanium_part_18 = config.getItem("TiC Titanium - Pickaxe Head", 29051).getInt();
            ItemIds.titanium_part_19 = config.getItem("TiC Titanium - Scythe Head", 29052).getInt();
            ItemIds.titanium_part_20 = config.getItem("TiC Titanium - Shovel Head", 29053).getInt();
            ItemIds.titanium_part_21 = config.getItem("TiC Titanium - Sword Blade", 29054).getInt();
            ItemIds.titanium_part_22 = config.getItem("TiC Titanium - Tool Rod", 29055).getInt();
            ItemIds.titanium_part_23 = config.getItem("TiC Titanium - Tough Binding", 29056).getInt();
            ItemIds.titanium_part_24 = config.getItem("TiC Titanium - Tough Rod", 29057).getInt();
            
            //FREEIDS: 29069 > 29100
            ItemIds.rodFlux = config.getItem("Fishing Rod - Flux", 29058).getInt();
            ItemIds.filter = config.getItem("Item Filter", 29059).getInt();
            ItemIds.turbineCopper = config.getItem("Rotor - Copper", 29060).getInt();
            ItemIds.turbineAluminum = config.getItem("Rotor - Aluminum", 29061).getInt();
            ItemIds.turbineTitanium = config.getItem("Rotor - Titanium", 29062).getInt();
            ItemIds.snorkel = config.getItem("Snorkel", 29063).getInt();
            ItemIds.hammer = config.getItem("Pearl Hammer", 29064).getInt();
            ItemIds.worked = config.getItem("Worked Item", 29065).getInt();
            ItemIds.batteryCopper = config.getItem("Battery(Copper)", 29066).getInt();
            ItemIds.guides = config.getItem("Guidebooks", 29067).getInt();
            ItemIds.ladle = config.getItem("Ladle", 29068).getInt();
            ItemIds.magnet = config.getItem("Magnet", 29069).getInt();
            ItemIds.bucket = config.getItem("Titanium Bucket", 29070).getInt();

            /** END ITEM IDS BEGIN ENCHANT IDS **/
            EnchantIds.blink = config.get(Category.ENCHANT, "Blink", 53).getInt();
            EnchantIds.clock = config.get(Category.ENCHANT, "Timelord", 54).getInt();
            EnchantIds.fall = config.get(Category.ENCHANT, "Fall Resistance", 55).getInt();
            EnchantIds.fire = config.get(Category.ENCHANT, "Inferno", 56).getInt();
            EnchantIds.flight = config.get(Category.ENCHANT, "Superman", 57).getInt();
            EnchantIds.glide = config.get(Category.ENCHANT, "Paraglide", 58).getInt();
            EnchantIds.health = config.get(Category.ENCHANT, "1 Up", 59).getInt();
            EnchantIds.jump = config.get(Category.ENCHANT, "Leapfrog", 60).getInt();
            EnchantIds.hungry = config.get(Category.ENCHANT, "Never Hungry", 61).getInt();
            EnchantIds.oneRing = config.get(Category.ENCHANT, "The One Ring", 62).getInt();
            EnchantIds.poison = config.get(Category.ENCHANT, "Poison Ivy", 63).getInt();
            EnchantIds.punch = config.get(Category.ENCHANT, "Power Punch", 64).getInt();
            EnchantIds.repair = config.get(Category.ENCHANT, "Restoration", 65).getInt();
            EnchantIds.resurrection = config.get(Category.ENCHANT, "Reaper", 66).getInt();
            EnchantIds.speed = config.get(Category.ENCHANT, "Sonic the Hedgehog", 67).getInt();
            EnchantIds.spider = config.get(Category.ENCHANT, "Spiderman", 68).getInt();
            EnchantIds.stepUp = config.get(Category.ENCHANT, "Step Up", 69).getInt();
            EnchantIds.luck = config.get(Category.ENCHANT, "Luck of the Irish", 70).getInt();
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Mariculture had a serious issue loading it's block/item/enchant ids");
        } finally {
            config.save();
        }
    }
}
