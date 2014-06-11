package mariculture.plugins;

import mariculture.plugins.Plugins.Plugin;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginWaila extends Plugin {
    @Override
    public void preInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "mariculture.plugins.waila.VatDataProvider.register");
        FMLInterModComms.sendMessage("Waila", "register", "mariculture.plugins.waila.CopperTankDataProvider.register");
    }

    @Override
    public void postInit() {
        // TODO Auto-generated method stub

    }
}
