package mariculture.core.handlers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.ClientProxy;
import mariculture.core.Core;
import mariculture.core.gui.GuiGuide;
import mariculture.core.guide.PageCrafting;
import mariculture.core.guide.PageImage;
import mariculture.core.guide.PageParagraph;
import mariculture.core.guide.PageParser;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.Rand;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class GuideHandler {
	private static final ResourceLocation elements = new ResourceLocation(Mariculture.modid, "textures/gui/guide_elements.png");
	public static final HashMap<String, ItemStack> icons = new HashMap();
	public static final ArrayList<LinkedMeta> metaCycling = new ArrayList<LinkedMeta>();
	public static final ArrayList<String> oreDicCycling = new ArrayList<String>();
	protected static RenderItem itemRenderer = new RenderItem();
	
	public static class LinkedMeta {
		public String str;
		public int max;
		public LinkedMeta(String str, int max) {
			this.max = max;
			this.str = str;
		}
	}
	
	public static void registerIcon(String key, ItemStack stack) {
		icons.put(key, stack);
	}
	
	public static void registerOreDicIcon(String key, ItemStack stack) {
		icons.put(key, stack);
		oreDicCycling.add(key);
	}
	
	public static void registerCyclingMetaIcon(String key, ItemStack stack, int max) {
		icons.put(key, stack);
		metaCycling.add(new LinkedMeta(key, max));
	}
	
	public static void init() {		
		registerIcons();
		
		PageParser.parsers.put("crafting", new PageCrafting());
		PageParser.parsers.put("paragraph", new PageParagraph());
		PageParser.parsers.put("img", new PageImage());
	}
	
	public static void registerIcons() {
		/** Rotatables **/
		registerCyclingMetaIcon("wool", new ItemStack(Core.pearls, 1, PearlColor.COUNT), 16);
		registerCyclingMetaIcon("pearl", new ItemStack(Core.pearls, 1, PearlColor.COUNT), PearlColor.COUNT);
		registerOreDicIcon("plankWood", new ItemStack(Block.planks, 1, 1));
		registerOreDicIcon("ingotGold", new ItemStack(Item.ingotGold));
		registerOreDicIcon("ingotIron", new ItemStack(Item.ingotIron));
		registerOreDicIcon("ingotCopper", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER));
		registerOreDicIcon("ingotAluminum", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		registerOreDicIcon("stickWood", new ItemStack(Item.stick));
		
		registerIcon("reed", new ItemStack(Item.reed));
		registerIcon("blank", new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR));
		registerIcon("glassPane", new ItemStack(Block.thinGlass));
		registerIcon("vat", new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT));
		registerIcon("storage", new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF));
		registerIcon("enchant", new ItemStack(Block.enchantmentTable));
		registerIcon("netherStar", new ItemStack(Item.netherStar));
		registerIcon("goldenThread", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD));
		registerIcon("bookshelf", new ItemStack(Block.bookShelf));
		registerIcon("chest", new ItemStack(Block.chest));
		registerIcon("diamond", new ItemStack(Item.diamond));
		registerIcon("pearlRed", new ItemStack(Core.pearls, 1, PearlColor.RED));
		registerIcon("pearlWhite", new ItemStack(Core.pearls, 1, PearlColor.WHITE));
		registerIcon("pearlBlack", new ItemStack(Core.pearls, 1, PearlColor.BLACK));
		registerIcon("string", new ItemStack(Item.silk));
		registerIcon("goldenSilk", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK));
		registerIcon("goldenThread", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD));
		registerIcon("titaniumBattery", new ItemStack(Core.batteryTitanium));
		
		/** These are replaced when modules are added **/
		registerIcon("magicDroplet", new ItemStack(Item.ghastTear));
		/** Ignore replaceables **/
		
		if(Modules.fishery.isActive()) {
			registerIcon("rodReed", new ItemStack(Fishery.rodReed));
			registerIcon("rodWood", new ItemStack(Fishery.rodWood));
			registerIcon("rodTitanium", new ItemStack(Fishery.rodTitanium));
			registerIcon("rodRF", new ItemStack(Fishery.rodFlux));
			registerIcon("polishedTitanium", new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_TITANIUM));
			registerIcon("polishedStick", new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK));
			registerIcon("sifter", new ItemStack(Fishery.siftBlock, 1, 0));
			registerIcon("fishingNet", new ItemStack(Fishery.net));
			registerIcon("magicDroplet", new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC));
		}
		
		if(Modules.magic.isActive()) {
			registerIcon("basicMirror", new ItemStack(Magic.basicMirror));
			registerIcon("magicMirror", new ItemStack(Magic.magicMirror));
			registerIcon("celestialMirror", new ItemStack(Magic.celestialMirror));
			registerIcon("ringPearl", MaricultureRegistry.get("ring.pearlRed.gold"));
			registerIcon("braceletPearl", MaricultureRegistry.get("bracelet.pearlBlack.goldString"));
			registerIcon("necklacePearl", MaricultureRegistry.get("necklace.pearlWhite.wool"));
			registerIcon("ringIron", MaricultureRegistry.get("ring.diamond.iron"));
			registerIcon("braceletIron", MaricultureRegistry.get("bracelet.iron.string"));
			registerIcon("necklaceIron", MaricultureRegistry.get("necklace.iron.wool"));
		}
	}
	
	public static void updateIcons() {
		for(String str: oreDicCycling) {
			icons.put(str, OreDicHelper.getNextEntry(icons.get(str)));
		}
		
		for(LinkedMeta link: metaCycling) {
			ItemStack stack = icons.get(link.str);
			stack.setItemDamage(Rand.rand.nextInt(link.max));
			icons.put(link.str, stack);
		}
	}
	
	public static ItemStack getIcon(String key) {
		if(icons.get(key) == null)
			return new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR);
		if(icons.containsKey(key))
			return icons.get(key);
		if(key.contains(":")) {
			String[] vals = key.split("\\s*:\\s*");
			if(vals.length == 2) {
				ItemStack stack = new ItemStack(Integer.parseInt(vals[0]), 1, Integer.parseInt(vals[2]));
				icons.put(key, stack);
				return stack;
			}
		}
		
		if(OreDictionary.getOres(key).size() > 0) {
			ItemStack stack = OreDictionary.getOres(key).get(0);
			icons.put(key, stack);
			return stack;
		}
		
		return new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR);
	}
	
	public static NodeList fetch(String xml) {
		if(Extra.DEBUG_ON) {
			try {
				if(xml.equals("breeding") || xml.equals("diving") || xml.equals("enchants") || 
						xml.equals("fishing") || xml.equals("machine") || xml.equals("processing")) {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        	String lang = FMLClientHandler.instance().getCurrentLanguage();
		            InputStream stream = Mariculture.class.getResourceAsStream("/assets/mariculture/xml/" + xml + "_" + lang + ".xml");
		            if(stream == null)
		            	stream = Mariculture.class.getResourceAsStream("/assets/mariculture/xml/" + xml + "_en_US.xml");
		            DocumentBuilder dBuilder = factory.newDocumentBuilder();
		            Document doc = dBuilder.parse(stream);
		            doc.getDocumentElement().normalize();
		            return doc.getElementsByTagName("page");
				} else {
					return CompatBooks.getDocumentDebugMode(xml).getElementsByTagName("page");
				}
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		} else {
			return ClientProxy.getDocument(xml).getElementsByTagName("page");
		}
	}

	public static void draw(GuiGuide gui, Node node, int x, int y, boolean left) {
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
							parser.init(gui, left, x, y);
							parser.read(new XMLHelper((Element) n));
							GL11.glPushMatrix();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							parser.parse();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							GL11.glPopMatrix();
						}
					}
					
				}
			}
		}
		
		/*if(node == null)
			return;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			XMLHelper xml = new XMLHelper((Element) node);
			if(xml.e.getElementsByTagName("paragraph") != null)
				parseParagraphElement(xml, gui, x, y, left);
			if(xml.e.getElementsByTagName("crafting") != null)
				parseCraftingPage(xml, gui, x, y, left);
			if(xml.e.getElementsByTagName("img") != null)
				parseImgPage(xml, gui, x, y, left);
			if(xml.e.getElementsByTagName("stack") != null)
				parseStackPage(xml, gui, x, y, left);
			if(xml.e.getElementsByTagName("vat") != null)
				parseVatPage(xml, gui, x, y, left);
		} */
	}

	private static void parseImgPage(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		Element e = xml.e;
		NodeList list = e.getElementsByTagName("img");
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				parseImg(new XMLHelper((Element) node), gui, x, y, left);
			}
		}
	}
	
	private static void parseVatPage(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		Element e = xml.e;
		NodeList list = e.getElementsByTagName("crafting");
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				parseVat(new XMLHelper((Element) node), gui, x, y, left);
			}
		}
	}
	
	private static void parseParagraphElement(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		Element e = xml.e;
		NodeList list = e.getElementsByTagName("paragraph");
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				parseText(new XMLHelper((Element) node), gui, x, y, left);
			}
		}
	}
	
	private static void parseStackPage(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		Element e = xml.e;
		NodeList list = e.getElementsByTagName("stack");
		for(int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				parseStack(new XMLHelper((Element) node), gui, x, y, left);
			}
		}
	}
	
	private static void parseVat(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		
	}
	
	private static void parseImg(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		ResourceLocation src = new ResourceLocation("books" + ":" + xml.getAttribString("src") + ".png");
		float size =  xml.getHSize("size");
		int width = xml.getAttribInt("width");
		int height = xml.getAttribInt("height");
		x += xml.getOffset("x");
		y += xml.getOffset("y");
		x = x + ((left)? -8: 34);
		int xPos = (int) ((x / size) * 1F);
		gui.getMC().getTextureManager().bindTexture(src);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		GL11.glScalef(size, size, size);
		gui.drawTexturedModalRect(xPos, y, 0, 0, width, height);
		GL11.glPopMatrix();
	}
	
	private static void parseText(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		FontRenderer font = gui.getMC().fontRenderer;
		String heading = xml.getElementString("heading");
		String text = xml.getElementString("text");	

		//Heading >>
		int wrap = xml.getWrap("wrap");
		x += xml.getOffset("x");
		y += xml.getOffset("y");
		int hX = xml.getOffset("hX");
		int hY = xml.getHOffset("hY");
		float hSize = xml.getHSize("hSize");
		float tSize = xml.getTSize("tSize");
		int hXPos = (int) ((x / hSize) * 1F);
		int tXPos = (int) ((x / tSize) * 1F);
		
		int hBoost = (left)? 80: 48;
		int tBoost = (left)? 10: 40;
		
		if(!heading.equals("")) {
			//Parse the Heading
			GL11.glPushMatrix();
			GL11.glScalef(hSize, hSize, hSize);
			font.drawString(StringHelper.BOLD + heading, hXPos + hBoost + hX, y + hY, 4210752);
			GL11.glPopMatrix();
		}

		//Parse the Text
		GL11.glPushMatrix();
		GL11.glScalef(tSize, tSize, tSize);
		font.drawSplitString(text, tXPos + tBoost, y, wrap, 4210752);
		GL11.glPopMatrix();
	}
	
	private static void parseStack(XMLHelper xml, GuiGuide gui, int x, int y, boolean left) {
		x += xml.getOffset("x");
		y += xml.getOffset("y");
		float size =  xml.getCSize("size");
		x = x + ((left)? -8: 34);
		int cXPos = (int) ((x / size) * 1F);
		
		String stack = xml.getAttribString("item");
		GL11.glPushMatrix();
		GL11.glScalef(size, size, size);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
		drawItemStack(gui, (ItemStack) getIcon(stack), cXPos, y + 0);
		GL11.glPopMatrix();
	}
	
	private static void drawItemStack(GuiGuide gui, ItemStack stack, int x, int y) {
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
