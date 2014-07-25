package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getDouble;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraftforge.common.config.Configuration;

public class FishMechanics {
    public static boolean EASY_SCANNER;
    public static boolean DISABLE_FISH;
    public static double BREEDING_MULTIPLIER;
    public static int DEMON_FISH_LIMIT;
    public static int WEAK_FISH_LIMIT;
    public static boolean IGNORE_BIOMES;
    public static double ALIVE_MODIFIER;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Settings");
        FishMechanics.DISABLE_FISH = getBoolean("Disable Mariculture Live Fish in NEI", false);
        FishMechanics.BREEDING_MULTIPLIER = getDouble("Breeding Multiplier", 1.0D);
        FishSpecies.DISABLE_BIOME_CATCHING = getBoolean("Ignore biomes when catching fish", false);
        FishSpecies.DISABLE_DIMENSION_CATCHING = getBoolean("Ignore dimensions when catching fish", false);
        FishMechanics.WEAK_FISH_LIMIT = getInt("Bound Fishing Rod - Fish Limit Per Use (Weak)", 8);
        FishMechanics.DEMON_FISH_LIMIT = getInt("Bound Fishing Rod - Fish Limit Per Use (Demon)", 64);
        FishMechanics.ALIVE_MODIFIER = getDouble("Fish Caught Alive Modifier", 1.25D);
        FishMechanics.EASY_SCANNER = getBoolean("Easier Fish Scanner Recipe", true);
    }
}
