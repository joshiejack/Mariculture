package mariculture.plugins;

import net.minecraftforge.fluids.FluidRegistry;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import cpw.mods.fml.common.event.FMLInterModComms;

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
