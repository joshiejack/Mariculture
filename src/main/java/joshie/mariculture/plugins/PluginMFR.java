package joshie.mariculture.plugins;

import joshie.mariculture.core.handlers.OreDicHandler;
import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.core.util.XPRegistry;
import joshie.mariculture.plugins.Plugins.Plugin;
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
