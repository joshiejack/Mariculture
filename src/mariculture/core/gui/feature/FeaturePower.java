package mariculture.core.gui.feature;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.util.IPowered;

public class FeaturePower extends Feature {
	private IPowered machine;
	private int xPoz;
	private int yPoz;
	
	public FeaturePower(IPowered machine, int x, int y) {
		this.machine = machine;
		this.xPoz = x;
		this.yPoz = y;
	}
	
	@Override
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		if (mouseX >= (xPoz) && mouseX <= (xPoz + 4) && mouseY >= (yPoz) && mouseY <= (yPoz + 60)) {
			System.out.println("text");
			tooltip.add(machine.getPowerText());
		}
	}
	
	@Override
	public void draw(GuiMariculture gui, int x, int y) {
		int power = machine.getPowerScaled(60);
		gui.drawTexturedModalRect(x + xPoz, y + yPoz + 60 - power, 128, 60 - power, 5, power);
	}
}
