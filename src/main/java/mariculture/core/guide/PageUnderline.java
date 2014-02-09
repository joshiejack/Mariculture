package mariculture.core.guide;


public class PageUnderline extends PageParser {
	int width;

	@Override
	public void read(XMLHelper xml) {	
		width = xml.getAttribAsInteger("width", 180);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x, y, 0, 82, width, 4);
	}
}
