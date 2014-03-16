package mariculture.plugins.compatibility;

import java.io.File;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.RecipesSmelting;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.util.FluidCustom;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CompatFluids {

	public static void init() {
		try {
			addCustomFluids();
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Mariculture - Something went wrong with adding Custom Fluids");
		}
		
		try {
			addRecipes();
		} catch (Exception e) {
			e.printStackTrace();
			LogHandler.log(Level.INFO, "Mariculture - Something went wrong with adding Fluid Recipes");
		}
	}
	
	public static void addCustomFluids() {
		File file = new File(Mariculture.root + "/mariculture/fluids.xml");
		if(!file.exists()) {
			file = createFluidXML();
		}
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(file);
			doc.getDocumentElement().normalize();
			NodeList node = doc.getElementsByTagName("register");
			for (int temp = 0; temp < node.getLength(); temp++) {
				Node nNode = node.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					XMLHelper xml = new XMLHelper((Element) nNode);
					String ident = xml.getElement("identifier");
					String name = xml.getElement("name");
					int id = (xml.getElementAsInteger("blockTextureID", -1) == -1)? Core.transparentBlocks.blockID: xml.getElementAsInteger("blockTextureID", -1);
					int meta = (xml.getElementAsInteger("blockTextureMeta", -1) == -1)? TransparentMeta.PLASTIC: xml.getElementAsInteger("blockTextureMeta", -1);
					
					FluidRegistry.registerFluid(new FluidCustom(ident, name, id, meta).setUnlocalizedName(name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addRecipes() {
		File file = new File(Mariculture.root + "/mariculture/fluids.xml");
		if(!file.exists()) {
			file = createFluidXML();
		}
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(file);
			doc.getDocumentElement().normalize();
			NodeList node = doc.getElementsByTagName("melting");
			for (int temp = 0; temp < node.getLength(); temp++) {
				Node nNode = node.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					XMLHelper xml = new XMLHelper((Element) nNode);
					String type = xml.getAttribute("type");
					String fluid = xml.getElement("fluid");
					int temperature = xml.getElementAsInteger("temp", 100);
					
					if(type.equals("metal")) {
						String name = xml.getElement("metal");
						int bonusID = xml.getElementAsInteger("bonusID", 1);
						int bonusMeta = xml.getElementAsInteger("bonusMeta", 0);
						int bonusChance = xml.getElementAsInteger("bonusChance", 2);
						RecipesSmelting.addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
								"ore" + name, "nugget" + name, "ingot" + name, "block" + name, "dust" + name }, temperature, 
								new ItemStack(bonusID, 1, bonusMeta), bonusChance);
						RecipeHelper.addIngotCasting(fluid, name);
					} else if (type.equals("other")) {
						int volume = xml.getElementAsInteger("volume", 1000);
						int id = xml.getElementAsInteger("id", 1);
						int meta = xml.getElementAsInteger("meta", 0);
						
						RecipeHelper.addMelting(new ItemStack(id, 1, meta), temperature, FluidRegistry.getFluidStack(fluid, volume));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static File createFluidXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element rootElement = document.createElement("fluids");
			document.appendChild(rootElement);

			addRegistration(document, rootElement, "molten.dirt", "Molten Dirt", 3, 0);
			addGenericMelting(document, rootElement, "molten.dirt", 1, 333, 3, 0);
			addMetalMelting(document, rootElement, "ardite.molten", 2000, "Ardite", 87, 0, 2);
			addMetalMelting(document, rootElement, "cobalt.molten", 1495, "Cobalt", 87, 0, 2);
			addMetalMelting(document, rootElement, "manyullyn.molten", 2000, "Manyullyn", 87, 0, 2);
			addMetalMelting(document, rootElement, "aluminumbrass.molten", 940, "AluminumBrass", 1, 0, 2);
			addMetalMelting(document, rootElement, "alumite.molten", 1800, "Alumite", 1, 0, 2);
			addMetalMelting(document, rootElement, "platinum.molten", 1768, "Platinum", 1, 0, 2);
			addMetalMelting(document, rootElement, "invar.molten", 921, "Invar", 1, 0, 2);
			addMetalMelting(document, rootElement, "electrum.molten", 1000, "Electrum", 1, 0, 2);
			
			TransformerFactory t = TransformerFactory.newInstance();
			Transformer transformer = t.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(Mariculture.root + "/mariculture/fluids.xml"));
			transformer.transform(domSource, streamResult);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		
		return new File(Mariculture.root + "/mariculture/fluids.xml");
	}

	private static void addGenericMelting(Document document, Element element, String fluid, int temp, int vol, int id, int meta) {
		Element melting = document.createElement("melting");
		element.appendChild(melting);
		Attr attribute = document.createAttribute("type");  
		attribute.setValue("other");  
		melting.setAttributeNode(attribute);
		Element eIdent = document.createElement("fluid");
		eIdent.appendChild(document.createTextNode(fluid));
		melting.appendChild(eIdent);
		Element eTemp = document.createElement("temp");
		eTemp.appendChild(document.createTextNode("" + temp));
		melting.appendChild(eTemp);
		Element eVolume = document.createElement("volume");
		eVolume.appendChild(document.createTextNode("" + vol));
		melting.appendChild(eVolume);
		Element eId = document.createElement("id");
		eId.appendChild(document.createTextNode("" + id));
		melting.appendChild(eId);
		Element eMeta = document.createElement("meta");
		eMeta.appendChild(document.createTextNode("" + meta));
		melting.appendChild(eMeta);
	}
	
	private static void addMetalMelting(Document document, Element element, String fluid, int temp, String metal, int id, int meta, int chance) {
		Element melting = document.createElement("melting");
		element.appendChild(melting);
		Attr attribute = document.createAttribute("type");  
		attribute.setValue("metal");  
		melting.setAttributeNode(attribute);
		Element eIdent = document.createElement("fluid");
		eIdent.appendChild(document.createTextNode(fluid));
		melting.appendChild(eIdent);
		Element eTemp = document.createElement("temp");
		eTemp.appendChild(document.createTextNode("" + temp));
		melting.appendChild(eTemp);
		Element eMetal = document.createElement("metal");
		eMetal.appendChild(document.createTextNode(metal));
		melting.appendChild(eMetal);
		Element eId = document.createElement("bonusID");
		eId.appendChild(document.createTextNode("" + id));
		melting.appendChild(eId);
		Element eMeta = document.createElement("bonusMeta");
		eMeta.appendChild(document.createTextNode("" + meta));
		melting.appendChild(eMeta);
		Element eChance = document.createElement("bonusChance");
		eChance.appendChild(document.createTextNode("" + chance));
		melting.appendChild(eChance);
	}

	private static void addRegistration(Document document, Element element, String ident, String name, int id, int meta) {
		Element register = document.createElement("register");
		element.appendChild(register);
		Element eIdent = document.createElement("identifier");
		eIdent.appendChild(document.createTextNode(ident));
		register.appendChild(eIdent);
		Element eName = document.createElement("name");
		eName.appendChild(document.createTextNode(name));
		register.appendChild(eName);
		Element eId = document.createElement("blockTextureID");
		eId.appendChild(document.createTextNode("" + id));
		register.appendChild(eId);
		Element eMeta = document.createElement("blockTextureMeta");
		eMeta.appendChild(document.createTextNode("" + meta));
		register.appendChild(eMeta);
	}
}
