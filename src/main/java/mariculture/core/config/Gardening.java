package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.lib.config.Comment;
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
        Gardening.CORAL_SPREAD_CHANCE = getInt("Coral > Spread Chance", 75, Comment.CORAL_SPREAD);
        Gardening.MOSS_SPREAD_ENABLED = getBoolean("Kelp > Spread Moss Enabled", true);
        Gardening.KELP_SPREAD_CHANCE = getInt("Kelp > Spread Moss Chance", 65, Comment.KELP_SPREAD);
        Gardening.KELP_GROWTH_ENABLED = getBoolean("Kelp > Growth Enabled", true);
        Gardening.KELP_GROWTH_CHANCE = getInt("Kelp > Growth Chance", 200, Comment.KELP_GROWTH);
        Gardening.GEN_ENDER_PEARLS = getBoolean("Pearl Oyster > Generate Ender Pearls", true);
        Gardening.PEARL_GEN_CHANCE = getInt("Pearl Oyster > Pearl Generation Chance", 32, Comment.PEARL_CHANCE);
    }
}
