package mariculture.core.guide;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import mariculture.core.helpers.XMLHelper;

public class PageImage extends PageParser {
	ResourceLocation texture;
	float size;
	
	@Override
	public void read(XMLHelper xml) {
		size = xml.getAttribAsFloat("size", 1F);
		x += xml.getAttribAsInteger("x", 0);
		y += xml.getAttribAsInteger("y", 0);
		texture = new ResourceLocation("books" + ":" + xml.getAttribute("src") + ".png");;
		x = (int) ((x / size) * 1F);
	}

	@Override
	public void parse() {
		gui.getMC().getTextureManager().bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		GL11.glScalef(size, size, size);
		gui.drawTexturedModalRect(x, y, 0, 0, 256, 256);
		GL11.glPopMatrix();
	}
}
