package mariculture.core.gui;

import mariculture.core.Mariculture;
import mariculture.core.handlers.GuideHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.NodeList;

public class GuiGuide extends GuiScreen {
	private static final ResourceLocation cover_left = new ResourceLocation(Mariculture.modid, "textures/gui/guide_cover_left.png");
	private static final ResourceLocation cover_right = new ResourceLocation(Mariculture.modid, "textures/gui/guide_cover_right.png");
	private static final ResourceLocation page_left = new ResourceLocation(Mariculture.modid, "textures/gui/guide_page_left.png");
	private static final ResourceLocation page_right = new ResourceLocation(Mariculture.modid, "textures/gui/guide_page_right.png");
	
	protected float red, green, blue;
	protected int xSize = 256;
	protected int ySize = 256;
	protected int maxPage;
	
	public static int currentPage;
	public NodeList guide;
	
	public FontRenderer getFont() {
		return this.fontRenderer;
	}
	
	public Minecraft getMC() {
		return this.mc;
	}

	public GuiGuide(int hex, String xml) {
		red = (hex >> 16 & 255) / 255.0F;
		green = (hex >> 8 & 255) / 255.0F;
		blue = (hex & 255) / 255.0F;
		guide = GuideHandler.fetch(xml);
		maxPage = guide.getLength()/2;
	}
	
	private void drawPage(int i, int x, int y) {
		GuideHandler.draw(this, guide.item(i), x, y);
	}

	public void drawScreen(int i, int j, float f) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width) / 2;
		int y = 8;
	//Right Page
		// Cover
		GL11.glColor4f(red, green, blue, 1.0F);
		mc.getTextureManager().bindTexture(cover_right);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		//Page
		mc.getTextureManager().bindTexture(page_right);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawPage(currentPage + 1, x + 10, y + 20);

	//Left Page
		x = x - 256;
		//Cover
		GL11.glColor4f(red, green, blue, 1.0F);
		mc.getTextureManager().bindTexture(cover_left);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		//Page
		mc.getTextureManager().bindTexture(page_left);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		drawPage(currentPage, x + 70, y + 20);
	}
}
