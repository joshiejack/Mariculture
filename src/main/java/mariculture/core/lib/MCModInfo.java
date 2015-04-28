package mariculture.core.lib;

public class MCModInfo {
    public static final String MODID = "Mariculture";
    public static final String MODNAME = "Mariculture";
    public static final String MODPATH = "mariculture";
    public static final String JAVAPATH = "mariculture.";
    
    private static final String forge = "10.13.0.1197";
    private static final String blood_magic = "v1.0.1";
    private static final String bop = "2.0.0";
    private static final String liquids = "after:HardcoreEnderExpansion;after:TConstruct;after:ThermalFoundation";
    public static final String AFTER = "required-after:Forge@[" + forge + ",);" + "after:modtweaker2;after:Forestry;after:AWWayofTime@[" + blood_magic + ",);after:BiomesOPlenty@[" + bop + ",);" + liquids;
}
