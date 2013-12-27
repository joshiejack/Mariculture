package mariculture.core.gui.feature;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.util.IProgressable;

public class FeatureArrow extends Feature {
	private IProgressable machine;
	private int xPoz;
	private int yPoz;
	
	public FeatureArrow(IProgressable machine, int x, int y) {
		this.machine = machine;
		this.xPoz = x;
		this.yPoz = y;
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		if (mouseX >= (xPoz + 1) && mouseX <= (xPoz + 20) && mouseY >= (yPoz + 3) && mouseY <= (yPoz + 14)) {
			tooltip.add(machine.getProgessText());
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		super.draw(gui, x, y, mouseX, mouseY);
		gui.drawTexturedModalRect(x + xPoz, y + yPoz, 198, 74, 22, 16);
		
		int progress = machine.getProgressScaled(22);
		gui.drawTexturedModalRect(x + xPoz, y + yPoz, 176, 74, progress, 16);
	}
}
