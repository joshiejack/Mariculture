package mariculture.plugins.compatibility;

import java.io.File;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.Core;
import mariculture.core.RecipesSmelting;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.FluidCustom;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

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
		File file = new XMLHelper("fluids").get();
		
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
					String ident = xml.toString("identifier");
					String name = xml.toString("name");
					int id = (xml.toInt("blockTextureID") == -1)? Core.glassBlocks.blockID: xml.toInt("blockTextureID");
					int meta = (xml.toInt("blockTextureMeta") == -1)? GlassMeta.PLASTIC: xml.toInt("blockTextureMeta");
					
					FluidRegistry.registerFluid(new FluidCustom(ident, name, id, meta).setUnlocalizedName(name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addRecipes() {
		File file = new XMLHelper("fluids").get();
		
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
					String type = xml.toIdent("type");
					String fluid = xml.toString("fluid");
					int temperature = xml.toInt("temp");
					
					if(type.equals("metal")) {
						String name = xml.toString("metal");
						int bonusID = xml.toInt("bonusID");
						int bonusMeta = xml.toInt("bonusMeta");
						int bonusChance = xml.toInt("bonusChance");
						RecipesSmelting.addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
								"ore" + name, "nugget" + name, "ingot" + name, "block" + name, "dust" + name }, temperature, 
								new ItemStack(bonusID, 1, bonusMeta), bonusChance);
					} else if (type.equals("other")) {
						int volume = xml.toInt("volume");
						int id = xml.toInt("id");
						int meta = xml.toInt("meta");
						
						MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(id, 1, meta), temperature, 
								new SmelterOutput(FluidRegistry.getFluidStack(fluid, volume), null, 0)));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
