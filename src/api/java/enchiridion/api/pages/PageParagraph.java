package enchiridion.api.pages;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import enchiridion.api.Formatting;
import enchiridion.api.XMLHelper;

public class PageParagraph extends PageParser {
	String heading, text;
	int wrap, hX, hY, tX;
	float tSize, hSize;

	@Override
	public void read(Element xml) {
		wrap = XMLHelper.getAttribAsInteger(xml, "wrap", 216);
		
		int xH = 0;
		int xT = 0;
		
		heading = XMLHelper.getElement(xml, "heading");
		if(!heading.equals("")) {
			Element header = XMLHelper.getNode(xml, "heading");
			hSize = XMLHelper.getAttribAsFloat(header, "size", 1F);
			xH = XMLHelper.getAttribAsInteger(header, "x", 0);
			hY = XMLHelper.getAttribAsInteger(header, "y", 0);
		}
		
		text = XMLHelper.getElement(xml, "text");
		if(!text.equals("")) {
			Element words = XMLHelper.getNode(xml, "text");
			tSize = XMLHelper.getAttribAsFloat(words, "size", 0.85F);
			xT = XMLHelper.getAttribAsInteger(words, "x", 0);
		}

		hX = (int) (((x + xH) / hSize) * 1F);
		tX = (int) (((x + xT) / tSize) * 1F);
	}
	
	@Override
	public void resize(Element xml) {
		y += XMLHelper.getAttribAsInteger(xml, "y", 0);
	}

	@Override
	public void parse() {				
		if(!heading.equals("")) {
			GL11.glPushMatrix();
			GL11.glScalef(hSize, hSize, hSize);
			font.drawString(Formatting.BOLD + heading, hX, y - 16 + hY, 4210752);
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		GL11.glScalef(tSize, tSize, tSize);
		font.drawSplitString(text, tX, y, wrap, 4210752);
		GL11.glPopMatrix();
	}
}
