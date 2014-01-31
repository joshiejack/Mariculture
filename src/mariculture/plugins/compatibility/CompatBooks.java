package mariculture.plugins.compatibility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.gui.GuiGuide;
import mariculture.core.guide.GuideHandler;
import mariculture.core.guide.Guides;
import mariculture.core.guide.XMLHelper;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Text;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CompatBooks {
	public static final ArrayList<String> onWorldStart = new ArrayList();
	public static final HashMap<String, BookInfo> books = new HashMap();
	public static class BookInfo {
		public String aColor;
		public String author, name;
		public Icon icon;
		public Integer color;
		public Document data;
		
		public BookInfo(String name, String author, String aColor, int color, Document data) {
			this.name = name;
			this.author = author;
			this.aColor = aColor;
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
					String id = filename.substring(0, filename.lastIndexOf('.'));
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder build = factory.newDocumentBuilder();
					Document doc = build.parse(file);
					doc.getDocumentElement().normalize();
					NodeList list = doc.getElementsByTagName("info");
					XMLHelper info = new XMLHelper((Element) list.item(0));
					String aColor = Text.getColor(info.getHelper("author").getOptionalAttribute("color"));
					String author =  info.getElement("author");
					String display = Text.getColor(info.getHelper("name").getOptionalAttribute("color")) + info.getElement("name");
					if (info.getAttribAsBoolean("gen"))
						onWorldStart.add(id);
					
					Integer color = info.getElementAsHex("color", 0xFFFFFF);
					
					books.put(id, new BookInfo(display, author, aColor, color, doc));
					
					NodeList nodes = doc.getElementsByTagName("register");
					for(int i = 0; i < nodes.getLength(); i++) {
						XMLHelper helper = new XMLHelper((Element) nodes.item(i));
						String type = helper.getAttribute("type");
						if(type.equals("item")) {
							String[] data = helper.getAttribute("data").split(":");
							String name = helper.getAttribute("name");
							if(data.length == 2) {
								ItemStack stack = new ItemStack(Integer.parseInt(data[0]), 1, Integer.parseInt(data[1]));
								Guides.instance.registerIcon(name, stack);
							} else if (data.length == 1) {
								if(!data[0].equals("")) {
									ItemStack stack = new ItemStack(Integer.parseInt(data[0]), 1, 0);
									Guides.instance.registerIcon(name, stack);
								}
							}
						} else if (type.equals("fluid")) {
							String data = helper.getAttribute("data");
							String name = helper.getAttribute("name");
							Guides.instance.registerFluidIcon(name, data);
						}
					}
					
					ItemStack item = GuideHandler.getIcon(info.getElement("item"));
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
			if(books.get(id) != null) {
				return books.get(id).name;
			}
		}
		
		return "Invalid Custom Book";
	}
	
	public static void addAuthor(ItemStack stack, List list) {
		if(stack.hasTagCompound()) {
			String id = stack.stackTagCompound.getString("booksid");
			if(books.get(id) != null) {
				list.add(books.get(id).aColor + StatCollector.translateToLocal("mariculture.string.by") + " " + books.get(id).author);
			}
		} else {
			list.add(StatCollector.translateToLocal("mariculture.string.by") + " " + "Unknown");
		}
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
	
	public static ItemStack generateBook(String id) {
		ItemStack stack = new ItemStack(Core.guides, 1, GuideMeta.CUSTOM);
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setString("booksid", id);
		return stack;
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
