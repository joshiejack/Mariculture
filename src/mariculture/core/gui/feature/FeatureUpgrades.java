package mariculture.core.gui.feature;

import mariculture.core.gui.GuiMariculture;

public class FeatureUpgrades extends Feature {	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		gui.drawTexturedModalRect(x + 176, y + 8, 176, 8, 25, 64);
	}
}
