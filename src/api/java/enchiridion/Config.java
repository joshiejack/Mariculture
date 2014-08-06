package enchiridion;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import enchiridion.api.GuiGuide;
import enchiridion.api.GuideHandler;
import enchiridion.api.StackHelper;

public class Config {
	public static boolean spawn_binder;
	public static final ItemStack[] preload_books = new ItemStack[21];
	public static final ArrayList<String> additions = new ArrayList();
	public static final ArrayList<String> removals = new ArrayList();
	public static final String[] prefix_dft_good = new String[] { "guide", "manual", "pedia", "lexicon", "Thaumonomicon", "mudora", "book", "routing.table", "compendium" };
	public static final String[] prefix_dft_bad = new String[] { "minecraft:bookshelf" };
	public static boolean display_nbt;
	public static boolean binder_enabled;
	public static boolean binder_recipe;
	public static boolean enable_autopick;
	
	public static void setPreloadedBooks(String[] items) {
		try {
			for(int i = 0; i < items.length; i++)
				preload_books[i] = StackHelper.getStackFromString(items[i]);
		} catch (Exception e) {
			BookLogHandler.log(Level.WARN, "Invalid Itemstack in preload books list");
		}
	}
	
	public static void addBookString(String[] str) {
		for(String s: str) {
			additions.add(s);
		}
	}
	
	public static void removeBookString(String[] str) {
		for(String s: str) {
			removals.add(s);
		}
	}

	public static void init(Configuration config) {
		try {
			config.load();
			Config.enable_autopick = config.get("Settings", "Enable Autopickup with Bookbinder", true).getBoolean(true);
			Config.binder_enabled = config.get("Settings", "Enable Book Binder", true).getBoolean(true);
			Config.binder_recipe = config.get("Settings", "Enable Book Binder Recipe", true).getBoolean(true);
			
			config.getCategory("Settings").remove("Book Strings");
			addBookString(config.get("Settings", "Book Strings > Additions", prefix_dft_good, "This is a list of additional words or registered names for books that are accepted by the book binder, this is a partial match, as long as the name contains this it will be accepted").getStringList());
			removeBookString(config.get("Settings", "Book Strings > Removals", prefix_dft_bad, "This is a list of strings, or registered names that cannot be used with the book binder whatsoever").getStringList());
			Config.spawn_binder = config.get("Settings", "Preloaded Binder > Enable", false, "Enabling this will spawn you in the world with a Book Binder").getBoolean(false);
			setPreloadedBooks(config.get("Settings", "Preloaded Binder > Book List", new String[0], "Add a list of itemstacks here in the form of: (itemName meta nbtTags), feel free to omit meta or nbt").getStringList());
			TooltipHandler.PRINT = config.get("Settings", "Print Item Codes to the Console", false).getBoolean(false);
			if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				Config.display_nbt = config.get("Settings", "Debug Mode > Display NBT", true).getBoolean(true);
				GuideHandler.DEBUG_ENABLED = config.get("Settings", "Debug Mode Enabled", false).getBoolean(false);
				GuiGuide.loopRate = config.get("Settings", "Icon Update Rate", 96).getInt();
			}
		} catch (Exception e) {
			FMLLog.getLogger().log(Level.ERROR, "Enchiridion failed to load it's config");
			e.printStackTrace();
		} finally {
			config.save();
		}
	}
}
