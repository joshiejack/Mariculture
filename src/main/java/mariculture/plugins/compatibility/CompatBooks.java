package mariculture.plugins.compatibility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.guide.GuiGuide;
import mariculture.core.guide.GuideHandler;
import mariculture.core.guide.Guides;
import mariculture.core.guide.PageImage.LinkedTexture;
import mariculture.core.guide.XMLHelper;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Text;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cpw.mods.fml.client.FMLClientHandler;

public class CompatBooks {
	public static final ArrayList<String> onWorldStart = new ArrayList();
	public static final HashMap<String, BookInfo> books = new HashMap();
	public static class BookInfo {
		public String aColor;
		public String author, name;
		public Icon icon;
		public Integer color;
		
		public BookInfo(String name, String author, String aColor, int color) {
			this.name = name;
			this.author = author;
			this.aColor = aColor;
			this.color = color;
		}
		
		public void setIcon(Icon icon) {
			this.icon = icon;
		}
	}
	
	public static final HashMap<String, Document> cache = new HashMap();
	public static final HashMap<String, LinkedTexture> imageCache = new HashMap();
	
	public static void preInit() {
		//We need to create the books config folder if it does not exist
		File folder = new File((Mariculture.root + "/books/").replace('/', File.separatorChar));
		if(!folder.exists()){
			folder.mkdir();
		}
		
		//Loop through all the files in the books folder
		for(File file: folder.listFiles()) {
			String zipName = file.getName();
			//Continue if the file i a zip
			if(zipName.substring(zipName.lastIndexOf(".") + 1, zipName.length()).equals("zip")) {
				LogHandler.log(Level.FINE, "Attempting to read data for the installed Guide Book: " + zipName);
				try {
					ZipFile zipfile = new ZipFile(file);
					Enumeration enumeration = zipfile.entries();
					while (enumeration.hasMoreElements()) {
						ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
						String fileName = zipentry.getName();
			    		String extension = fileName.substring(fileName.length() - 3, fileName.length());
			    		if(!zipentry.isDirectory()) {
			    			if(FMLClientHandler.instance()!= null && extension.equals("png")) {
			    				try {
				    				String id = fileName.substring(0, fileName.lastIndexOf('.'));
					    			BufferedImage img = ImageIO.read(zipfile.getInputStream(zipentry));
					    			DynamicTexture dmTexture = new DynamicTexture(img);
					    			ResourceLocation texture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(id, dmTexture);
					    			LinkedTexture linked = new LinkedTexture(img.getHeight(), img.getWidth(), dmTexture, texture);
					    			String identifier = (zipName.substring(0, zipName.length() - 4) + "|" + fileName.substring(0, fileName.length() - 4));
					    			imageCache.put(identifier, linked); 
			    				} catch (Exception e) {
			    					LogHandler.log(Level.WARNING, "Failed to Read Image Data of " + fileName);
			    				}
			    			} else if(extension.equals("xml")) {
				    			try {
				    				//Saving the Document Data to the cache, ready for them to be read on init
				    				String id = fileName.substring(0, fileName.lastIndexOf('.'));
				    		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				    				DocumentBuilder build = factory.newDocumentBuilder();
				    				Document doc = build.parse(zipfile.getInputStream(zipentry));
				    				doc.getDocumentElement().normalize();
				    				cache.put(id, doc);
				    			} catch (Exception e) {
				    				e.printStackTrace();
				    				LogHandler.log(Level.WARNING, "Failed to Read XML Data of " + fileName);
				    			}
				    		}
			    		}
			        }
					
					zipfile.close();
					LogHandler.log(Level.FINE, "Sucessfully finished reading the installed Guide Book: " + zipName);
				} catch (Exception e) {
					LogHandler.log(Level.WARNING, "Failed to read the installed Guide Book: " + zipName);
				}
			}
		}
		
		//Loading the xml on startup from the debug folder
		if(Extra.DEBUG_ON) {
			File debugFolder = new File(Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + 
					File.separator  + "config" + File.separator + "books" + File.separator + "debug");
			
			if(!debugFolder.exists()){
				debugFolder.mkdir();
			}
			
			for(File file: debugFolder.listFiles()) {
				String xmlName = file.getName();
				if(xmlName.substring(xmlName.lastIndexOf(".") + 1, xmlName.length()).equals("xml")) {
					try {
							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    				DocumentBuilder build = factory.newDocumentBuilder();
		    				Document doc = build.parse(file);
		    				doc.getDocumentElement().normalize();
		    				cache.put(xmlName.substring(0, xmlName.lastIndexOf('.')), doc);
		    				LogHandler.log(Level.WARNING, "Sucessfully loaded debug mode custom book xml " + xmlName);
					} catch(Exception e) {
						LogHandler.log(Level.WARNING, "Failed to load debug mode custom book xml " + xmlName);
					}
				}
			}
		}
	}
	
