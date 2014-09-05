package joshie.mariculture.plugins;

import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginHEE extends Plugin {
    public PluginHEE(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        Fluid fluid = FluidRegistry.getFluid("enderGoo");
        if(fluid != null) {
            Fluids.add("ender", FluidRegistry.getFluid("enderGoo"), 250, true);
        }
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {}
}
