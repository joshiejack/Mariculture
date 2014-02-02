package mariculture.core.lib;

import tconstruct.common.TContent;

public class Compatibility {
    public static String[] EXCEPTIONS;
	public static String[] WHITELIST;
	public static String[] BLACKLIST;
	
	public static boolean ENABLE_WHITELIST;
	public static final String[] EXCEPTIONS_DEFAULT = new String[] { "nuggetAluminum: nuggetNaturalAluminum", 
	"ingotAluminum: ingotNaturalAluminum", "oreAluminum: oreNaturalAluminum", "blockAluminum: blockNaturalAluminum", 
	"oreBauxite: oreAluminum", "oreBauxite: oreNaturalAluminum"};
	
	public static final String[] WHITELIST_DEFAULT = new String[] { "ore", "ingot", "block", "nugget", "dust", "gem", 
	"dyeRed", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange" };
	
	public static final String[] BLACKLIST_DEFAULT = new String[] { "dyeBlue", "dyeBlack", "dyeWhite", "dyeGreen", "dyeBrown", "stickWood", "plankWood",
		"logWood", "stairWood", "treeLeaves", "treeSapling", "glass", "slabWood"};
}
