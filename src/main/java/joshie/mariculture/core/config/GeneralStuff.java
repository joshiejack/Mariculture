package joshie.mariculture.core.config;

import static joshie.mariculture.core.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.core.helpers.ConfigHelper.getInt;
import static joshie.mariculture.core.helpers.ConfigHelper.setCategory;
import static joshie.mariculture.core.helpers.ConfigHelper.setConfig;
import joshie.lib.util.Library;
import joshie.mariculture.api.events.MaricultureEvents;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;

public class GeneralStuff {
    public static boolean SPAWN_BOOKS;
    public static int HARDCORE_DIVING;
    public static int METAL_RATE;
    public static boolean ENABLE_ENDER_SPAWN;
    public static boolean SHOW_CASTER_RECIPES;

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
        MaricultureEvents.onConfigure("GeneralStuff", config);
    }
}
