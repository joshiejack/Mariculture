package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
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
    public static double SPEEDBOAT_VERTICAL_MODIFIER;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Stuff");
        SPAWN_BOOKS = getBoolean("Spawn Books on First Action", true);
        if (!Loader.isModLoaded("Enchiridion")) {
            GeneralStuff.SPAWN_BOOKS = false;
        }
        
        HARDCORE_DIVING = getInt("Hardcore Diving Setting", 0, "This causes your air to drop quicker, the higher the faster it will drop, set to 0 to turn off");
        DEBUG_ON = getBoolean("Debug Mode Enabled", false);
        METAL_RATE = getInt("Molten Metal Nugget mB Value", 16, "This is how many mB a Nugget is worth, the default value is the same as Tinker's Construct");
        FLUDD_WATER_ON = getBoolean("Enable FLUDD Animations", true, "Whether a server will tell the client to display the fludd animations");
        ENABLE_ENDER_SPAWN = getBoolean("Enable Ender Dragon Spawning", true, "This is whether players can Spawn the Ender Dragon with the Dragon Spawn Egg");
        SHOW_CASTER_RECIPES = getBoolean("Enable Casting Recipes in NEI", false);
        SPEEDBOAT_VERTICAL_MODIFIER = getDouble("Speedboat Vertical Modifier", 2.0D, "This changes the speed modifier of a speedboat moving upwards in water when the speedboat is at least 90% covered in water.");
    }
}
