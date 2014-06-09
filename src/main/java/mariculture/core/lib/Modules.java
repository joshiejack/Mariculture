package mariculture.core.lib;

import java.util.ArrayList;

import mariculture.core.handlers.LogHandler;

import org.apache.logging.log4j.Level;

public class Modules {
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
        }

        @Override
        public void init() {
            registerRecipes();
        }

        @Override
        public void postInit() {
            setLoaded(null);
        }
    }
}
