package mariculture.plugins;

import java.util.ArrayList;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules.Module;
import mariculture.plugins.Plugins.Plugin.Stage;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;

public class Plugins extends Module {
	// Only used for loading
	public static ArrayList<Plugin> plugins = new ArrayList<Plugin>();

	public abstract static class Plugin {
		public static enum Stage {
			PRE, INIT, POST;
		}
		
		public String name;
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
				LogHandler.log(Level.WARN, "Something went wrong with " + name + " Plugin at " + stage.toString() + " Phase");
			}
		}

		public abstract void preInit();
		public abstract void init();
		public abstract void postInit();
		public void registerWildcards() {
			return;
		}
	}
	
	public void add(String str) {
		if(Loader.isModLoaded(str)) {
			try {
				Class.forName("mariculture.plugins.Plugin" + str).newInstance();
			} catch (Exception e) {
				LogHandler.log(Level.WARN, "Something went wrong when initializing " + str + " Plugin");
			}
		}
	}
	
	@Override
	public void setLoaded(String str) {
		LogHandler.log(Level.INFO, str + " Plugin Finished Loading");
	}
	
	@Override
	public void preInit() {
		add("Railcraft");
		add("TConstruct");
		add("ExtrabiomesXL");
		add("Forestry");
		add("IC2");
		add("Thaumcraft");
		add("BiomesOPlenty");
		add("HungerOverhaul");
		add("ThermalExpansion");
		add("Enchiridion");
		
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
			String name = plug.name;
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			setLoaded(name);
		}
	}
}
