package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getDouble;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.getIntList;
import static mariculture.core.helpers.ConfigHelper.getRand;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import net.minecraftforge.common.config.Configuration;

public class WorldGeneration {
    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Ore Generation", "This section let's you adjust where your ores will spawn and how much. Please note when it comes to 'chance', that LOWER = more common, as it's a x in this many chance.");
        OreGen.BAUXITE_ON = getBoolean("Bauxite > Generation", true);
        OreGen.BAUXITE_TOTAL = getInt("Bauxite > Number of Veins", 16);
        OreGen.BAUXITE_VEIN = getInt("Bauxite > Maximum Vein Size", 8);
        OreGen.BAUXITE_MIN = getInt("Bauxite > Minimum Y Height", 64);
        OreGen.BAUXITE_MAX = getInt("Bauxite > Maximum Y Height", 256);
        OreGen.COPPER_ON = getBoolean("Copper > Generation", true);
        OreGen.COPPER_TOTAL = getInt("Copper > Number of Veins", 12);
        OreGen.COPPER_VEIN = getInt("Copper > Maximum Vein Size", 5);
        OreGen.COPPER_MIN = getInt("Copper > Minimum Y Height", 1);
        OreGen.COPPER_MAX = getInt("Copper > Maximum Y Height", 64);
        OreGen.RUTILE_ON = getBoolean("Rutile > Generation", true);
        OreGen.RUTILE_SPAWN_CHANCE = getInt("Rutile > 1 Vein Per This Many Limestone", 500);
        OreGen.NATURAL_GAS_ON = getBoolean("Natural Gas > Generation", true);
        OreGen.NATURAL_GAS_CHANCE = getInt("Natural Gas > 1 Pocket Per This Many Chunks", 20);
        OreGen.NATURAL_GAS_VEIN = getInt("Natural Gas > Maximum Vein Size", 48);
        OreGen.NATURAL_GAS_MIN = getInt("Natural Gas > Minimum Y Height", 16);
        OreGen.NATURAL_GAS_MAX = getInt("Natural Gas > Maximum Y Height", 26);

        setCategory("Aquatic Generation");
        WorldGen.OCEAN_ROOT = getDouble("Ocean > Base Height", -1.4D);
        WorldGen.OCEAN_VARIATION = getDouble("Ocean > Height Variation", 0.35D);
        WorldGen.OCEAN_DEEP_ROOT = getDouble("Deep Ocean > Base Height", -1.995D);
        WorldGen.OCEAN_DEEP_VARIATION = getDouble("Deep Ocean >  Height Variation", 0.05D);
        WorldGen.OCEAN_LIMESTONE = getDouble("Ocean > Limestone Height", 7.5D);
        WorldGen.OCEAN_DEEP_LIMESTONE = getDouble("Deep Ocean > Limestone Height", 3.5D);
        WorldGen.RIVER_LIMESTONE = getDouble("River > Limestone Height", 2.5D);
        WorldGen.BEACH_LIMESTONE = getDouble("Beach > Limestone Height", 25D);
        WorldGen.VARYING_LIMESTONE = getBoolean("Limestone > Varying Height", true);
        WorldGen.LIMESTONE_FLOOR = getBoolean("Limestone > Sea Bed", true, "If this is false, you will get a sandstone seabed instead.");
        WorldGen.ENABLE_REPLACEMENTS = getBoolean("Biome Top Block Replacements > Enable", true, "Disabling this will stop the generation of limestone, rutile and sand on ocean floors");
        WorldGen.OCEAN_BLACKLIST = getIntList("Ocean Generation > Blacklisted Dimension IDs", new int[] { -1, 1 });

        WorldGen.OYSTER_ENABLED = getBoolean("Pearl Oyster > Generation", true);
        WorldGen.OYSTER_PER_CHUNK = getInt("Pearl Oyster > Number Chances to Gen Per Chunk", 3);
        WorldGen.OYSTER_CHANCE = getInt("Pearl Oyster > 1 Oyster per This Many Blocks Per Chunk", 12);
        WorldGen.OYSTER_PEARL_CHANCE = getInt("Pearl Oyster > 1 Natural Pearl Per this Many Oysters", 3);
        WorldGen.ANCIENT_SAND_ENABLED = getBoolean("Ancient Sand > Enabled", true);
        WorldGen.ANCIENT_SAND_CHANCE = getInt("Ancient Sand > 1 in this many chance", 2);
        WorldGen.ANCIENT_SAND_SIZE = getInt("Ancient Sand > Size", 4);

        WorldGen.CORAL_REEF_ENABLED = getBoolean("Coral Reef > Generation", true);
        WorldGen.CORAL_EXTRA = getBoolean("Coral Reef > Enable Harsher Generation", true);
        WorldGen.CORAL_REEF_START_CHANCE = getRand("Coral Reef > Start Chance", 256, "The higher the number, the rarer the generation will be, this number defines the chance of this gen spawning");
        WorldGen.CORAL_REEF_END_CHANCE = getRand("Coral Reef > End Chance", 40, "The higher the number, the larger the generation will be, this number defines the chance of this gen no longer spawning");

