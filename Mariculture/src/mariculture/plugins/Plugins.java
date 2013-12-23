package mariculture.plugins;

import java.util.ArrayList;
import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.plugins.hungryfish.PluginHungryFish;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Loader;

public class Plugins {
	public static void init() {
		if (Loader.isModLoaded("Railcraft")) {
			try {
				PluginRailcraft.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Railcraft Plugin");
			}
		}

		if (Loader.isModLoaded("ExtrabiomesXL")) {
			try {
				PluginExtraBiomes.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with ExtrabiomesXL Plugin");
			}
		}

		if (Loader.isModLoaded("Forestry")) {
			try {
				PluginForestry.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Forestry Plugin");
			}
		}

		if (Loader.isModLoaded("IC2")) {
			try {
				PluginIndustrialcraft.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with IC2 Plugin");
			}
		}

		if (Loader.isModLoaded("carbonization")) {
			try {
				PluginCarbonization.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Carbonization Plugin");
			}
		}

		if (Loader.isModLoaded("Thaumcraft")) {
			try {
				PluginThaumcraft.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Thaumcraft Plugin");
			}
		}

		if (Loader.isModLoaded("BiomesOPlenty")) {
			try {
				PluginBiomesOPlenty.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with BiomesOPlenty Plugin");
			}
		}
		
		if(Loader.isModLoaded("TConstruct")) {
			try {
				MinecraftForge.EVENT_BUS.register(new PluginTinkersConstruct());
				PluginTinkersConstruct.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Tinkers Construct Plugin");
			}
		}
		
		if(Loader.isModLoaded("HungerOverhaul")) {
			try {
				PluginHungryFish.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong with Hungry Overhaul(Hungry Fish) Plugin");
			}
		}
	}
}
