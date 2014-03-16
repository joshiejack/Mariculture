package mariculture.plugins.compatibility;

import java.io.File;

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
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.lib.BaitMeta;
import mariculture.fishery.Fishery;
import net.minecraft.item.ItemStack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CompatBait {

	public static void init() {
		File file = new File(Mariculture.root + "/mariculture/sifter.xml");
		if(!file.exists()) {
			file = createBaitXML();
		}
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(file);
			doc.getDocumentElement().normalize();
			NodeList node = doc.getElementsByTagName("block");
			for (int temp = 0; temp < node.getLength(); temp++) {
				Node nNode = node.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					XMLHelper xml = new XMLHelper((Element) nNode);
					String bait = xml.getElement("bait");
					int meta = getMeta(bait);
					int val = getValue(bait);
					int blockID = xml.getElementAsInteger("id", 1);
					int blockMeta = xml.getElementAsInteger("meta", 0);
					int rarity = xml.getElementAsInteger("rarity", 15);
					int min = xml.getElementAsInteger("min", 1);
					int max = xml.getElementAsInteger("max", 2);
					
					Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(Fishery.bait, 1, meta), 
												new ItemStack(blockID, 1, blockMeta), min, max, rarity));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File createBaitXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element rootElement = document.createElement("blocks");
			document.appendChild(rootElement);

			addBlock(document, rootElement, "grasshopper", 106, 0, 1, 1, 10);
			addBlock(document, rootElement, "grasshopper", 31, 2, 1, 2, 15);
			addBlock(document, rootElement, "maggot", 397, 2, 5, 15, 80);
			
			TransformerFactory t = TransformerFactory.newInstance();
			Transformer transformer = t.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(Mariculture.root + "/mariculture/sifter.xml"));
			transformer.transform(domSource, streamResult);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		
		return new File(Mariculture.root + "/mariculture/sifter.xml");
	}
	
	public static void addBlock(Document document, Element element, String bait, int id, int meta, int min, int max, int rarity) {
		Element block = document.createElement("block");
		element.appendChild(block);
		Element eBait = document.createElement("bait");
		eBait.appendChild(document.createTextNode(bait));
		block.appendChild(eBait);
		Element eId = document.createElement("id");
		eId.appendChild(document.createTextNode("" + id));
		block.appendChild(eId);
		Element eMeta = document.createElement("meta");
		eMeta.appendChild(document.createTextNode("" + meta));
		block.appendChild(eMeta);
		Element eMin = document.createElement("min");
		eMin.appendChild(document.createTextNode("" + min));
		block.appendChild(eMin);
		Element eMax = document.createElement("max");
		eMax.appendChild(document.createTextNode("" + max));
		block.appendChild(eMax);
		Element eRarity = document.createElement("rarity");
		eRarity.appendChild(document.createTextNode("" + rarity));
		block.appendChild(eRarity);
	}

	private static int getMeta(String name) {
		int ret = 0;
		if(name.equals("ant")) {
			ret = BaitMeta.ANT;
		} else if(name.equals("maggot")) {
			ret = BaitMeta.MAGGOT;
		} else if(name.equals("grasshopper")) {
			ret = BaitMeta.HOPPER;
		} else if(name.equals("worm")) {
			ret = BaitMeta.WORM;
		}
		
		return ret;
	}
	
	private static int getValue(String name) {
		int ret = 0;
		if(name.equals("maggot")) {
			ret = 2;
		} else if(name.equals("grasshopper")) {
			ret = 2;
		} else if(name.equals("worm")) {
			ret = 3;
		}
		
		return ret;
	}
}