        WorldGen.KELP_FOREST_ENABLED = getBoolean("Kelp Forest > Enabled", true);
        WorldGen.KELP_FOREST_START_CHANCE = getRand("Kelp Forest > Start Chance", 640, "The higher the number, the rarer the generation will be, this number defines the chance of this gen spawning");
        WorldGen.KELP_FOREST_END_CHANCE = getRand("Kelp Forest > End Chance", 96, "The higher the number, the larger the generation will be, this number defines the chance of this gen no longer spawning");
        WorldGen.KELP_FOREST_CHEST_MAX_ITEMS = getInt("Kelp Forest > Maximum Items", 10);
        WorldGen.KELP_FOREST_CHEST_MIN_ITEMS = getInt("Kelp Forest > Minimum Items", 2);
        WorldGen.KELP_FOREST_CHEST_CHANCE = getInt("Kelp Forest > Chest Chance", 640, "The higher the number the less common a chest will be, this roughly works out to this 1 chest per this many blocks of a forest.");
        WorldGen.PENGUIN_SPAWN_ENABLED = getBoolean("Penguin Spawning > Enabled", true);
        
        setCategory("Retro-Generation", "Retro-Generation allows you to generate ores/other features in your existing worlds, without you having to go off and explore new areas. If you set all to true, it will override the individual settings for retro-gen, Make sure you disable retro-gen after you have regenned your world");
        RetroGen.ENABLED = getBoolean("Enable Retro-Gen", false);
        RetroGen.KEY = getInt("Key", 555, "This key is what stops, retro gen from generating in chunks over and over, if you want to retro gen again, change the key to any other integer");
        RetroGen.ALL = getBoolean("All", true);
        RetroGen.BAUXITE = getBoolean("Bauxite", false);
        RetroGen.COPPER = getBoolean("Copper", false);
        RetroGen.CORALREEF = getBoolean("Coral Reef", false);
        RetroGen.GAS = getBoolean("Natural Gas", false);
        RetroGen.KELP = getBoolean("Kelp Forest", false);
        RetroGen.OYSTER = getBoolean("Oysters", false);
        RetroGen.ANCIENT = getBoolean("Ancient Sand", false);
    }

    public static class OreGen {
        public static boolean COPPER_ON;
        public static int COPPER_TOTAL;
        public static int COPPER_VEIN;
        public static int COPPER_MIN;
        public static int COPPER_MAX;
        public static boolean RUTILE_ON;
        public static int RUTILE_SPAWN_CHANCE;
        public static boolean BAUXITE_ON;
        public static int BAUXITE_TOTAL;
        public static int BAUXITE_VEIN;
        public static int BAUXITE_MIN;
        public static int BAUXITE_MAX;
        public static boolean NATURAL_GAS_ON;
        public static int NATURAL_GAS_VEIN;
        public static int NATURAL_GAS_CHANCE;
        public static int NATURAL_GAS_MIN;
        public static int NATURAL_GAS_MAX;
    }

    public static class WorldGen {
        public static boolean ENABLE_REPLACEMENTS;
        public static boolean LIMESTONE_FLOOR;
        public static int[] OCEAN_BLACKLIST;
        public static boolean VARYING_LIMESTONE;
        public static double OCEAN_ROOT;
        public static double OCEAN_VARIATION;
        public static double OCEAN_DEEP_ROOT;
        public static double OCEAN_DEEP_VARIATION;
        public static double OCEAN_LIMESTONE;
        public static double OCEAN_DEEP_LIMESTONE;
        public static double RIVER_LIMESTONE;
        public static double BEACH_LIMESTONE;

        public static boolean OYSTER_ENABLED;
        public static int OYSTER_PER_CHUNK;
        public static int OYSTER_CHANCE;
        public static int OYSTER_PEARL_CHANCE;
        public static boolean ANCIENT_SAND_ENABLED;
        public static int ANCIENT_SAND_SIZE;
        public static int ANCIENT_SAND_CHANCE;

        // Coral vars
        public static boolean CORAL_REEF_ENABLED;
        public static boolean CORAL_EXTRA;
        public static int CORAL_REEF_START_CHANCE;
        public static int CORAL_REEF_END_CHANCE;
        // Kelp vars
        public static boolean KELP_FOREST_ENABLED;
        public static int KELP_FOREST_START_CHANCE;
        public static int KELP_FOREST_END_CHANCE;
        public static int KELP_FOREST_CHEST_CHANCE;
        public static int KELP_FOREST_CHEST_MIN_ITEMS;
        public static int KELP_FOREST_CHEST_MAX_ITEMS;
        public static boolean PENGUIN_SPAWN_ENABLED;
    }

    public static class RetroGen {
        public static boolean ENABLED;
        public static int KEY;
        public static boolean ALL;
        public static boolean BAUXITE;
        public static boolean COPPER;
        public static boolean OYSTER;
        public static boolean GAS;
        public static boolean CORALREEF;
        public static boolean ANCIENT;
        public static boolean KELP;
    }
}
