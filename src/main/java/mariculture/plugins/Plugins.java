package mariculture.plugins;

import java.util.ArrayList;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.MCModInfo;
import mariculture.core.lib.Modules.Module;
import mariculture.plugins.Plugins.Plugin.Stage;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;

public class Plugins extends Module {
    public static ArrayList<Plugin> plugins = new ArrayList<Plugin>();

    public abstract static class Plugin extends PluginHelper {
        public static enum Stage {
            PRE, INIT, POST;
        }

        public Plugin(String name) {
            super(name);
            plugins.add(this);
        }

        public void load(Stage stage) {
            try {
                switch (stage) {
                    case PRE:
                        preInit();
                        break;
                    case INIT:
                        init();
                        break;
                    case POST:
                        postInit();
                        break;
                }
            } catch (Exception e) {
                LogHandler.log(Level.WARN, "Something went wrong with " + name + " Plugin at " + stage.toString() + " Phase");
                e.printStackTrace();
            }
        }

        public abstract void preInit();

        public abstract void init();

        public abstract void postInit();
    }

    public Plugins() {
        add("HEE", "HardcoreEnderExpansion");
        add("Railcraft");
        add("TConstruct");
        add("Forestry");
        add("Thaumcraft");
        add("BiomesOPlenty");
        add("AppleCore");
        add("Highlands");
        add("BloodMagic", "AWWayofTime");
        add("LiquidXP");
        add("MineFactoryReloaded", "MFR");
        add("OpenBlocks");
        add("Waila");
        add("ThermalFoundation");
        add("RedstoneArsenal");
        add("Aquaculture");
        add("HarvestCraft", "harvestcraft");
        //Always add theByeByeSmelting Plugin
        add("ModdedTweaks", "Enchiridion");
        add("Botania");
        add("MineTweaker3");
        add("EE3");
        add("Enchiridion");
    }

    public void add(String str) {
        add(str, str);
    }

    public void add(String clazz, String mod) {
        if (Loader.isModLoaded(mod)) {
            try {
                Class.forName(MCModInfo.JAVAPATH + "plugins.Plugin" + clazz).getConstructor(String.class).newInstance(mod);
            } catch (Exception e) {
                LogHandler.log(Level.WARN, "Something went wrong when initializing " + clazz + " Plugin");
            }
        }
    }

    @Override
    public void setLoaded(String str) {
        LogHandler.log(Level.INFO, str + " Plugin Finished Loading");
    }

    @Override
    public void preInit() {
        for (Plugin plug : plugins) {
            plug.load(Stage.PRE);
        }
    }

    @Override
    public void init() {
        for (Plugin plug : plugins) {
            plug.load(Stage.INIT);
        }
    }

    @Override
    public void postInit() {
        for (Plugin plug : plugins) {
            plug.load(Stage.POST);

            //Display that the plugin finished loading
            setLoaded(plug.getClass().getSimpleName().substring(6));
        }
    }
}
