package mariculture.core.handlers;

import mariculture.api.core.IModules;
import mariculture.core.lib.Modules;

public class ModulesHandler implements IModules {
	@Override
	public boolean isLoaded(String module) {
		if(module.equals("core")) {
			return Modules.core.isActive();
		} else if (module.equals("diving")) {
			return Modules.diving.isActive();
		} else if (module.equals("factory")) {
			return Modules.factory.isActive();
		} else if (module.equals("fishery")) {
			return Modules.fishery.isActive();
		} else if (module.equals("magic")) {
			return Modules.magic.isActive();
		} else if (module.equals("sealife")) {
			return Modules.sealife.isActive();
		} else if (module.equals("transport")) {
			return Modules.transport.isActive();
		} else if (module.equals("world")) {
			return Modules.world.isActive();
		}
		
		return false;
	}
}
