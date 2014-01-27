package mariculture.plugins.compatibility;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.gui.GuiGuide;
import mariculture.core.handlers.GuideHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.XMLHelper;
import mariculture.core.lib.GuideMeta;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class CompatBooks {
	public static final HashMap<String, BookInfo> books = new HashMap();
	public static class BookInfo {
		public String name;
		public Icon icon;
		public Integer color;
		public Document data;
		
		public BookInfo(String name, int color, Document data) {
			this.name = name;
			this.color = color;
			this.data = data;
		}
		
		public void setIcon(Icon icon) {
			this.icon = icon;
		}
	}
	
	public static void init() {
		String root = Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + "\\assets\\books";
		File folder = new File(root);
		for (File file : folder.listFiles()) {
			String filename = file.getName();
			String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			if(extension.equals("xml")) {
				try {		            
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder build = factory.newDocumentBuilder();
					Document doc = build.parse(file);
					doc.getDocumentElement().normalize();
					NodeList list = doc.getElementsByTagName("info");
					XMLHelper info = new XMLHelper((Element) list.item(0));
					String display = info.getElementString("name");
					Integer color = info.getHexCode("color");
					String id = filename.substring(0, filename.lastIndexOf('.'));
					books.put(id, new BookInfo(display, color, doc));
					
					NodeList nodes = doc.getElementsByTagName("register");
					for(int i = 0; i < nodes.getLength(); i++) {
						XMLHelper helper = new XMLHelper((Element) nodes.item(i));
						String[] data = helper.getAttribString("data").split(":");
						String name = helper.getAttribString("name");
						if(data.length == 2) {
							ItemStack stack = new ItemStack(Integer.parseInt(data[0]), 1, Integer.parseInt(data[1]));
							GuideHandler.registerIcon(name, stack);
						} else if (data.length == 1) {
							if(!data[0].equals("")) {
								ItemStack stack = new ItemStack(Integer.parseInt(data[0]), 1, 0);
								GuideHandler.registerIcon(name, stack);
							}
						}
					}
					
					ItemStack item = GuideHandler.getIcon(info.getElementString("item"));
					ItemStack stack = new ItemStack(Core.guides.itemID, 1, GuideMeta.CUSTOM);
					stack.setTagCompound(new NBTTagCompound());
					stack.stackTagCompound.setString("booksid", id);
					RecipeHelper.addShapelessRecipe(stack, new Object[] { Item.book, item });
				} catch (Exception e) {
					e.printStackTrace();
					LogHandler.log(Level.WARNING, "Mariculture failed to read a books content in the books config folder");
				}
			}
	    } 
	}

	public static Document getDocument(String xml) {
		return books.get(xml).data;
	}

	public static String getName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			String id = stack.stackTagCompound.getString("booksid");
			if(books.get(id) != null)
				return books.get(id).name;
		}
		
		return "Invalid Custom Book";
	}
	
	public static int getColor(ItemStack stack) {
		if(stack.hasTagCompound()) {
			String id = stack.stackTagCompound.getString("booksid");
			if(books.get(id) != null)
				return books.get(id).color;
		}
		
		return 0;
	}
	
	public static Object getGUI(ItemStack stack) {
		if(stack.hasTagCompound()) {
			String id = stack.stackTagCompound.getString("booksid");
			if(books.get(id) != null)
				return new GuiGuide(books.get(id).color, id);
		}
		
		return null;
	}

	public static void addBooks(int id, CreativeTabs creative, List list) {
		for (Entry<String, BookInfo> book : books.entrySet()) {
			ItemStack stack = new ItemStack(id, 1, GuideMeta.CUSTOM);
			stack.setTagCompound(new NBTTagCompound());
			stack.stackTagCompound.setString("booksid", book.getKey());
			list.add(stack);
		}
	}

	public static Document getDocumentDebugMode(String xml) {
		File file = new File(Mariculture.root + "/mariculture/books/" + xml + ".xml");
		String filename = file.getName();
		try {		            
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			LogHandler.log(Level.WARNING, "Mariculture failed to read a books content in the books config folder");
		}
		
		return null;
	}
}
