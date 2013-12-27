package mariculture.plugins.compatibility;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;

public class Compat {
	public static void init() {
		try {
			CompatBait.init();
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Mariculture - Something went wrong when loading the Bait Compatibility Config");
		}
		
		CompatFluids.init();
	}
}
