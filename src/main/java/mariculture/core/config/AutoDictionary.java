package mariculture.core.config;

import net.minecraftforge.common.config.Configuration;

public class AutoDictionary {
	private static final String CATEGORY = "Auto-Dictionary";
	//Defaults
	public static final String[] WHITELIST_DEFAULT = new String[] { "ore", "ingot", "block", "nugget", "dust", "gem", 
		"dyeRed", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange" };
	
	public static final String[] BLACKLIST_PREFIX_DEFAULT = new String[] { "listAll", "crop" };
	public static final String[] BLACKLIST_ITEMS_DEFAULT = new String[] { "TConstruct:materials 36", "dye 4", "dye 2", "dye 3", "dye 15", "dye 0"};
	public static final String[] BLACKLIST_DEFAULT = new String[] { "dye", "stickWood", "plankWood", "logWood", "stairWood", "treeLeaves", "treeSapling", "slabWood",
		"blockGlass", "paneGlass", "stone", "cobblestone", "sandstone", "record"};
	
	public static String[] WHITELIST;
	public static String[] BLACKLIST;
	public static String[] BLACKLIST_PREFIX;
	public static String[] BLACKLIST_ITEMS;
	public static boolean ENABLE_WHITELIST;
	
	public static void init(Configuration config) {
		ENABLE_WHITELIST = config.get(CATEGORY, "Use Whitelist", false, "Whether to use Whitelisting or Blacklisting Mode").getBoolean(false);
        BLACKLIST = config.get(CATEGORY, "Blacklist", BLACKLIST_DEFAULT, "Place Ore Dictionary names, to blacklist here").getStringList();
        WHITELIST = config.get(CATEGORY, "Whitelist", WHITELIST_DEFAULT, "Place Ore Dictionary Prefixes here, that are acceptable to be converted").getStringList();
        BLACKLIST_PREFIX = config.get(CATEGORY, "Blacklist Prefixes", BLACKLIST_PREFIX_DEFAULT, "Add a list of prefixes you want to blacklist from the auto-dictionary").getStringList();
        BLACKLIST_ITEMS = config.get(CATEGORY, "Blacklist Items", BLACKLIST_ITEMS_DEFAULT, "List of items to blacklist from being able to converted, format is 'modid:itemname meta'").getStringList();
	}
}
