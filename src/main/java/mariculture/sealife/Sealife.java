package mariculture.sealife;

import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules.Module;

public class Sealife extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive && Extra.DEBUG_ON;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	@Override
	public void registerEntities() {
	}
	
	@Override
	public void registerHandlers() {
	}

	@Override
	public void registerBlocks() {
	}

	@Override
	public void registerItems() {
	}
	
	@Override
	public void registerOther() {
	}
	
	@Override
	public void addRecipes() {
	}
}
