package mariculture.core.guide;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mariculture.Mariculture;
import mariculture.core.lib.Extra;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class PageImage extends PageParser {
	boolean internal;
	float stretch;
	private ResourceLocation resource;
	private TextureManager tm;
	private String src, key;
	
	@Override
	public void init(GuiGuide gui, int x, int y, boolean left) {
		super.init(gui, x, y, left);
		this.tm = gui.getMC().getTextureManager();
	}

	@Override
	public void read(XMLHelper xml) {
		stretch = xml.getAttribAsFloat("stretch", 1.0F);
		internal = xml.getAttribAsBoolean("local");
		String mod = xml.getOptionalAttribute("mod");
		if(internal) {
			if(mod.equals("")) {
				resource = new ResourceLocation(Mariculture.modid, "/textures/books/" + xml.getAttribute("src") + ".png");
			} else {
				resource = new ResourceLocation(mod, "/textures/books/" + xml.getAttribute("src") + ".png");
			}
		} else {
			src = xml.getAttribute("src");
			key = bookID + "|" + xml.getAttribute("src");
		}
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
		if(internal) {
			tm.bindTexture(resource);
			gui.drawTexturedModalRect(x, y, 0, 0, 256, 256);
		} else {
			if(!Extra.DEBUG_ON) {
				LinkedTexture tex = CompatBooks.imageCache.get(key);
				if(tex != null) {
					drawImage(tex.texture, tex.resource, x + (100 - tex.width) / 2, y + (100 - tex.height) / 2, tex.width, tex.height);
				}
			} else {
				File file = new File(Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + 
						File.separator  + "config" + File.separator + "books" + File.separator + "debug" + File.separator + src + ".png");
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
	
	/**
	public static final HashMap<String, LinkedTexture> cache = new HashMap();
	
	float stretch;
	private File file;
	private BufferedImage img;
	private DynamicTexture dmTexture;
	private ResourceLocation texture;
	private TextureManager tm;
	boolean internal;
	String path, mod;
	
	@Override
	public void init(GuiGuide gui, int x, int y, boolean left) {
		super.init(gui, x, y, left);
		this.tm = gui.getMC().getTextureManager();
	}
	
	@Override
	public void read(XMLHelper xml) {
		stretch = xml.getAttribAsFloat("stretch", 1.0F);
		internal = xml.getAttribAsBoolean("local");
		mod = xml.getOptionalAttribute("mod").equals("")? "books": xml.getOptionalAttribute("mod");
		String root = Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + "\\assets\\" + mod + "\\" + xml.getAttribute("src") + ".png";
		root = root.replace('/', File.separatorChar);
		root = root.replace('\\', File.separatorChar);
		
		file = new File(root);
		texture = new ResourceLocation("books" + ":" + xml.getAttribute("src") + ".png");
		path = xml.getAttribute("src");
	}
	
	@Override
	public void resize(XMLHelper xml) {
		x += xml.getAttribAsInteger("x", 0);
		y += xml.getAttribAsInteger("y", 0);
		size = xml.getAttribAsFloat("size", 1F);
		x = (int) ((x / (stretch * size)) * 1F);
		GL11.glScalef(stretch * size, size, size);
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
	
	@Override
	public void parse() {
		int height = 0;
		int width = 0;
		if(!cache.containsKey(path + "|" + mod)) {
			try {
				if(internal) {
					img = ImageIO.read(Mariculture.class.getResourceAsStream("/assets/books/" + path + ".png"));
				} else {
					img = ImageIO.read(file);
				}
				
				dmTexture = new DynamicTexture(img);
				texture = tm.getDynamicTextureLocation("image", dmTexture);
				height = img.getHeight();
				width = img.getWidth();
				cache.put(path + "|" + mod, new LinkedTexture(height, width, dmTexture, texture));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			LinkedTexture tex = cache.get(path + "|" + mod);
			dmTexture = tex.texture;
			texture = tex.resource;
			width = tex.width;
			height = tex.height;
		}
		
		drawImage(x + (100 - width) / 2, y + (100 - height) / 2, width, height);
	}
	
	private void drawImage(int x, int y, int width, int height) {
		dmTexture.updateDynamicTexture();
		Tessellator tessellator = Tessellator.instance;
		tm.bindTexture(texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y + height, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(x + width, y, 0, 1.0, 0.0);
		tessellator.addVertexWithUV(x, y, 0, 0.0, 0.0);
		tessellator.draw();
	} */
}
