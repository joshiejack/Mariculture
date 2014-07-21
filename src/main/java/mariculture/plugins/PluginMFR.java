package mariculture.plugins;

import net.minecraftforge.fluids.FluidRegistry;
import mariculture.core.handlers.OreDicHandler;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import powercrystals.minefactoryreloaded.MFRRegistry;

public class PluginMFR extends Plugin {
    @Override
    public void preInit() {
        OreDicHandler.has_unifier = true;
        if (FluidRegistry.getFluid("milk") != null) {
            Fluids.instance.addFluid("milk", FluidRegistry.getFluid("milk"));
        }
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
