package maritech.plugins;

import static mariculture.plugins.PluginEnchiridion.register;
import mariculture.core.lib.Modules;
import maritech.extensions.modules.ExtensionCore;
import maritech.extensions.modules.ExtensionDiving;
import maritech.extensions.modules.ExtensionFactory;
import maritech.extensions.modules.ExtensionFishery;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class Enchiridion {
	public static void init() {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			register(ExtensionCore.batteryCopper);
			register(ExtensionCore.batteryTitanium);
			
			if (Modules.isActive(Modules.diving)) {
				register(ExtensionDiving.scubaMask);
				register(ExtensionDiving.scubaSuit);
				register(ExtensionDiving.scubaTank);
				register(ExtensionDiving.swimfin);
			}
			
			if (Modules.isActive(Modules.factory)) {
				register(ExtensionFactory.fludd);
				register(ExtensionFactory.turbineAluminum);
				register(ExtensionFactory.turbineCopper);
				register(ExtensionFactory.turbineTitanium);
			}
			
			if (Modules.isActive(Modules.fishery)) {
				register(ExtensionFishery.rodFlux);
			}
		}
	}
}
