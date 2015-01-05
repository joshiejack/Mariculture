package mariculture.core.gui.feature;

import mariculture.core.gui.GuiMariculture;

public class FeatureRedExtras extends Feature {
    @Override
    public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
        super.draw(gui, x, y, mouseX, mouseY);
        gui.drawTexturedModalRect(x + 35, y - 32, 72, 188, 100, 32);
    }
}
