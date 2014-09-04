package joshie.mariculture.core.lib;

import java.util.ArrayList;

import joshie.mariculture.api.events.MaricultureEvents;
import joshie.mariculture.core.handlers.LogHandler;
import joshie.mariculture.plugins.Plugins.Plugin.Stage;

import org.apache.logging.log4j.Level;

public class Modules {
    //Whether the high tech parts of the mod are activated
    public static boolean ENABLE_HIGH_TECH;
    public static final ArrayList<Module> modules = new ArrayList();
    //Base Modules
    public static Module core;
    public static Module compat;
    public static Module plugins;

    //Other Modules	
    public static Module aesthetics;
    public static Module diving;
    public static Module factory;
    public static Module fishery;
    public static Module magic;
    public static Module sealife;
    public static Module transport;
    public static Module worldplus;

    //Returns whether this module is activated or not
    public static boolean isActive(Module module) {
        return module != null;
    }

    public void setup(Class<? extends Module> clazz, boolean isActive) {
        if (isActive) {
            try {
                getClass().getField(clazz.getSimpleName().toLowerCase()).set(null, clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract static class Module {
        public Module() {
            modules.add(this);
        }

        //Just a display helper
        public void setLoaded(String str) {
            LogHandler.log(Level.INFO, this.getClass().getSimpleName() + " Module Finished Loading");
        }

        public abstract void preInit();

        public abstract void init();

        public abstract void postInit();
    }

    public abstract static class RegistrationModule extends Module {
        public abstract void registerHandlers();

        public abstract void registerBlocks();

        public abstract void registerItems();

        public abstract void registerOther();

        public abstract void registerRecipes();

        public void registerFluids() {
            return;
        }

        @Override
        public void preInit() {
            registerHandlers();
            registerItems();
            registerFluids();
            registerBlocks();
            registerOther();
            MaricultureEvents.onRegistration(this, Stage.PRE);
        }

        @Override
        public void init() {
            registerRecipes();
            MaricultureEvents.onRegistration(this, Stage.INIT);
        }

        @Override
        public void postInit() {
            MaricultureEvents.onRegistration(this, Stage.POST);
            setLoaded(null);
        }
    }
}
