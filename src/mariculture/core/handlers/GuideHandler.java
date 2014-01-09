package mariculture.core.handlers;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.core.Core;
import mariculture.core.gui.GuiGuide;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.Text;
import mariculture.core.util.FluidCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class GuideHandler {
	public static NodeList fetch(String xml) {
		String lang = FMLClientHandler.instance().getCurrentLanguage();
		System.out.println(lang);
		
		InputStream in = GuideHandler.class.getClass().getResourceAsStream("/assets/mariculture/xml/" + xml + "_" + lang + ".xml");
		if(in == null)
			in = GuideHandler.class.getClass().getResourceAsStream("/assets/mariculture/xml/" + xml + "_en_US.xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(in);
			return doc.getElementsByTagName("page");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void draw(GuiGuide gui, Node node, int x, int y) {
		if(node == null)
			return;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			XMLHelper xml = new XMLHelper((Element) node);
			String type = xml.toIdent("type");
			if(type.equals("text"))
				parseTextPage(xml, gui, x, y);
			if(type.equals("img"))
				parseImgPage(xml, gui, x, y);
		}
	}

	private static void parseImgPage(XMLHelper xml, GuiGuide gui, int x, int y) {
	}

	private static void parseTextPage(XMLHelper xml, GuiGuide gui, int x, int y) {
		FontRenderer font = gui.getMC().fontRenderer;
		String heading = xml.toString("heading");
		//Heading >>
		font.drawString("§o" + heading, x, y, 4210752);

		String text = xml.toString("text");	
		font.drawSplitString(text, x, y + 20, 180, 4210752);
	}
}
