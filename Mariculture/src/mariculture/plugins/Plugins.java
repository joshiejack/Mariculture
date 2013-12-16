package mariculture.plugins;

import java.util.ArrayList;
import java.util.logging.Level;

import mariculture.core.Mariculture.Stage;
import mariculture.core.handlers.LogHandler;
import cpw.mods.fml.common.Loader;

public class Plugins {
	//Only used for loading
	public static ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	
	public static Plugin rc = new PluginRailcraft("Railcraft");
	public static Plugin tic = new PluginTinkersConstruct("TConstruct");
	public static Plugin exbl = new PluginExtraBiomes("ExtrabiomesXL");
	public static Plugin forestry = new PluginForestry("Forestry");
	public static Plugin ic2 = new PluginIndustrialcraft("IC2");
	public static Plugin carbonization = new PluginCarbonization("carbonization");
	public static Plugin tc4 = new PluginThaumcraft("Thaumcraft");
	public static Plugin bop = new PluginBiomesOPlenty("BiomesOPlenty");
	
	public abstract static class Plugin {
		public String name;		
		public Plugin(String name) {
			this.name = name;
			plugins.add(this);
		}
		
		public boolean isLoaded() {
			return Loader.isModLoaded(this.name);
		}
		
		public abstract void preInit();
		public abstract void init();
		public abstract void postInit();
	}

	public void load(Stage stage) {
		for(Plugin plug: plugins) {
			if(plug.isLoaded()) {
	        	try {
	        		switch(stage) {
	        			case PRE: 
	        				plug.preInit();
	        			break;
	        			case INIT: 
	        				plug.init();
	        			break;
	        			case POST: 
	        				plug.postInit();
	        			break;
	        		}
				} catch (Exception e) {
					LogHandler.log(Level.INFO, "Mariculture - Something went wrong with " + plug.name + " Plugin at Init Phase");
				}
	        }
		}
	}
}
