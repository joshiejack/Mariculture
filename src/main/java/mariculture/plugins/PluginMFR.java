package mariculture.plugins;

import mariculture.core.handlers.OreDicHandler;
import mariculture.plugins.Plugins.Plugin;
import powercrystals.minefactoryreloaded.MFRRegistry;

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
