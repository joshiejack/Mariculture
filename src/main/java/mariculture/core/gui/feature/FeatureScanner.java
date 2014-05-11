package mariculture.core.gui.feature;

import mariculture.core.gui.GuiMariculture;

public class FeatureScanner extends Feature {	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		gui.drawTexturedModalRect(x + 176, y + 8, 1, 82, 25, 28);
		gui.drawTexturedModalRect(x - 62, y + 12, 86, 82, 62, 90);
	}
}
