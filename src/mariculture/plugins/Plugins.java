package mariculture.plugins;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.logging.Level;

import mariculture.Mariculture.Stage;
import mariculture.core.handlers.LogHandler;
import cpw.mods.fml.common.Loader;

public class Plugins {
	// Only used for loading
	public static ArrayList<Plugin> plugins = new ArrayList<Plugin>();

	public abstract static class Plugin {
		public String name;

		public Plugin(String name) {
			this.name = name;
			plugins.add(this);
		}
		
		public Plugin() {
			this.name = this.getClass().getSimpleName().toString().substring(6);
			plugins.add(this);
		}

		public void load(Stage stage) {
			try {
				switch(stage) {
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
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with " + name + " Plugin at " + stage.toString() + " Phase");
			}
		}

		public abstract void preInit();
		public abstract void init();
		public abstract void postInit();
	}
	
	public void add(String str) {
		if(Loader.isModLoaded(str)) {
			try {
				Class clazz = Class.forName("mariculture.plugins.Plugin" + str);
				Constructor constructor = clazz.getConstructor(new Class[] {String.class});
				constructor.newInstance(new Object[] {str});
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong when initializing " + str + " Plugin");
			}
		}
	}

	public void init() {
		add("Railcraft");
		add("TConstruct");
		add("ExtrabiomesXL");
		add("Forestry");
		add("IC2");
		add("Thaumcraft");
		add("BiomesOPlenty");
		add("HungerOverhaul");
		add("ThermalExpansion");
	}

	public void load(Stage stage) {
		for (Plugin plug : plugins) {
			plug.load(stage);
		}
	}
}
