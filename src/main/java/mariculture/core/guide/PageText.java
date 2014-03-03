package mariculture.core.guide;

import mariculture.core.util.Text;

import org.lwjgl.opengl.GL11;

public class PageText extends PageParser {
	int wrap;
	String bold, italics, underline, color, text;

	@Override
	public void read(XMLHelper xml) {
		if(!xml.getAttribute("color").equals(""))
			color = Text.getColor(xml.getAttribute("color"));
		else
			color = "";
		bold = (xml.getAttribAsBoolean("bold"))? Text.BOLD: "";
		italics = (xml.getAttribAsBoolean("italic"))? Text.ITALIC: "";
		underline = (xml.getAttribAsBoolean("underline"))? Text.UNDERLINE: "";
		wrap = xml.getAttribAsInteger("wrap", 216);
		text = xml.getSelf();
	}
	
	@Override
	public void resize(XMLHelper xml) {
		x += xml.getAttribAsInteger("x", 0);
		y += xml.getAttribAsInteger("y", 0);
		if(bold.equals("")) {
			size = xml.getAttribAsFloat("size", 0.85F);
		} else {
			size = xml.getAttribAsFloat("size", 1F);
		}
		
		x = (int) ((x / size) * 1F);
		GL11.glScalef(size, size, size);
	}

	@Override
	public void parse() {
		font.drawSplitString(color + bold + italics + underline + text, x, y, wrap, 4210752);
	}
}