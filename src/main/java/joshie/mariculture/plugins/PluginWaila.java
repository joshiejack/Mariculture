package joshie.mariculture.plugins;

import joshie.mariculture.plugins.Plugins.Plugin;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginWaila extends Plugin {
    public PluginWaila(String name) {
        super(name);
    }

    @Override
    public void preInit() {}

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "mariculture.plugins.waila.WailaRegistrar.register");
    }

    @Override
    public void postInit() {}
}
