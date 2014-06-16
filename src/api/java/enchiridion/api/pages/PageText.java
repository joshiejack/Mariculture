package enchiridion.api.pages;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import enchiridion.api.Formatting;
import enchiridion.api.XMLHelper;

public class PageText extends PageParser {
	int wrap;
	String bold, italics, underline, color, text;

	@Override
	public void read(Element xml) {
		if(!XMLHelper.getAttribute(xml, "color").equals(""))
			color = Formatting.getColor(XMLHelper.getAttribute(xml, "color"));
		else
			color = "";
		bold = (XMLHelper.getAttribAsBoolean(xml, "bold"))? Formatting.BOLD: "";
		italics = (XMLHelper.getAttribAsBoolean(xml, "italic"))? Formatting.ITALIC: "";
		underline = (XMLHelper.getAttribAsBoolean(xml, "underline"))? Formatting.UNDERLINE: "";
		wrap = XMLHelper.getAttribAsInteger(xml, "wrap", 216);
		text = XMLHelper.getSelf(xml);
	}
	
	@Override
	public void resize(Element xml) {
		x += XMLHelper.getAttribAsInteger(xml, "x", 0);
		y += XMLHelper.getAttribAsInteger(xml, "y", 0);
		if(bold.equals("")) {
			size = XMLHelper.getAttribAsFloat(xml, "size", 0.85F);
		} else {
			size = XMLHelper.getAttribAsFloat(xml, "size", 1F);
		}
		
		x = (int) ((x / size) * 1F);
		GL11.glScalef(size, size, size);
	}

	@Override
	public void parse() {
		font.drawSplitString(color + bold + italics + underline + text, x, y, wrap, 4210752);
	}
}