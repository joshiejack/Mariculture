package enchiridion;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import enchiridion.CustomBooks.BookInfo;
import enchiridion.api.GuiGuide;
import enchiridion.api.GuideHandler;
import enchiridion.api.IBookReader;

public class ClientProxy extends CommonProxy implements IBookReader {
	public static KeyBinding open_binder;
	private static DocumentBuilderFactory factory;
	public static final HashMap<String, Document> bookCache = new HashMap();
	
	//At Preinit register the key binding
	//At Init put all the custom books in to the cache, at PostInit, put all the modded books in to the cache
	// At PostInit process all the custom book registrations
	
	@Override
	public void preInit() {
		open_binder = new KeyBinding("key.binder", Keyboard.KEY_APOSTROPHE, "key.categories.gameplay");
		ClientRegistry.registerKeyBinding(open_binder);
	}
	
	@Override
	public void init() {
		CustomBooks.preInit();
	}
	
	@Override
	public void postInit() {
		factory = DocumentBuilderFactory.newInstance();
		for (Entry<String, String> data : GuideHandler.getGuides().entrySet()) {
			String guide = data.getValue();
			if(guide.contains(":")) {
				String[] arr = guide.split(":",-1);
				bookCache.put(guide, initModGuide(arr[0], arr[1]));
			}
		}
		
		//Now that we have the images and the documents in the cache registered we need to go through them all and 'register' their info
		//Since mod books have their own lore, images etc, we will asume any books without the book info is not a custom book
		for (Entry<String, Document> docs : bookCache.entrySet()) {
			if(docs.getValue() == null || docs.getKey() == null) continue;
			if(docs.getValue().getElementsByTagName("info").getLength() > 0) {
				CustomBooks.setup(docs.getKey(), (Element) docs.getValue().getElementsByTagName("info").item(0));
			}
			
			GuideHandler.registerModBook(new ItemStack(Enchiridion.items, 1, ItemEnchiridion.GUIDE), docs.getKey());
			GuideHandler.registerBookReader(docs.getKey(), this);
		}
	}

	private static Document initModGuide(String mod, String book) {
		try {
			String lang = FMLClientHandler.instance().getCurrentLanguage();
			InputStream stream = Enchiridion.class.getResourceAsStream("/assets/" + mod + "/books/" + book + "_" + lang + ".xml");
			if(stream == null) stream = Enchiridion.class.getResourceAsStream("/assets/" + mod + "/books/" + book + "_en_US.xml");
			DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(stream);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//Custom book based BookReader
	@Override
	public Document getDocument(String xml) {
		return bookCache.get(xml);
	}

	@Override
	public Document getDocumentDebugMode(String xml) {
		if(xml.contains(":")) {
			String[] arr = xml.split(":",-1);
			return initModGuide(arr[0], arr[1]);
		} else {
			return CustomBooks.getDebugMode(xml);
		}
	}
	
	@Override
	public Object getGui(ItemStack stack, String key) {		
		if(GuideHandler.getGuis().containsKey(key)) return GuideHandler.getGuis().get(key);
		else if(stack.hasTagCompound()) {
			String id = CustomBooks.getID(stack);
			BookInfo info = CustomBooks.bookInfo.get(id);
			return new GuiGuide(info.bookColor, info.background, id);
		} else return null;
	}
}