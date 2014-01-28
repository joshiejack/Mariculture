package mariculture.core.guide;

import mariculture.api.guide.PageParser;
import mariculture.api.guide.XMLHelper;
import mariculture.core.helpers.cofh.StringHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class PageParagraph extends PageParser {
	String heading, text;
	int wrap, hX, hY, tX;
	float tSize, hSize;

	@Override
	public void read(XMLHelper xml) {
		wrap = xml.getAttribAsInteger("wrap", 216);
		
		int xH = 0;
		int xT = 0;
		
		heading = xml.getOptionalElement("heading");
		if(!heading.equals("")) {
			XMLHelper header = xml.getHelper("heading");
			hSize = header.getAttribAsFloat("size", 1F);
			xH = header.getAttribAsInteger("x", 0);
			hY = header.getAttribAsInteger("y", 0);
		}
		
		text = xml.getOptionalElement("text");
		if(!text.equals("")) {
			XMLHelper words = xml.getHelper("text");
			tSize = words.getAttribAsFloat("size", 0.85F);
			xT = words.getAttribAsInteger("x", 0);
		}

		hX = (int) (((x + xH) / hSize) * 1F);
		tX = (int) (((x + xT) / tSize) * 1F);
	}
	
	@Override
	public void resize(XMLHelper xml) {
		y += xml.getAttribAsInteger("y", 0);
	}

	@Override
	public void parse() {				
		if(!heading.equals("")) {
			GL11.glPushMatrix();
			GL11.glScalef(hSize, hSize, hSize);
			font.drawString(StringHelper.BOLD + heading, hX, y - 16 + hY, 4210752);
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		GL11.glScalef(tSize, tSize, tSize);
		font.drawSplitString(text, tX, y, wrap, 4210752);
		GL11.glPopMatrix();
	}
}
