package mariculture.plugins.compatibility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.api.fishery.Fishing;
import mariculture.core.Mariculture;
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
		File file = new XMLHelper("bait").get();
		
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
					String bait = xml.toString("bait");
					int meta = getMeta(bait);
					int val = getValue(bait);
					int blockID = xml.toInt("id");
					int blockMeta = xml.toInt("meta");
					int rarity = xml.toInt("rarity");
					int min = xml.toInt("min");
					int max = xml.toInt("max");
					
					Fishing.bait.addBait(new ItemStack(Fishery.bait, 1, meta), val, new Object[] {
						new ItemStack(blockID, 1, blockMeta), rarity, min, max });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
