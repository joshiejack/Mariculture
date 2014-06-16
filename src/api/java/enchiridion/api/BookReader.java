package enchiridion.api;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.item.ItemStack;

import org.w3c.dom.Document;

import cpw.mods.fml.client.FMLClientHandler;

public class BookReader implements IBookReader {
	//This is the standard book reader, I'd recommend you add your documents to the cache before minecraft starts, rather than having this load them.
	//Simply just call getDocument(); to add to the cache
	private static final HashMap<String, Document> cache = new HashMap();
	public static DocumentBuilderFactory factory;
	public BookReader() {
		factory = DocumentBuilderFactory.newInstance();
	}
	
	public Document getDocument(String xml) {
		if(cache.get(xml) != null) return cache.get(xml);
		else {
			Document doc = getDocumentDebugMode(xml);
			cache.put(xml, doc);
			return doc;
		}
	}

	public Document getDocumentDebugMode(String xml) {
		try {
			String lang = FMLClientHandler.instance().getCurrentLanguage();
			String[] arr = xml.split(":", -1);
			InputStream stream = BookReader.class.getResourceAsStream("/assets/" + arr[0] + "/books/" + arr[1] + "_" + lang + ".xml");
			if(stream == null) stream = BookReader.class.getResourceAsStream("/assets/" + arr[0] + "/books/" + arr[1] + "_en_US.xml");
			DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(stream);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object getGui(ItemStack stack, String key) {
		if(GuideHandler.getGuis().containsKey(key)) return GuideHandler.getGuis().get(key);
		else return null;
	}
}
