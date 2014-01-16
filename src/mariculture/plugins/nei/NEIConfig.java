package mariculture.plugins.nei;

import mariculture.core.gui.GuiLiquifier;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new NEILiquifierRecipeHandler());
		API.registerUsageHandler(new NEILiquifierRecipeHandler());
		API.setGuiOffset(GuiLiquifier.class, 0, 0);
	}

	@Override
	public String getName() {
		return "Mariculture NEI";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
