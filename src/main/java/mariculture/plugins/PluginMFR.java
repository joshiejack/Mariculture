package mariculture.plugins;

import powercrystals.minefactoryreloaded.MFRRegistry;
import mariculture.core.handlers.OreDicHandler;
import mariculture.plugins.Plugins.Plugin;

public class PluginMFR extends Plugin {
	@Override
	public void preInit() {
		OreDicHandler.has_unifier = true;
	}

	@Override
	public void init() {
		return;
	}

	@Override
	public void postInit() {
		return;
	}

	public static void blacklist(String name) {
		MFRRegistry.registerUnifierBlacklist(name);
	}
}
