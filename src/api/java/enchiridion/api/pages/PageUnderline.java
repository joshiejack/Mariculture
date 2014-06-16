package enchiridion.api.pages;

import org.w3c.dom.Element;

import enchiridion.api.XMLHelper;


public class PageUnderline extends PageParser {
	int width;

	@Override
	public void read(Element xml) {	
		width = XMLHelper.getAttribAsInteger(xml, "width", 180);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x, y, 0, 82, width, 4);
	}
}
