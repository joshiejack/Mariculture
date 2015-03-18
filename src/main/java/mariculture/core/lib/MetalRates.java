package mariculture.core.lib;

import mariculture.core.config.GeneralStuff;
import mariculture.core.config.Machines.MachineSettings;

public class MetalRates {
    public static int NUGGET = GeneralStuff.METAL_RATE;
    public static int INGOT = NUGGET * 9;
    public static int BLOCK = INGOT * 9;
    public static int ORE = MachineSettings.DEFAULT_CRUCIBLE_MULTIPLIER;

    public static final int[] ORES = new int[] { ORE };
    public static final int[] METALS = new int[] { NUGGET, INGOT, BLOCK, INGOT };
    //Pick, Shovel, Axe, Sword, Hoe
    public static final int[] TOOLS = new int[] { INGOT * 3, INGOT * 1, INGOT * 3, INGOT * 2, INGOT * 2 };
    //Head, Chest, Legs, Boots
    public static final int[] ARMOR = new int[] { INGOT * 5, INGOT * 8, INGOT * 7, INGOT * 4 };
}
