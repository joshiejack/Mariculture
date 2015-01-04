package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getRand;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import net.minecraftforge.common.config.Configuration;

public class Gardening {
    public static boolean CORAL_SPREAD_ENABLED;
    public static boolean MOSS_SPREAD_ENABLED;
    public static boolean KELP_GROWTH_ENABLED;
    public static int KELP_SPREAD_CHANCE;
    public static int CORAL_SPREAD_CHANCE;
    public static int KELP_GROWTH_CHANCE;
    public static boolean GEN_ENDER_PEARLS;
    public static int PEARL_GEN_CHANCE;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Settings");
        Gardening.CORAL_SPREAD_ENABLED = getBoolean("Coral > Spread Enabled", true);
        Gardening.CORAL_SPREAD_CHANCE = getRand("Coral > Spread Chance", 12, "This is how fast coral will spread, lower = faster");
        Gardening.MOSS_SPREAD_ENABLED = getBoolean("Kelp > Spread Moss Enabled", true);
        Gardening.KELP_SPREAD_CHANCE = getRand("Kelp > Spread Moss Chance", 65, "This is how fast Kelp will spread moss to neaby blocks, lower = faster");
        Gardening.KELP_GROWTH_ENABLED = getBoolean("Kelp > Growth Enabled", true);
        Gardening.KELP_GROWTH_CHANCE = getRand("Kelp > Growth Chance", 200, "This is the speed that kelp will grow, lower = faster");
        Gardening.GEN_ENDER_PEARLS = getBoolean("Pearl Oyster > Generate Ender Pearls", true);
        Gardening.PEARL_GEN_CHANCE = getRand("Pearl Oyster > Pearl Generation Chance", 32, "This is the chance that oysters will generate a pearl. It's a X in this many chance, so lower = better chance");
    }
}
