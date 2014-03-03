package mariculture.core;

import java.io.File;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Compatibility;
import mariculture.core.lib.EnchantIds;
import mariculture.core.lib.EnchantSetting;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.lib.config.Category;
import mariculture.core.lib.config.Comment;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class Config {
    public static void init(String dir) {
        initOther(new Configuration(new File(dir, "other.cfg")));
        initEnchantments(new Configuration(new File(dir, "enchantments.cfg")));
        initMachines(new Configuration(new File(dir, "mechanics.cfg")));
        initModules(new Configuration(new File(dir, "modules.cfg")));
        initWorld(new Configuration(new File(dir, "worldgen.cfg")));
    }

    private static void initOther(Configuration config) {
        try {
            config.load();
               
            Extra.DISABLE_FISH = config.get(Category.EXTRA, "Disable Mariculture Live Fish in NEI", false).getBoolean(false);
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
        	LogHandler.log(Level.ERROR, "Mariculture had a problem loading the other settings");
        	e.printStackTrace();
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
            OreGeneration.COPPER_TOTAL = config.get(Category.ORE, "Copper > Number of Veins", 12).getInt();
            OreGeneration.COPPER_VEIN = config.get(Category.ORE, "Copper > Maximum Vein Size", 5).getInt();
            OreGeneration.COPPER_MIN = config.get(Category.ORE, "Copper > Minimum Y Height", 1).getInt();
            OreGeneration.COPPER_MAX = config.get(Category.ORE, "Copper > Maximum Y Height", 64).getInt();
            OreGeneration.RUTILE = config.get(Category.ORE, "Rutile > Generation", true).getBoolean(true);
            OreGeneration.RUTILE_CHANCE = config.get(Category.ORE, "Rutile > 1 Vein Per This Many Limestone", 250).getInt();
            OreGeneration.LIMESTONE = config.get(Category.ORE, "Limestone > Generation", true).getBoolean(true);
            OreGeneration.LIMESTONE_CHANCE = config.get(Category.ORE, "Limestone > Number of Chances Per Chunk to Generate", 1).getInt();
            OreGeneration.LIMESTONE_VEIN = config.get(Category.ORE, "Limestone > Maximum Vein Size", 32).getInt();
            OreGeneration.LIMESTONE_MAX_DEPTH = config.get(Category.ORE, "Limestone > Maximum Depth (Y Height)", 40).getInt();
            OreGeneration.NATURAL_GAS_ON = config.get(Category.ORE, "Natural Gas > Generation", true).getBoolean(true);
            OreGeneration.NATURAL_GAS_CHANCE = config.get(Category.ORE, "Natural Gas > 1 Pocket Per This Many Chunks", 20).getInt();
            OreGeneration.NATURAL_GAS_VEIN = config.get(Category.ORE, "Natural Gas > Maximum Vein Size", 48).getInt();
            OreGeneration.NATURAL_GAS_MIN = config.get(Category.ORE, "Natural Gas > Minimum Y Height", 16).getInt();
            OreGeneration.NATURAL_GAS_MAX = config.get(Category.ORE, "Natural Gas > Maximum Y Height", 26).getInt();

            WorldGeneration.CORAL_BIOMESOP = config.get(Category.WORLD, "Coral > Force in BOP Coral Biome", false, Comment.BIOMESOP_CORAL).getBoolean(false);
            WorldGeneration.CORAL_BIOMESOP_TYPES = config.get(Category.WORLD, "Coral > Force in Coral Biome Level Types", new String[] { "BIOMESOP" }).getStringList();
            WorldGeneration.CORAL_ENABLED = config.get(Category.WORLD, "Coral > Generation", true).getBoolean(true);
            WorldGeneration.CORAL_CHANCE = config.get(Category.WORLD, "Coral > 1 Reef Per this Many Chunks", 64).getInt();
            WorldGeneration.CORAL_DEPTH = config.get(Category.WORLD, "Coral > Maximum Depth", 25).getInt();
            WorldGeneration.KELP_CHANCE = config.get(Category.WORLD, "Kelp > 1 Forest Per This Many Chunks", 400).getInt();
            WorldGeneration.KELP_DEPTH = config.get(Category.WORLD, "Kelp > Maximum Depth", 35).getInt();
            WorldGeneration.KELP_HEIGHT = config.get(Category.WORLD, "Kelp > Maximum World Gen Height", 25).getInt();
            WorldGeneration.KELP_PATCH_ENABLED = config.get(Category.WORLD, "Kelp > Single Generation", true).getBoolean(true);
            WorldGeneration.KELP_PATCH_DENSITY = config.get(Category.WORLD, "Kelp > Single Density", 10).getInt();
            WorldGeneration.KELP_FOREST_ENABLED = config.get(Category.WORLD, "Kelp > Forest Generation", true).getBoolean(true);
            WorldGeneration.KELP_FOREST_DENSITY = config.get(Category.WORLD, "Kelp > Forest Density", 25).getInt();
            WorldGeneration.KELP_CHEST_CHANCE = config.get(Category.WORLD, "Kelp > 1 Treasure Chest Per This Many Kelp", 1024).getInt();
            WorldGeneration.KELP_BIOMESOP = config.get(Category.WORLD, "Kelp > (Forest) Force in BOP Kelp Biome", false, Comment.BIOMESOP_CORAL).getBoolean(false);
            WorldGeneration.KELP_BIOMESOP_TYPES = config.get(Category.WORLD, "Kelp > (Forest) Force in Kelp Biome Level Types", new String[] { "BIOMESOP" }).getStringList();
            WorldGeneration.DEEP_OCEAN = config.get(Category.WORLD, "Deep Oceans", false).getBoolean(false);
            WorldGeneration.WATER_CAVES = config.get(Category.WORLD, "Water Filled Caves in Oceans", false).getBoolean(false);
            WorldGeneration.WATER_RAVINES = config.get(Category.WORLD, "Water Filled Ravines in Oceans", true).getBoolean(true);
            WorldGeneration.RAVINE_CHANCE = config.get(Category.WORLD, "Water Ravine Chance (Lower = More Common)", 25).getInt();
            WorldGeneration.NO_MINESHAFTS = config.get(Category.WORLD, "Remove Mineshafts in Oceans", true).getBoolean(true);
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
        	LogHandler.log(Level.ERROR, "Oh dear, there was a problem with Mariculture loading it's world configuration");
        	e.printStackTrace();
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
            LogHandler.log(Level.ERROR, "Problem with Mariculture when copying over the module data");
        	e.printStackTrace();
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
            LogHandler.log(Level.ERROR, "There was an issue with Mariculture when adjusting machine settings");
        	e.printStackTrace();
        } finally {
            config.save();
        }
    }

    private static void initEnchantments(Configuration config) {
        try {
            config.load();
            EnchantIds.blink = config.get(Category.ENCHANT, "Blink", 70).getInt();
            EnchantIds.elemental = config.get(Category.ENCHANT, "Elemental Affinity", 71).getInt();
            EnchantIds.fall = config.get(Category.ENCHANT, "Fall Resistance", 72).getInt();
            EnchantIds.fire = config.get(Category.ENCHANT, "Inferno", 73).getInt();
            EnchantIds.flight = config.get(Category.ENCHANT, "Superman", 74).getInt();
            EnchantIds.glide = config.get(Category.ENCHANT, "Paraglide", 75).getInt();
            EnchantIds.health = config.get(Category.ENCHANT, "1 Up", 76).getInt();
            EnchantIds.jump = config.get(Category.ENCHANT, "Leapfrog", 77).getInt();
            EnchantIds.hungry = config.get(Category.ENCHANT, "Never Hungry", 78).getInt();
            EnchantIds.oneRing = config.get(Category.ENCHANT, "The One Ring", 79).getInt();
            EnchantIds.poison = config.get(Category.ENCHANT, "Poison Ivy", 80).getInt();
            EnchantIds.punch = config.get(Category.ENCHANT, "Power Punch", 81).getInt();
            EnchantIds.repair = config.get(Category.ENCHANT, "Restoration", 82).getInt();
            EnchantIds.resurrection = config.get(Category.ENCHANT, "Reaper", 83).getInt();
            EnchantIds.speed = config.get(Category.ENCHANT, "Sonic the Hedgehog", 84).getInt();
            EnchantIds.spider = config.get(Category.ENCHANT, "Spiderman", 85).getInt();
            EnchantIds.stepUp = config.get(Category.ENCHANT, "Step Up", 86).getInt();
            EnchantIds.luck = config.get(Category.ENCHANT, "Luck of the Irish", 87).getInt();
            
            EnchantSetting.JUMPS_PER = config.get(Category.EXTRA, "Leapfrog > Jumps per Damage", 10).getInt();
            EnchantSetting.JUMP_FACTOR = config.get(Category.EXTRA, "Leapfrog > Jump Factor", 0.15).getDouble(0.15);
            EnchantSetting.JUMPS_PER = config.get(Category.EXTRA, "Sonic the Hedgehog > Ticks per Damage", 1200).getInt();
            EnchantSetting.JUMP_FACTOR = config.get(Category.EXTRA, "Sonic the Hedgehog > Speed Factor", 0.035).getDouble(0.035);
        } catch (Exception e) {
            LogHandler.log(Level.ERROR, "Mariculture had a serious issue loading it's block/item/enchant ids");
        	e.printStackTrace();
        } finally {
            config.save();
        }
    }
}
