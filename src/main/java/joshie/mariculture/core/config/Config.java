package joshie.mariculture.core.config;

import java.io.File;
import java.lang.reflect.Method;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.handlers.LogHandler;
import joshie.mariculture.core.helpers.ConfigHelper;
import joshie.mariculture.core.lib.MCModInfo;
import joshie.mariculture.core.lib.Modules;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class Config {
    private static final String dir = Mariculture.root + "/mariculture/";

    private static boolean setup(String name) {
        Configuration config = new Configuration(new File(dir, name.replaceAll("(.)([A-Z])", "$1-$2").toLowerCase() + ".cfg"));
        try {
            try {
                config.load();
                Class clazz = Class.forName(MCModInfo.JAVAPATH + "core.config." + name);
                Method method = clazz.getMethod("init", Configuration.class);
                method.invoke(null, config);
            } catch (Exception e) {
                LogHandler.log(Level.ERROR, "There was a problem loading the " + name + " config settings");
                e.printStackTrace();
            } finally {
                config.save();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setup() {
        setup("Modules");
        setup("AutoDictionary");
        setup("Enchantments");
        setup("FishMechanics");
        setup("Gardening");
        setup("GeneralStuff");
        setup("Machines");
        setup("Vanilla");
        setup("WorldGeneration");
        ConfigHelper.setConfig(null);

        // Setup the tab icons
        setupTabs();
    }

    private static void setupTabs() {
        if (!Modules.isActive(Modules.magic)) {
            MaricultureTab.tabMagic = null;
        }
    }
}