	public static void init() {
		//Now that we have the images loaded in to the cache AND we have the Documents in the Cache, It is Startup Time, AKA it's time to read the book data
		for (Entry<String, Document> docs : cache.entrySet()) {
			Document doc = docs.getValue();
			
			NodeList registrations = doc.getElementsByTagName("register");
			for(int i = 0; i < registrations.getLength(); i++) {
				parseRegistrations(new XMLHelper((Element) registrations.item(i)));
			}
			
			parseBookInfo(docs.getKey(), new XMLHelper((Element) doc.getElementsByTagName("info").item(0)));
		}
	}

	//Books Information, Whether you get it on spawn, author, name, color etc.
	public static void parseBookInfo(String id, XMLHelper info) {
		String aColor = Text.getColor(info.getHelper("author").getOptionalAttribute("color"));
		String author =  info.getElement("author");
		String display = Text.getColor(info.getHelper("name").getOptionalAttribute("color")) + info.getElement("name");
		if (info.getAttribAsBoolean("gen"))
			onWorldStart.add(id);
		
		Integer color = info.getElementAsHex("color", 0xFFFFFF);
		books.put(id, new BookInfo(display, author, aColor, color));
		
		ItemStack item = GuideHandler.getIcon(info.getElement("item"));
		ItemStack stack = new ItemStack(Core.guides, 1, GuideMeta.CUSTOM);
		stack.setTagCompound(new NBTTagCompound());
		stack.stackTagCompound.setString("booksid", id);
		RecipeHelper.addShapelessRecipe(stack, new Object[] { Items.book, item });
	}
	
	private static void parseRegistrations(XMLHelper helper) {
		String type = helper.getAttribute("type");
		if(type.equals("item")) {
			ItemStack stack = null;
			String data = helper.getAttribute("data");
			String name = helper.getAttribute("name");
			if(Item.itemRegistry.getObject(name) != null) 
				stack = new ItemStack((Item)Item.itemRegistry.getObject(name), 1, 0);
			else if(Block.blockRegistry.getObject(name) != null)
				stack = new ItemStack((Block)Block.blockRegistry.getObject(name));
			if(stack != null)
				Guides.instance.registerIcon(name, stack);
		} else if (type.equals("fluid")) {
			String data = helper.getAttribute("data");
			String name = helper.getAttribute("name");
			Guides.instance.registerFluidIcon(name, data);
		}
	}

	public static Document getDocument(String xml) {
		return cache.get(xml);
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

	public static void addBooks(Item item, CreativeTabs creative, List list) {
		for (Entry<String, BookInfo> book : books.entrySet()) {
			ItemStack stack = new ItemStack(item, 1, GuideMeta.CUSTOM);
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
		String file = Mariculture.root.getAbsolutePath().substring(0, Mariculture.root.getAbsolutePath().length() - 7) + 
				File.separator  + "config" + File.separator + "books" + File.separator + "debug" + File.separator + xml + ".xml";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = factory.newDocumentBuilder();
			Document doc = build.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception e) {
			return null;
		}
	}
}
