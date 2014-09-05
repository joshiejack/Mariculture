package joshie.mariculture.plugins;

import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.core.util.XPRegistry;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginLiquidXP extends Plugin {
    public PluginLiquidXP(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        Fluids.add("xp", FluidRegistry.getFluid("immibis.liquidxp"), 100, true);
        XPRegistry.register("immibis.liquidxp", 100);
    }

    @Override
    public void postInit() {
        return;
    }
}
