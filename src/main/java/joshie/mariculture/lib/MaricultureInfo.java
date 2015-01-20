package joshie.mariculture.lib;

public class MaricultureInfo {
	public static final String JAVAPATH = "joshie.mariculture.";
	public static final String MODID = "Mariculture2";
	public static final String MODNAME = "Mariculture 2";
	public static final String MODPATH = "mariculture";
    public static final String INITIALS = "M";
    public static final String PENGUINCORE_VERSION = "0.0.2";
    public static final String ENCHIRIDION_VERSION = "2.0";
    public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES = "after:PenguinCore;after:Enchiridion2";
	public static boolean IS_DEV = false;
    
    public static final String[] MODULES = new String[] { 
    	"diving", "exploration", "fishery", "sealife"
    };
    
    public static void updateDev() {
    	try {
    		Class.forName("net.minecraft.item.Item");
    		IS_DEV = true;
    	} catch (Exception e) { IS_DEV = false; }
    }
}
