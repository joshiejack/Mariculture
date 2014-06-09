package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.lib.config.Comment;
import net.minecraftforge.common.config.Configuration;

public class WorldGeneration {
    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Ore Generation", "This section let's you adjust where your ores will spawn and how much. Please note when it comes to 'chance', that LOWER = more common, as it's a x in this many chance.");
        OreGen.BAUXITE_ON = getBoolean("Bauxite > Generation", true);
        OreGen.BAUXITE_TOTAL = getInt("Bauxite > Number of Veins", 16);
        OreGen.BAUXITE_VEIN = getInt("Bauxite > Maximum Vein Size", 8);
        OreGen.BAUXITE_MIN = getInt("Bauxite > Minimum Y Height", 60);
        OreGen.BAUXITE_MAX = getInt("Bauxite > Maximum Y Height", 256);
        OreGen.COPPER_ON = getBoolean("Copper > Generation", false);
        OreGen.COPPER_TOTAL = getInt("Copper > Number of Veins", 12);
        OreGen.COPPER_VEIN = getInt("Copper > Maximum Vein Size", 5);
        OreGen.COPPER_MIN = getInt("Copper > Minimum Y Height", 1);
        OreGen.COPPER_MAX = getInt("Copper > Maximum Y Height", 64);
        OreGen.RUTILE_SPAWN_CHANCE = getInt("Rutile > 1 Vein Per This Many Limestone", 500);
        OreGen.LIMESTONE_ON = getBoolean("Limestone > Generation", true);
        OreGen.NATURAL_GAS_ON = getBoolean("Natural Gas > Generation", true);
        OreGen.NATURAL_GAS_CHANCE = getInt("Natural Gas > 1 Pocket Per This Many Chunks", 20);
        OreGen.NATURAL_GAS_VEIN = getInt("Natural Gas > Maximum Vein Size", 48);
        OreGen.NATURAL_GAS_MIN = getInt("Natural Gas > Minimum Y Height", 16);
        OreGen.NATURAL_GAS_MAX = getInt("Natural Gas > Maximum Y Height", 26);

        setCategory("Aquatic Generation");
        WorldGen.WATER_CAVES = getBoolean("Water Filled Caves in Oceans", false);
        WorldGen.WATER_RAVINES = getBoolean("Water Filled Ravines in Oceans", true);
        WorldGen.RAVINE_CHANCE = getInt("Water Ravine Chance (Lower = More Common)", 25);
        WorldGen.NO_MINESHAFTS = getBoolean("Remove Mineshafts in Oceans", true);

        WorldGen.OYSTER_ENABLED = getBoolean("Pearl Oyster > Generation", true);
        WorldGen.OYSTER_PER_CHUNK = getInt("Pearl Oyster > Number Chances to Gen Per Chunk", 3);
        WorldGen.OYSTER_CHANCE = getInt("Pearl Oyster > 1 Oyster per This Many Blocks Per Chunk", 12);
        WorldGen.OYSTER_PEARL_CHANCE = getInt("Pearl Oyster > 1 Natural Pearl Per this Many Oysters", 3);
        WorldGen.ANCIENT_SAND_ENABLED = getBoolean("Ancient Sand > Enabled", true);
        WorldGen.ANCIENT_SAND_CHANCE = getInt("Ancient Sand > 1 in this many chance", 2);
        WorldGen.ANCIENT_SAND_SIZE = getInt("Ancient Sand > Size", 4);

        WorldGen.CORAL_REEF_ENABLED = getBoolean("Coral Reef > Generation", true);
        WorldGen.CORAL_EXTRA = getBoolean("Coral Reef > Enable Harsher Generation", true);
        WorldGen.CORAL_REEF_START_CHANCE = getInt("Coral Reef > Start Chance", 256, Comment.GEN_START);
        WorldGen.CORAL_REEF_END_CHANCE = getInt("Coral Reef > End Chance", 40, Comment.GEN_END);

        WorldGen.KELP_FOREST_ENABLED = getBoolean("Kelp Forest > Enabled", true);
        WorldGen.KELP_FOREST_START_CHANCE = getInt("Kelp Forest > Start Chance", 640, Comment.GEN_START);
        WorldGen.KELP_FOREST_END_CHANCE = getInt("Kelp Forest > End Chance", 96, Comment.GEN_END);
        WorldGen.KELP_FOREST_CHEST_MAX_ITEMS = getInt("Kelp Forest > Maximum Items", 10);
        WorldGen.KELP_FOREST_CHEST_MIN_ITEMS = getInt("Kelp Forest > Minimum Items", 2);
        WorldGen.KELP_FOREST_CHEST_CHANCE = getInt("Kelp Forest > Chest Chance", 640, Comment.KELP_FOREST_CHEST_CHANCE);

        setCategory("Retro-Generation", "Retro-Generation allows you to generate ores/other features in your existing worlds, without you having to go off and explore new areas. If you set all to true, it will override the individual settings for retro-gen, Make sure you disable retro-gen after you have regenned your world");
        RetroGen.ENABLED = getBoolean("Enable Retro-Gen", false);
        RetroGen.KEY = getInt("Key", 555, Comment.RETRO_KEY);
        RetroGen.ALL = getBoolean("All", true);
        RetroGen.BAUXITE = getBoolean("Bauxite", false);
        RetroGen.COPPER = getBoolean("Copper", false);
        RetroGen.CORALREEF = getBoolean("Coral Reef", false);
        RetroGen.GAS = getBoolean("Natural Gas", false);
        RetroGen.KELP = getBoolean("Kelp Forest", false);
        RetroGen.LIMESTONE = getBoolean("Limestone", false);
        RetroGen.OYSTER = getBoolean("Oysters", false);
        RetroGen.RUTILE = getBoolean("Rutile", false);
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
        public static boolean LIMESTONE_ON;
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
        public static boolean WATER_RAVINES;
        public static boolean NO_MINESHAFTS;
        public static boolean WATER_CAVES;
        public static int RAVINE_CHANCE;
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
    }

    public static class RetroGen {
        public static boolean ENABLED;
        public static int KEY;
        public static boolean ALL;
        public static boolean BAUXITE;
        public static boolean LIMESTONE;
        public static boolean RUTILE;
        public static boolean COPPER;
        public static boolean OYSTER;
        public static boolean GAS;
        public static boolean CORALREEF;
        public static boolean ANCIENT;
        public static boolean KELP;
    }
}
