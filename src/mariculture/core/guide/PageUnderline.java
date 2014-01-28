package mariculture.core.guide;

import mariculture.api.guide.PageParser;
import mariculture.api.guide.XMLHelper;

public class PageUnderline extends PageParser {
	int width;

	@Override
	public void read(XMLHelper xml) {	
		width = xml.getAttribAsInteger("width", 220);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x, y, 0, 82, width, 4);
	}
}
