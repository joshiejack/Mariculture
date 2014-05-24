package mariculture.core.lib;

public class Compatibility {
	public static String[] WHITELIST;
	public static String[] BLACKLIST;
	public static String[] BLACKLIST_PREFIX;
	public static String[] BLACKLIST_ITEMS;
	
	public static boolean ENABLE_WHITELIST;
	public static final String[] WHITELIST_DEFAULT = new String[] { "ore", "ingot", "block", "nugget", "dust", "gem", 
	"dyeRed", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange" };
	
	public static final String[] BLACKLIST_DEFAULT = new String[] { "dyeBlue", "dyeBlack", "dyeWhite", "dyeGreen", "dyeBrown", "stickWood", "plankWood",
		"logWood", "stairWood", "treeLeaves", "treeSapling", "glass", "slabWood"};
	
	public static final String[] BLACKLIST_PREFIX_DEFAULT = new String[] { "listAll", "crop" };
	public static final String[] BLACKLIST_ITEMS_DEFAULT = new String[] { "tconstruct.Materials 36", "dye 4", "dye 2", "dye 3", "dye 15", "dye 0"};
}