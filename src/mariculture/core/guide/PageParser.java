package mariculture.core.guide;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import mariculture.Mariculture;
import mariculture.core.gui.GuiGuide;
import mariculture.core.helpers.XMLHelper;

public abstract class PageParser {
	protected static final ResourceLocation elements = new ResourceLocation(Mariculture.modid, "textures/gui/guide_elements.png");
	public static HashMap<String, PageParser> parsers = new HashMap();
	protected static RenderItem itemRenderer = new RenderItem();
	public String node;
	
	protected FontRenderer font;
	protected GuiGuide gui;
	protected boolean left;
	protected int x, y;
	
	public void init(GuiGuide gui, boolean left, int x, int y) {
		this.gui = gui;
		this.left = left;
		this.x = x;
		this.y = y;
		font = gui.getFont();
	}
	
	public abstract void read(XMLHelper xml);
	public abstract void parse();
	
	protected void drawItemStack(GuiGuide gui, ItemStack stack, int x, int y) {
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
    }
}
