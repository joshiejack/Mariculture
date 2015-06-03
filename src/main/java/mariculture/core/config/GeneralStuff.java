package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getDouble;
import static mariculture.core.helpers.ConfigHelper.getFloat;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.events.MaricultureEvents;
import mariculture.lib.util.Library;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;

public class GeneralStuff {
    public static boolean SPAWN_BOOKS;
    public static int HARDCORE_DIVING;
    public static int METAL_RATE;
    public static boolean ENABLE_ENDER_SPAWN;
    public static boolean SHOW_CASTER_RECIPES;
    public static boolean GAS_CAN_CATCH_ALIGHT;
    public static float FISHING_GEAR_CHANCE;
    public static float FISHING_HAT_DROP_CHANCE;
    public static float FISHING_ROD_DROP_CHANCE;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Stuff");
        Library.DEBUG_ON = getBoolean("Debug Mode Enabled", false);

        SPAWN_BOOKS = getBoolean("Spawn Books on First Action", true);
        if (!Loader.isModLoaded("Enchiridion")) {
            GeneralStuff.SPAWN_BOOKS = false;
        }

        HARDCORE_DIVING = getInt("Hardcore Diving Setting", 0, "This causes your air to drop quicker, the higher the faster it will drop, set to 0 to turn off");
        METAL_RATE = getInt("Molten Metal Nugget mB Value", 16, "This is how many mB a Nugget is worth, the default value is the same as Tinker's Construct");
        ENABLE_ENDER_SPAWN = getBoolean("Enable Ender Dragon Spawning", true, "This is whether players can Spawn the Ender Dragon with the Dragon Spawn Egg");
        SHOW_CASTER_RECIPES = getBoolean("Enable Casting Recipes in NEI", true);
        GAS_CAN_CATCH_ALIGHT = getBoolean("Natural Gas Can Catch on Fire", true);
        FISHING_GEAR_CHANCE = getFloat("Zombie > Spawn with Fishing Gear Chance > 0-1", 0.0125F);
        FISHING_HAT_DROP_CHANCE = getFloat("Zombie > Drop Fishing Hat Chance > 0-1", 0.1F);
        FISHING_ROD_DROP_CHANCE = getFloat("Zombie > Drop Fishing Rod Chance > 0-1", 0.05F);
        MaricultureEvents.onConfigure("GeneralStuff", config);
    }
}
