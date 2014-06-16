package enchiridion.api;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enchiridion.api.pages.PageParser;

public class GuiGuide extends GuiScreen {
	protected int guiTick = 0;
	protected int mouseXLeft = 0;
	protected int mouseXRight = 0;
	public int mouseY = 0;
	
	public static final HashMap<String, ResourceLocation> cover_left_cache = new HashMap();
	public static final HashMap<String, ResourceLocation> cover_right_cache = new HashMap();
	public static final HashMap<String, ResourceLocation> page_left_cache = new HashMap();
	public static final HashMap<String, ResourceLocation> page_right_cache = new HashMap();
	private static ResourceLocation cover_left;
	private static ResourceLocation cover_right;
	private static ResourceLocation page_left;
	private static ResourceLocation page_right;
	
	public static HashMap lastPage = new HashMap();
	public static int loopRate = 96;
	
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
	
	public double getZLevel() {
		return this.zLevel;
	}

	public GuiGuide(int hex, String xml) {
		this.hex = hex;
		this.xml = xml;
		if(cover_left_cache.get(xml) == null) cover_left_cache.put(xml, new ResourceLocation("books", "textures/gui/guide_cover_left.png"));
		if(cover_right_cache.get(xml) == null) cover_right_cache.put(xml, new ResourceLocation("books" , "textures/gui/guide_cover_right.png"));
		if(page_left_cache.get(xml) == null) page_left_cache.put(xml, new ResourceLocation("books", "textures/gui/guide_page_left.png"));
		if(page_right_cache.get(xml) == null) page_right_cache.put(xml, new ResourceLocation("books", "textures/gui/guide_page_right.png"));
	}
	
	public GuiGuide(int hex, String bg, String xml) {
		this(hex, xml);
		if(cover_left_cache.get(xml) == null) cover_left_cache.put(xml, new ResourceLocation(bg));
		if(cover_right_cache.get(xml) == null) cover_right_cache.put(xml, new ResourceLocation(bg));
		if(page_left_cache.get(xml) == null) page_left_cache.put(xml, new ResourceLocation(bg));
		if(page_right_cache.get(xml) == null) page_right_cache.put(xml, new ResourceLocation(bg));
	}

	public void onGuiClosed() {
		lastPage.put(xml, currentPage);
	}
	
	@Override
	public void initGui() {		
		if(lastPage.containsKey(xml)) {
			currentPage = (Integer) lastPage.get(xml);
		} else {
			currentPage = 0;
		}
		
		cover_left = cover_left_cache.get(xml);
		cover_right = cover_right_cache.get(xml);
		page_left = page_left_cache.get(xml);
		page_right = page_right_cache.get(xml);
		red = (hex >> 16 & 255) / 255.0F;
		green = (hex >> 8 & 255) / 255.0F;
		blue = (hex & 255) / 255.0F;
		guide = GuideHandler.getDocument(xml);
		maxPage = guide.getLength();
		page1 = guide.item(currentPage);
		if(guide.item(currentPage) != null) page2 = guide.item(currentPage + 1);
	}
	
	//Decorate each element
	public void draw(GuiGuide gui, Node node, int x, int y, boolean left) {
		if(node == null)
			return;
		if(node.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) node;
			for (Entry<String, PageParser> page : PageParser.parsers.entrySet()) {
				if(e.getElementsByTagName(page.getKey()) != null) {
					NodeList list = e.getElementsByTagName(page.getKey());
					for(int i = 0; i < list.getLength(); i++) {
						Node n = list.item(i);
						if(n.getNodeType() == Node.ELEMENT_NODE) {
							PageParser parser = page.getValue();
							parser.init(gui, x, y, left);
							parser.read((Element) n);
							GL11.glPushMatrix();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							parser.resize((Element) n);
							parser.parse();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							GL11.glPopMatrix();
						}
					}
				}
			}
		}
	}
	
	private void drawPage(Node page, int x, int y, boolean left) {
		draw(this, page, x, y + 9, left);
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
		mc.fontRenderer.drawString(Formatting.DARK_GREY + Formatting.BOLD + (currentPage + 1) + "/" + guide.getLength(), x + 42, y + 202, 0);
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
			mc.fontRenderer.drawString(Formatting.DARK_GREY + Formatting.BOLD + name, x + 124 + xBonus, y + 202, 0);
		}
	}

	public void drawScreen(int i, int j, float f) {		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = width / 2;
		int y = 8;
		
		drawLeftPage(x - 212, y);
		drawRightPage(x, y);
		
		if(loopRate >= 1) {
			guiTick++;
			if(guiTick %loopRate == 0) {
				DisplayRegistry.updateIcons();
			}
		}
		
		super.drawScreen(i, j, f);
	}
		
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
