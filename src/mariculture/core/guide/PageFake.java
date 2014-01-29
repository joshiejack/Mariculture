package mariculture.core.guide;

import org.lwjgl.opengl.GL11;

import mariculture.core.gui.GuiGuide;

public class PageFake extends PageParser {
	@Override
	public void init(GuiGuide gui, int x, int y, boolean left) {}
	
	@Override
	public void resize(XMLHelper xml) {}
	
	@Override
	public void read(XMLHelper xml) {}

	@Override
	public void parse() {}
}
