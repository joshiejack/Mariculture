package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.lib.config.Comment;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;

public class GeneralStuff {
    public static boolean SPAWN_BOOKS;
    public static int HARDCORE_DIVING;
    public static boolean DEBUG_ON;
    public static boolean FLUDD_WATER_ON;
    public static int METAL_RATE;
    public static boolean ENABLE_ENDER_SPAWN;
    public static boolean SHOW_CASTER_RECIPES;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Stuff");
        SPAWN_BOOKS = getBoolean("Spawn Books on First Action", true);
        if (!Loader.isModLoaded("Enchiridion")) {
            GeneralStuff.SPAWN_BOOKS = false;
        }
        HARDCORE_DIVING = getInt("Hardcore Diving Setting", 0, Comment.HARDCORE);
        DEBUG_ON = getBoolean("Debug Mode Enabled", false);
        METAL_RATE = getInt("Molten Metal Nugget mB Value", 16, Comment.METAL);
        FLUDD_WATER_ON = getBoolean("Enable FLUDD Animations", true, Comment.FLUDD);
        ENABLE_ENDER_SPAWN = getBoolean("Enable Ender Dragon Spawning", true, Comment.ENDERDRAGON);
        SHOW_CASTER_RECIPES = getBoolean("Enable Casting Recipes in NEI", false);
    }
}
