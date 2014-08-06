package mariculture.plugins;

import mariculture.core.handlers.OreDicHandler;
import mariculture.core.util.Fluids;
import mariculture.core.util.XPRegistry;
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
    }

    @Override
    public void init() {
        Fluids.add("milk", FluidRegistry.getFluid("milk"), 1000, true);
        Fluids.add("xp", FluidRegistry.getFluid("mobEssence"), 66, true);
        XPRegistry.register("mobEssence", 66.66666667f);
    }

    @Override
    public void postInit() {
        MFRRegistry.registerUnifierBlacklist("fish");
    }

    public static void blacklist(String name) {
        MFRRegistry.registerUnifierBlacklist(name);
    }
}
