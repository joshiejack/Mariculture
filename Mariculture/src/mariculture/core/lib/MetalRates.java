package mariculture.core.lib;

public class MetalRates {
	public static int NUGGET = Extra.METAL_RATE;
	public static int INGOT = NUGGET * 9;
	public static int BLOCK = INGOT * 9;
	public static int ORE = INGOT * 2;
	
	// Ore Nugget Ingot Block Dust
	public static final int[] MATERIALS = new int[] { ORE, NUGGET, INGOT, BLOCK, INGOT };
	//Pick, Shovel, Axe, Sword, Hoe
	public static final int[] TOOLS = new int[] { NUGGET * 6, NUGGET * 2, NUGGET * 6, NUGGET * 4, NUGGET * 4 };
	//Head, Chest, Legs, Boots
	public static final int[] ARMOR = new int[] { NUGGET * 10, NUGGET * 16, NUGGET * 14, NUGGET * 8 };
}
