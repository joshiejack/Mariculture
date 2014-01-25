package mariculture.core.handlers;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.Mariculture;
import mariculture.core.ClientProxy;
import mariculture.core.Core;
import mariculture.core.gui.GuiGuide;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.magic.Magic;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class GuideHandler {
	private static final ResourceLocation elements = new ResourceLocation(Mariculture.modid, "textures/gui/guide_elements.png");
	public static final HashMap icons = new HashMap();
	protected static RenderItem itemRenderer = new RenderItem();
	
	public static void registerIcons() {
		icons.put("blank", new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR));
		icons.put("ingotAluminum", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		icons.put("ingotIron", new ItemStack(Item.ingotIron));
		icons.put("glassPane", new ItemStack(Block.thinGlass));
		icons.put("ingotCopper", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER));
		icons.put("vat", new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT));
		
		if(Modules.magic.isActive()) {
			icons.put("basicMirror", new ItemStack(Magic.basicMirror));
		}
	}
	
	public static NodeList fetch(String xml) {
		return ClientProxy.getDocument(xml).getElementsByTagName("page");
	}

	public static void draw(GuiGuide gui, Node node, int x, int y, boolean left) {
		if(node == null)
			return;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			XMLHelper xml = new XMLHelper((Element) node);
			if(xml.getElementString("text") != null)
				parseTextPage(xml, gui, x, y, left);
			if(xml.e.getElementsByTagName("crafting") != null)
				parseCraftingPage(xml, gui, x, y, left);
		}
	}

	private static void parseImgPage(XMLHelper xml, GuiGuide gui, int x, int y) {
	}
	
	private static void parseCraftingPage(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		parseTextPage(xml, gui, x, y, left);
		
		Element e = xml.e;
		NodeList list = e.getElementsByTagName("crafting");
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				parseCrafting(new XMLHelper((Element) node), gui, x, y);
			}
		}
	}

	private static void parseCrafting(XMLHelper xml, GuiGuide gui, int x, int y) {
		x+=xml.getAttribInt("x");
		y+=xml.getAttribInt("y");
		String line1 = xml.getElementString("craft1");
		String line2 = xml.getElementString("craft2");
		String line3 = xml.getElementString("craft3");
		String output = xml.getElementString("craftResult");
		int number = xml.getElementInt("craftNum");
		
		String [] craft1 = line1.split("\\s*,\\s*");
		String [] craft2 = line2.split("\\s*,\\s*");
		String [] craft3 = line3.split("\\s*,\\s*");
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		gui.getMC().getTextureManager().bindTexture(elements);
		gui.drawTexturedModalRect(x - 1, y - 1, 0, 0, 58, 58);
		
		for(int i = 0; i < 3; i++) {
			drawItemStack(gui, (ItemStack) icons.get(craft1[i]), x + (i * 20), y + 0);
			drawItemStack(gui, (ItemStack) icons.get(craft2[i]), x + (i * 20), y + 20);
			drawItemStack(gui, (ItemStack) icons.get(craft3[i]), x + (i * 20), y + 40);
		}
		
		drawItemStack(gui, (ItemStack) icons.get(output), x + 64, y + 18);
		if(number < 10)
			gui.getMC().fontRenderer.drawString("x" + number, x + 67, y + 36, 4210752);
		else
			gui.getMC().fontRenderer.drawString("x" + number, x + 63, y + 36, 4210752);
	}
	
	private static void parseTextPage(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		FontRenderer font = gui.getMC().fontRenderer;
		String heading = xml.getElementString("heading");
		//Heading >>
		int boost = (left)? 80: 20;
		font.drawString(StringHelper.BOLD + heading, x + boost, y, 4210752);

		String text = xml.getElementString("text");	
		font.drawSplitString(text, x, y + 14, 180, 4210752);
	}
	
	private static void drawItemStack(GuiGuide gui, ItemStack stack, int x, int y) {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = gui.getFont();
        itemRenderer.renderItemIntoGUI(font, gui.getMC().getTextureManager(), stack, x, y, false);
        itemRenderer.renderItemOverlayIntoGUI(font, gui.getMC().getTextureManager(), stack, x, y, "");
        GL11.glDisable(GL11.GL_LIGHTING);
    }
}
