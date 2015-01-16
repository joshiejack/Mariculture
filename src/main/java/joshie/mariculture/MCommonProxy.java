package joshie.mariculture;

import static joshie.mariculture.Mariculture.instance;
import joshie.mariculture.handlers.GuiHandler;
import joshie.mariculture.modules.Module;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class MCommonProxy {
    public void preInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        for(Module module: Module.modules) {
        	module.preInit();
        }
    }
    
    public void init() {
    	for(Module module: Module.modules) {
        	module.init();
        }
    }
    
    public void postInit() {
    	for(Module module: Module.modules) {
        	module.postInit();
        }
    	
        setupClient();
    }

    public void setupClient() {}
}