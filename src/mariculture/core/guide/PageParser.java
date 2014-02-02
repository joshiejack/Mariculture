package mariculture.core.guide;

import java.util.HashMap;

import mariculture.core.Core;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.AirMeta;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

public abstract class PageParser {
	protected static final ResourceLocation elements = new ResourceLocation("mariculture", "textures/gui/guide_elements.png");
	public static HashMap<String, PageParser> parsers = new HashMap();
	protected static RenderItem itemRenderer = new RenderItem();
	public String node;
	
	protected String bookID;
	protected FontRenderer font;
	protected GuiGuide gui;
	protected boolean left;
	protected int x, y;
	protected float size;
	
	public void init(GuiGuide gui, int x, int y, boolean left) {
		this.bookID = gui.xml;
		this.gui = gui;
		this.left = left;
		this.x = x;
		this.y = y;
		this.font = gui.getFont();
	}
	
	public void resize(XMLHelper xml) {
		x += xml.getAttribAsInteger("x", 0);
		y += xml.getAttribAsInteger("y", 0);
		size = xml.getAttribAsFloat("size", 1F);
		x = (int) ((x / size) * 1F);
		GL11.glScalef(size, size, size);
	}
	
	public abstract void read(XMLHelper xml);
	public abstract void parse();
	
	protected void drawItemStack(GuiGuide gui, ItemStack stack, int x, int y) {
		try {
	        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
	        FontRenderer font = null;
	        if (stack != null) font = stack.getItem().getFontRenderer(stack);
	        if (font == null) font = gui.getFont();
	        Minecraft mc = Minecraft.getMinecraft();
	        if (!ForgeHooksClient.renderInventoryItem(mc.renderGlobal.globalRenderBlocks, mc.getTextureManager(), stack, 
	        		itemRenderer.renderWithColor, itemRenderer.zLevel, (float)x, (float)y)) {
	        	itemRenderer.renderItemIntoGUI(font, gui.getMC().getTextureManager(), stack, x, y, false);
	        }
	        
	        GL11.glDisable(GL11.GL_LIGHTING);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ItemStack stack2 = OreDicHelper.getNextEntry(stack);
				GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		        FontRenderer font = null;
		        if (stack2 != null) font = stack2.getItem().getFontRenderer(stack2);
		        if (font == null) font = gui.getFont();
		        Minecraft mc = Minecraft.getMinecraft();
		        if (!ForgeHooksClient.renderInventoryItem(mc.renderGlobal.globalRenderBlocks, mc.getTextureManager(), stack2, 
		        		itemRenderer.renderWithColor, itemRenderer.zLevel, (float)x, (float)y)) {
		        	itemRenderer.renderItemIntoGUI(font, gui.getMC().getTextureManager(), stack2, x, y, false);
		        }
			} catch (Exception e2) {
				ItemStack stack2 = new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR);
				GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		        FontRenderer font = null;
		        if (stack2 != null) font = stack2.getItem().getFontRenderer(stack2);
		        if (font == null) font = gui.getFont();
		        Minecraft mc = Minecraft.getMinecraft();
		        if (!ForgeHooksClient.renderInventoryItem(mc.renderGlobal.globalRenderBlocks, mc.getTextureManager(), stack2, 
		        		itemRenderer.renderWithColor, itemRenderer.zLevel, (float)x, (float)y)) {
		        	itemRenderer.renderItemIntoGUI(font, gui.getMC().getTextureManager(), stack2, x, y, false);
		        }
			}
		}
    }
}
