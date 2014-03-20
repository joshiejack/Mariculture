package mariculture.core.handlers;

import mariculture.api.core.IModules;
import mariculture.core.lib.Modules;

public class ModulesHandler implements IModules {
	@Override
	public boolean isLoaded(String module) {
		if(module.equals("core")) {
			return true;
		} else if (module.equals("diving")) {
			return Modules.isActive(Modules.diving);
		} else if (module.equals("factory")) {
			return Modules.isActive(Modules.factory);
		} else if (module.equals("fishery")) {
			return Modules.isActive(Modules.fishery);
		} else if (module.equals("magic")) {
			return Modules.isActive(Modules.magic);
		} else if (module.equals("sealife")) {
			return Modules.isActive(Modules.sealife);
		} else if (module.equals("transport")) {
			return Modules.isActive(Modules.transport);
		} else if (module.equals("world")) {
			return Modules.isActive(Modules.worldplus);
		}
		
		return false;
	}
}
