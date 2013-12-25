package mariculture.core.gui.feature;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.util.IProgressable;

public class FeatureBubbles extends Feature {
	private IProgressable machine;
	private int xPoz;
	private int yPoz;
	
	public FeatureBubbles(IProgressable machine, int x, int y) {
		this.machine = machine;
		this.xPoz = x;
		this.yPoz = y;
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		if (mouseX >= (xPoz + 2) && mouseX <= (xPoz + 23) && mouseY >= (yPoz + 2) && mouseY <= (yPoz + 61)) {
			tooltip.add(machine.getProgessText());
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		gui.drawTexturedModalRect(x + xPoz, y + yPoz, 72, 0, 24, 61);
		
		int progress = machine.getProgressScaled(61);
		gui.drawTexturedModalRect(x + xPoz, y + yPoz + 61 - progress, 96, 61 - progress, 24, progress);
	}
}
