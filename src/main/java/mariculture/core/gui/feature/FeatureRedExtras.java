package mariculture.core.gui.feature;

import mariculture.core.gui.GuiMariculture;

public class FeatureRedExtras extends Feature {
    @Override
    public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
        super.draw(gui, x, y, mouseX, mouseY);
        gui.drawTexturedModalRect(x - 25, y - 2, 59, 130, 25, 74);
    }
}
