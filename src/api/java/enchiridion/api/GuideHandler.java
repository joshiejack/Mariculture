package enchiridion.api;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.w3c.dom.NodeList;

import cpw.mods.fml.common.FMLLog;
import enchiridion.api.pages.PageCrafting;
import enchiridion.api.pages.PageImage;
import enchiridion.api.pages.PageParagraph;
import enchiridion.api.pages.PageParser;
import enchiridion.api.pages.PageStack;
import enchiridion.api.pages.PageText;
import enchiridion.api.pages.PageUnderline;

public class GuideHandler {
	public static boolean DEBUG_ENABLED;
	
	/** Registries **/
	private static final HashMap<String, String> guides = new HashMap();
	private static final HashMap<String, IBookReader> handlers = new HashMap();
	private static final HashMap<String, Object> guis = new HashMap();
	
	public static final Random rand = new Random();
	
	static {
		registerBookReader("DEFAULT", new BookReader());
		registerPageHandler("crafting", new PageCrafting());
		registerPageHandler("paragraph", new PageParagraph());
		registerPageHandler("img", new PageImage());
		registerPageHandler("stack", new PageStack());
		registerPageHandler("hr", new PageUnderline());
		registerPageHandler("text", new PageText());
	}
	
	/** returns a list of all the registered guide string **/
	public static HashMap<String, String> getGuides() {
		return guides;
	}
	
	/** returns a list of all the registered Guis **/
	public static HashMap<String, Object> getGuis() {
		return guis;
	}
	
	/** This is for handling the readers for getting the documents, if  you have them in special locations
	 * 
	 * @param xml - The book identifier this handler is registered to
	 * @param reader - The reader itself
	 */
	public static void registerBookReader(String xml, IBookReader reader) {
		if(handlers.containsKey(xml)) {
			FMLLog.getLogger().log(Level.WARN, "[Enchiridion]Overwriting the handler for " + xml);
		}
		
		handlers.put(xml, reader);
	}
	
	//JUST the book registry, can mostly be ignored, just used by my custom book loader
	public static void registerModBook(ItemStack stack, String modAssets) {
		String key = Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		guides.put(key, modAssets);
	}
	
	//Use this if you don't want a custom background image, uses the default book cover instead
	public static void registerBook(ItemStack stack, String modAssets, String book, Integer color) {
		String key = Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		guides.put(key, modAssets +  ":" + book);
		guis.put(key, new GuiGuide(color, modAssets + ":" + book));
	}
	
	/**
	 * @param stack - The ItemStack of the book
	 * @param modAssets - The mod assets folder e.g. 'mariculture'
	 * @param book - The book name
	 * @param background - The background image path the gui should use
	 * @param color - The color of the books border
	 */
	public static void registerBook(ItemStack stack, String modAssets, String book, String background, Integer color) {
		String key = Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		guides.put(key, modAssets +  ":" + book);
		guis.put(key, new GuiGuide(color, modAssets + ":" + book));
		GuiGuide.cover_left_cache.put(key, new ResourceLocation(modAssets, background));
		GuiGuide.cover_right_cache.put(key, new ResourceLocation(modAssets, background));
		GuiGuide.page_left_cache.put(key, new ResourceLocation(modAssets, background));
		GuiGuide.page_right_cache.put(key, new ResourceLocation(modAssets, background));
	}
	
	/** This is for registering new page handlers, so you can add various other element types 
	 * 
	 * @param xml - The identifier for when searching through the xml, e.g. image tags are 'img' and crafting are 'crafting'
	 * @param handler
	 */
	public static void registerPageHandler(String xml, PageParser handler) {
		PageParser.registerHandler(xml, handler);
	}
	
	/** This is an example on right click method, to be called from your item */
	/*public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.worldObj.isRemote) {
			player.openGui(Mariculture.instance, 0, player.worldObj, 0, 0, 0);
		}
	
		return stack;
	} */

	/** This calls the book readers and is what you should return for your book in your CommonProxy, use the players current held item e.g. 
	 *	return GuideHandler.getGui(player.getCurrentEquippedItem());
	 * @param The currently held item
	 * @return some sort of instance of GuiGuide */
	public static Object getGui(ItemStack stack) {
		String key = Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
		if(handlers.get(guides.get(key)) != null) return handlers.get(guides.get(key)).getGui(stack, key);
		else {
			FMLLog.getLogger().log(Level.WARN, "The book you are currently trying to open, has errored and probably has an xml error");
			return null;
		}
	}
	
	public static NodeList getDocument(String xml) {
		if(DEBUG_ENABLED) {
			try {
				if(handlers.get(xml) == null) handlers.put(xml, handlers.get("DEFAULT"));
				return handlers.get(xml).getDocumentDebugMode(xml).getElementsByTagName("page");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		} else {
			if(handlers.get(xml) == null) handlers.put(xml, handlers.get("DEFAULT"));
			return handlers.get(xml).getDocument(xml).getElementsByTagName("page");
		}
	}
}
