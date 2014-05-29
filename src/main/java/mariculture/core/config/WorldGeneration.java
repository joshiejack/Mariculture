package mariculture.core.config;

import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.config.Category;
import net.minecraftforge.common.config.Configuration;

public class WorldGeneration {
	public static void init(Configuration config) {
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
        OreGeneration.RUTILE_SPAWN_CHANCE = config.get(Category.ORE, "Rutile > 1 Vein Per This Many Limestone", 500).getInt();
        OreGeneration.LIMESTONE_ON = config.get(Category.ORE, "Limestone > Generation", true).getBoolean(true);
        OreGeneration.NATURAL_GAS_ON = config.get(Category.ORE, "Natural Gas > Generation", true).getBoolean(true);
        OreGeneration.NATURAL_GAS_CHANCE = config.get(Category.ORE, "Natural Gas > 1 Pocket Per This Many Chunks", 20).getInt();
        OreGeneration.NATURAL_GAS_VEIN = config.get(Category.ORE, "Natural Gas > Maximum Vein Size", 48).getInt();
        OreGeneration.NATURAL_GAS_MIN = config.get(Category.ORE, "Natural Gas > Minimum Y Height", 16).getInt();
        OreGeneration.NATURAL_GAS_MAX = config.get(Category.ORE, "Natural Gas > Maximum Y Height", 26).getInt();
	}
}
