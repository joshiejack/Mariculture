package joshie.mariculture.plugins;

import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.core.util.XPRegistry;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginOpenBlocks extends Plugin {
    public PluginOpenBlocks(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        Fluids.add("xp", FluidRegistry.getFluid("xpjuice"), 20, true);
        XPRegistry.register("xpjuice", 20);
    }

    @Override
    public void postInit() {
        return;
    }
}
