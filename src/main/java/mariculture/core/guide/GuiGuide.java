package mariculture.core.guide;

import java.io.File;
import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.Text;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GuiGuide extends GuiScreen {
	protected int guiTick = 0;
	protected int mouseXLeft = 0;
	protected int mouseXRight = 0;
	public int mouseY = 0;
	
	private static final ResourceLocation cover_left = new ResourceLocation(Mariculture.modid, "textures/gui/guide_cover_left.png");
	private static final ResourceLocation cover_right = new ResourceLocation(Mariculture.modid, "textures/gui/guide_cover_right.png");
	private static final ResourceLocation page_left = new ResourceLocation(Mariculture.modid, "textures/gui/guide_page_left.png");
	private static final ResourceLocation page_right = new ResourceLocation(Mariculture.modid, "textures/gui/guide_page_right.png");
	
	public static HashMap lastPage = new HashMap();
	
	protected float red, green, blue;
	protected int leftX = 212;
	protected int rightX = 218;
	
	protected int ySize = 217;
	protected int maxPage;
	private int hex;
	public String xml;
	
	public static int currentPage;
	public NodeList guide;
	public Node page1;
	public Node page2;
	
	public FontRenderer getFont() {
		return this.fontRendererObj;
	}
	
	public Minecraft getMC() {
		return this.mc;
	}
	
	public void setZLevel(float f) {
		this.zLevel = f;
	}

	public GuiGuide(int hex, String xml) {
		this.hex = hex;
		this.xml = xml;
		if(lastPage.containsKey(xml)) {
			currentPage = (Integer) lastPage.get(xml);
		} else {
			currentPage = 0;
		}
	}
	
	public void onGuiClosed() {
		lastPage.put(xml, currentPage);
	}
	
	@Override
	public void initGui() {
		red = (hex >> 16 & 255) / 255.0F;
		green = (hex >> 8 & 255) / 255.0F;
		blue = (hex & 255) / 255.0F;
		guide = GuideHandler.fetch(xml);
		maxPage = guide.getLength();
		page1 = guide.item(currentPage);
		if(guide.item(currentPage) != null)
			page2 = guide.item(currentPage + 1);
		String root = Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + "\\assets\\books\\magical\\test_img.png";
		pic = new PagePicture(mc.getTextureManager(), new File(root));
	}
	
	private void drawPage(Node page, int x, int y, boolean left) {
		GuideHandler.draw(this, page, x, y + 9, left);
	}
	
	public void drawLeftPage(int x, int y) {
		//Cover
		GL11.glColor4f(red, green, blue, 1.0F);
		mc.getTextureManager().bindTexture(cover_left);
		drawTexturedModalRect(x - 9, y, 35, 0, leftX + 9, ySize);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
				
		//Page
		mc.getTextureManager().bindTexture(page_left);
		drawTexturedModalRect(x, y, 44, 0, leftX, ySize);
				
		//Arrows
		drawTexturedModalRect(x + 21, y + 200, 0, 246, 18, 10);
		if(mouseXRight >= -192 && mouseXRight <= -174 && mouseY >= 100 && mouseY <= 110)
			drawTexturedModalRect(x + 21, y + 200, 23, 246, 18, 10);
				
		//Draw Page
		drawPage(page1, x + 24, y + 15, true);
		
		//Numbers
		mc.fontRenderer.drawString(Text.DARK_GREY + StringHelper.BOLD + (currentPage + 1) + "/" + guide.getLength(), x + 42, y + 202, 0);
	}
	
	public void drawRightPage(int x, int y) {
		// Cover
		GL11.glColor4f(red, green, blue, 1.0F);
		mc.getTextureManager().bindTexture(cover_right);
		drawTexturedModalRect(x, y, 0, 0, rightX + 9, ySize);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
				
		//Page
		mc.getTextureManager().bindTexture(page_right);
		drawTexturedModalRect(x, y, 0, 0, rightX, ySize);
				
		//Arrows
		drawTexturedModalRect(x + 175, y + 200, 0, 246, 18, 10);
		if(mouseXRight >= 175 && mouseXRight <= 192 && mouseY >= 100 && mouseY <= 110)
			drawTexturedModalRect(x + 175, y + 200, 23, 246, 18, 10);
				
		//Draw Page
		drawPage(page2, x + 6, y + 15, false);
				
		//Numbers
		if(guide.getLength() %2 == 0 || currentPage < guide.getLength() - 1) {
			String name = (currentPage + 2) + "/" + guide.getLength();
			int xBonus = 0;
			if(name.length() < 4)
				xBonus = 28;
			else if(name.length() < 5)
				xBonus = 20;
			else if(name.length() < 6)
				xBonus = 15;
			else if(name.length() < 7)
				xBonus = 8;
			else if(name.length() < 8)
				xBonus = 0;
			mc.fontRenderer.drawString(Text.DARK_GREY + StringHelper.BOLD + name, x + 124 + xBonus, y + 202, 0);
		}
	}

	public void drawScreen(int i, int j, float f) {		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = width / 2;
		int y = 8;
		
		drawLeftPage(x - 212, y);
		drawRightPage(x, y);
		
		guiTick++;
		if(guiTick %64 == 0) {
			GuideHandler.updateIcons();
		}
		
		super.drawScreen(i, j, f);
	}
	
	PagePicture pic;
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3)  {
		super.mouseClicked(par1, par2, par3);
		boolean clicked = false;
		if(mouseXRight >= -192 && mouseXRight <= -174 && mouseY >= 100 && mouseY <= 110) {
			clicked = true;
			currentPage-=2;
		}
		
		if(mouseXRight >= 175 && mouseXRight <= 192 && mouseY >= 100 && mouseY <= 110) {
			clicked = true;
			currentPage+=2;
		}
		
		if(clicked) {
			//If Books is even length
			if(guide.getLength() % 2 == 0) {
				if(currentPage < 0) {
					currentPage = guide.getLength() - 2;
				} else if (currentPage >= guide.getLength()) {
					currentPage = 0;
				}
			} else {
				if(currentPage < 0) {
					currentPage = guide.getLength() - 1;
				} else if(currentPage > guide.getLength()) {
					currentPage = 0;
				}
			}

			page1 = guide.item(currentPage);
			page2 = guide.item(currentPage + 1);
		}
    }
	
	@Override
	public void handleMouseInput() {
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		mouseXRight = x - (this.width /2);
		mouseY = y - (ySize/2);

		super.handleMouseInput();
	}
}
