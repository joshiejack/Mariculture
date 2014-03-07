package mariculture.core.lib;

public class Compatibility {
	public static String[] WHITELIST;
	public static String[] BLACKLIST;
	
	public static boolean ENABLE_WHITELIST;
	public static final String[] WHITELIST_DEFAULT = new String[] { "ore", "ingot", "block", "nugget", "dust", "gem", 
	"dyeRed", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange" };
	
	public static final String[] BLACKLIST_DEFAULT = new String[] { "dyeBlue", "dyeBlack", "dyeWhite", "dyeGreen", "dyeBrown", "stickWood", "plankWood",
		"logWood", "stairWood", "treeLeaves", "treeSapling", "glass", "slabWood"};
}