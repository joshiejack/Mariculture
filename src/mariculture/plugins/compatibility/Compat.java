package mariculture.plugins.compatibility;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;

public class Compat {
	public static void preInit() {
		CompatBooks.preInit();
	}
	
	public static void init() {
		if(Modules.fishery.isActive()) {
			try {
				CompatBait.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong when loading the Bait Compatibility Config");
			}
		}
		
		CompatFluids.init();
		CompatBooks.init();
	}
}
