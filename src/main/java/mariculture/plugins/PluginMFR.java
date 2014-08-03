package mariculture.plugins;

import mariculture.core.handlers.OreDicHandler;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.minefactoryreloaded.MFRRegistry;

public class PluginMFR extends Plugin {
    public PluginMFR(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        OreDicHandler.has_unifier = true;
        Fluids.add("milk", FluidRegistry.getFluid("milk"), 1000, true);
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
