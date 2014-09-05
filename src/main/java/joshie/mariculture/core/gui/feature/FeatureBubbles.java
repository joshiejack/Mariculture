package joshie.mariculture.core.gui.feature;

import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.util.IProgressable;

public class FeatureBubbles extends Feature {
    private IProgressable machine;
    private int xPoz;
    private int yPoz;

    public FeatureBubbles(IProgressable machine, int x, int y) {
        this.machine = machine;
        xPoz = x;
        yPoz = y;
    }

    @Override
    public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
        super.draw(gui, x, y, mouseX, mouseY);

        gui.drawTexturedModalRect(x + xPoz, y + yPoz, 72, 0, 24, 61);

        int progress = machine.getProgressScaled(61);
        gui.drawTexturedModalRect(x + xPoz, y + yPoz + 61 - progress, 96, 61 - progress, 24, progress);
    }
}
