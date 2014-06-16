package enchiridion.api.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;

import enchiridion.Enchiridion;
import enchiridion.api.GuiGuide;
import enchiridion.api.GuideHandler;
import enchiridion.api.XMLHelper;

public class PageImage extends PageParser {
	private static final HashMap<String, LinkedTexture> imageCache = new HashMap();
	
	float stretch;
	private ResourceLocation resource;
	private TextureManager tm;
	private String mod, src, key;
	
	@Override
	public void init(GuiGuide gui, int x, int y, boolean left) {
		super.init(gui, x, y, left);
		this.tm = gui.getMC().getTextureManager();
	}

	@Override
	public void read(Element xml) {
		stretch = XMLHelper.getAttribAsFloat(xml, "stretch", 1.0F);
		mod = XMLHelper.getAttribute(xml, "mod");
		if(!mod.equals("")) {
			resource = new ResourceLocation(mod, "textures/books/" + XMLHelper.getAttribute(xml, "src") + ".png");
		} else {
			src = XMLHelper.getAttribute(xml, "src");
			key = bookID + "|" + XMLHelper.getAttribute(xml, "src");
		}
	}
	
	@Override
	public void resize(Element xml) {
		x += XMLHelper.getAttribAsInteger(xml, "x", 0);
		y += XMLHelper.getAttribAsInteger(xml, "y", 0);
		size = XMLHelper.getAttribAsFloat(xml, "size", 1F);
		x = (int) ((x / (stretch * size)) * 1F);
		GL11.glScalef(stretch * size, size, size);
	}

	@Override
	public void parse() {		
		if(!mod.equals("")) {
			tm.bindTexture(resource);
			gui.drawTexturedModalRect(x, y, 0, 0, 256, 256);
		} else {
			if(!GuideHandler.DEBUG_ENABLED) {
				LinkedTexture tex = imageCache.get(key);
				if(tex != null) {
					drawImage(tex.texture, tex.resource, x + (100 - tex.width) / 2, y + (100 - tex.height) / 2, tex.width, tex.height);
				}
			} else {
				File file = new File(Enchiridion.root + File.separator + "debug" + File.separator + src + ".png");
				try {
					BufferedImage img = ImageIO.read(file);
					DynamicTexture dm = new DynamicTexture(img);
					ResourceLocation texture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(key, dm);
					LinkedTexture tex = new LinkedTexture(img.getHeight(), img.getWidth(), dm, texture);
					drawImage(tex.texture, tex.resource, x + (100 - tex.width) / 2, y + (100 - tex.height) / 2, tex.width, tex.height);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void addToCache(String str, LinkedTexture tex) {
		imageCache.put(str, tex);
	}
	
	private void drawImage(DynamicTexture dm, ResourceLocation rs, int x, int y, int width, int height) {
		dm.updateDynamicTexture();
		Tessellator tessellator = Tessellator.instance;
		tm.bindTexture(rs);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(x + width, y, 0, 1.0, 0.0);
		tessellator.addVertexWithUV(x, y, 0, 0.0, 0.0);
		tessellator.draw();
	}
	
	public static class LinkedTexture {
		int height, width;
		DynamicTexture texture;
		ResourceLocation resource;
		
		public LinkedTexture(int height, int width, DynamicTexture texture, ResourceLocation resource) {
			this.height = height;
			this.width = width;
			this.texture = texture;
			this.resource = resource;
		}
	}
}
