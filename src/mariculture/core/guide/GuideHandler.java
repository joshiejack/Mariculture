package mariculture.core.guide;

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
import mariculture.core.helpers.OreDicHelper;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.opengl.GL11;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class GuideHandler implements IGuideHandler {
	private static final ResourceLocation elements = new ResourceLocation("mariculture", "textures/gui/guide_elements.png");
	public static final HashMap<String, ItemStack> icons = new HashMap();
	public static final ArrayList<LinkedMeta> metaCycling = new ArrayList<LinkedMeta>();
	public static final ArrayList<String> oreDicCycling = new ArrayList<String>();
	public static final HashMap<String, String> fluids = new HashMap();
	
	public static class LinkedMeta {
		public String str;
		public int max;
		public LinkedMeta(String str, int max) {
			this.max = max;
			this.str = str;
		}
	}
	
	public void registerPageHandler(String xml, PageParser page) {
		PageParser.parsers.put(xml, page);
	}
	
	public void registerIcon(String key, ItemStack stack) {
		icons.put(key, stack);
	}
	
	public void registerOreDicIcon(String key, ItemStack stack) {
		icons.put(key, stack);
		oreDicCycling.add(key);
	}
	
	public void registerCyclingMetaIcon(String key, ItemStack stack, int max) {
		icons.put(key, stack);
		metaCycling.add(new LinkedMeta(key, max));
	}
	
	public void registerFluidIcon(String key, String fluid) {
		fluids.put(key, fluid);
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
	
	public static String getFluidIcon(String key) {
		return fluids.get(key);
	}
	
	public static NodeList fetch(String xml) {
		if(Extra.DEBUG_ON) {
			try {
				if(xml.equals("breeding") || xml.equals("diving") || xml.equals("enchants") || 
						xml.equals("fishing") || xml.equals("machines") || xml.equals("processing")) {
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
							parser.init(gui, x, y, left);
							parser.read(new XMLHelper((Element) n));
							GL11.glPushMatrix();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							parser.resize(new XMLHelper((Element) n));
							parser.parse();
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
							GL11.glPopMatrix();
						}
					}
				}
			}
		}
	}
}
