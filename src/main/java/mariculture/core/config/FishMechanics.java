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
    public static boolean FIX_FISH;
    public static double SPEED_MULTIPLIER;
    public static boolean SQUID_DROP_CALAMARI;

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
        FishMechanics.FIX_FISH = getBoolean("Fix Fish", true, "Will automatically fix any broken fish in a fish feeder. Disable once your fish are fixed. As this can make the fish feeder laggier.");
        FishMechanics.SPEED_MULTIPLIER = getInt("Give me fish NAOW", 50, "Increasing this gives you faster fishing overall, Set to 0 for normal speed");
        FishMechanics.SQUID_DROP_CALAMARI = getBoolean("Squid Drop Calamari", true);
    }
}
