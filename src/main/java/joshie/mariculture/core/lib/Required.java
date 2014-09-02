package joshie.mariculture.core.lib;

public class Required {
    private static final String forge = "10.13.0.1197";
    private static final String enchiridion = "1.1";
    private static final String blood_magic = "v1.0.1";
    private static final String bop = "2.0.0";
    private static final String liquid_after = "after:HardcoreEnderExpansion;after:TConstruct;after:ThermalFoundation";
    public static final String after = "required-after:Forge@[" + forge + ",);required-after:Enchiridion@[" + enchiridion + ",);" + "before:Mariculture|API;" + "after:AWWayofTime@[" + blood_magic + ",);after:BiomesOPlenty@[" + bop + ",);" + liquid_after;
}
