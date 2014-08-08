package mariculture.plugins;

import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginHEE extends Plugin {
    public PluginHEE(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        Fluids.add("ender", FluidRegistry.getFluid("enderGoo"), 250, true);
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {}
}
