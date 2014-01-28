package mariculture.core.guide;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import mariculture.api.guide.PageParser;
import mariculture.api.guide.XMLHelper;

public class PageImage extends PageParser {
	ResourceLocation texture;
	float stretch;
	
	@Override
	public void read(XMLHelper xml) {
		stretch = xml.getAttribAsFloat("stretch", 1.0F);
		texture = new ResourceLocation("books" + ":" + xml.getAttribute("src") + ".png");
	}
	
	@Override
	public void resize(XMLHelper xml) {
		x += xml.getAttribAsInteger("x", 0);
		y += xml.getAttribAsInteger("y", 0);
		size = xml.getAttribAsFloat("size", 1F);
		x = (int) ((x / (stretch * size)) * 1F);
		GL11.glScalef(stretch * size, size, size);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(texture);
		gui.drawTexturedModalRect(x, y, 0, 0, 256, 256);
	}
}
