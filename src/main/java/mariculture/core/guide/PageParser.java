package mariculture.core.guide;

import java.lang.reflect.Field;
import java.util.HashMap;

import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

public abstract class PageParser {
	//TODO: Split All my guide based stuff AWAY from Mariculture, and in to it's own mods, also add nbt parsing for items etc.
	protected static final ResourceLocation elements = new ResourceLocation("mariculture", "textures/gui/guide_elements.png");
	public static HashMap<String, PageParser> parsers = new HashMap();
	public static RenderBlocks renderer;
	protected static RenderItem itemRenderer = (RenderItem) RenderManager.instance.entityRenderMap.get(EntityItem.class);
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
	
	public void drawFluidStack(int x, int y, IIcon icon, int width, int height) {
		if (icon == null) {
			return;
		}

		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + height, gui.getZLevel(), minU, minV + (maxV - minV) * height / 16.0D);
		tessellator.addVertexWithUV(x + width, y + height, gui.getZLevel(), minU + (maxU - minU) * width / 16.0D, minV + (maxV - minV) * height / 16.0D);
		tessellator.addVertexWithUV(x + width, y + 0, gui.getZLevel(), minU + (maxU - minU) * width / 16.0D, minV);
		tessellator.addVertexWithUV(x + 0, y + 0, gui.getZLevel(), minU, minV);
		tessellator.draw();
	}
	
	protected void drawItemStack(ItemStack stack, int x, int y) {
		if(stack == null) return;
		try {
			if(renderer == null) {
				Field field = Minecraft.getMinecraft().renderGlobal.getClass().getDeclaredField("renderBlocksRg");
				field.setAccessible(true);
				renderer = (RenderBlocks) field.get(Minecraft.getMinecraft().renderGlobal);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
		try {
	        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
	        FontRenderer font = null;
	        if (stack != null) font = stack.getItem().getFontRenderer(stack);
	        if (font == null) font = gui.getFont();
	        Minecraft mc = Minecraft.getMinecraft();
	        if (!ForgeHooksClient.renderInventoryItem(renderer, mc.getTextureManager(), stack, 
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
		        if (!ForgeHooksClient.renderInventoryItem(renderer, mc.getTextureManager(), stack2, 
		        		itemRenderer.renderWithColor, itemRenderer.zLevel, (float)x, (float)y)) {
		        	itemRenderer.renderItemIntoGUI(font, gui.getMC().getTextureManager(), stack2, x, y, false);
		        }
			} catch (Exception e2) {
				LogHandler.log(Level.ERROR, "Rendering failed when trying to render " + stack.toString());
				e2.printStackTrace();
			}
		} 
    }
}