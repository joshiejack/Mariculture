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
    public static boolean ADD_ALCHEMY_RECIPES;
    
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
        FishMechanics.ADD_ALCHEMY_RECIPES = getBoolean("Alchemy Recipes", true);
        
        setCategory("Less Fussy Fish");
        FussyFish.IGNORE_ALL_REQUIREMENTS = getBoolean("Ignore All Requirements", false, "Setting this to true, will mean fish will work under any conditions");
        FussyFish.IGNORE_DAY_REQUIREMENTS = getBoolean("Ignore Day Requirements", false, "Fish will work whether it is day or night, no matter their species");
        FussyFish.IGNORE_DIMENSION_REQUIREMENTS = getBoolean("Ignore Dimension Requirements", false, "Fish will work in any dimension, even if they don't call it home");
        FussyFish.IGNORE_BLOCK_TYPE = getBoolean("Ignore Water Block Type", false, "Fish will count ANY block that is water, lava or implements IFluidBlock");
        FussyFish.SALINITY_TOLERANCE_BOOSTER = getInt("Salinity Tolerance Booster", 0, "Increases the tolerance of fish to salinity");//
        FussyFish.TEMP_TOLERANCE_BOOSTER = getInt("Temperature Tolerance Booster", 3, "This adds more tolerance on to fish when it comes to temperature by default. Can be negative to reduce range, but that would break fish... Make a really high number for effectively no requirement");//
        FussyFish.RANGE_BOOSTER = getInt("Checking Area Range Booster", 0, "Increases the range of fish territory to count water");
        FussyFish.WATER_COUNT_BOOSTER = getInt("Water Count Booster", 0, "Adds this number to the total number of water blocks a fish needs");
        FussyFish.WORK_ETHIC_BOOSTER = getInt("Work Ethic Booster", 0, "Add this to the fish work ethic, recommended to leave this alone, or at worst change it to 1.");
        FussyFish.LIFESPAN_BOOSTER = getInt("Lifespan Booster", 0, "Set this to a negative number to make fish live shorter lives, or larger to make them live longer");
        FussyFish.FOOD_USAGE_BOOSTER = getInt("Food Usage Booster", 0, "Increase this to make fish use more food, or a negative number to decrease the amount");
        FussyFish.FERTILITY_BOOSTER = getInt("Fertility Booster", 0, "A higher number means fish will on average lay more eggs.");
    }
    
    public static class FussyFish {
        public static int FOOD_USAGE_BOOSTER;
        public static boolean IGNORE_ALL_REQUIREMENTS;
        public static boolean IGNORE_DIMENSION_REQUIREMENTS;
        public static boolean IGNORE_DAY_REQUIREMENTS;
        
        public static boolean IGNORE_BLOCK_TYPE;

        public static int TEMP_TOLERANCE_BOOSTER;
        public static int WATER_COUNT_BOOSTER;
        public static int RANGE_BOOSTER;
        public static int SALINITY_TOLERANCE_BOOSTER;
        public static int LIFESPAN_BOOSTER;
        public static int WORK_ETHIC_BOOSTER;
        public static int FERTILITY_BOOSTER;
    }
}
