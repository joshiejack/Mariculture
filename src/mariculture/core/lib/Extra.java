package mariculture.core.lib;

import net.minecraft.world.biome.BiomeGenBase;

public class Extra {
	public static int[] RIVER_BIOMES;
	public static final int[] RIVERS_DEFAULT = new int[] { BiomeGenBase.river.biomeID, BiomeGenBase.frozenRiver.biomeID };
	public static int[] OCEAN_BIOMES;
	public static final int[] OCEANS_DEFAULT =  new int[] { BiomeGenBase.ocean.biomeID };
	public static int HARDCORE_DIVING;
	public static boolean FLUDD_WATER_ON;
	public static int PEARL_GEN_CHANCE;
	public static boolean DEBUG_ON;
	public static int REFRESH_CLIENT_RATE;
	public static boolean REDSTONE_PUMP;
	public static boolean ACTIVATE_PUMP;
	public static boolean BUILDCRAFT_PUMP;
	public static int METAL_RATE;
	public static int bait0;
	public static int bait1;
	public static int bait2;
	public static int bait3;
	public static int bait4;
	public static int bait5;
	public static int KELP_SPREAD_CHANCE;
	public static int CORAL_SPREAD_CHANCE;
	public static int KELP_GROWTH_CHANCE;
	public static boolean ENABLE_ENDER_SPAWN;
	public static int FISH_FOOD_TICK;
	public static int TANK_UPDATE;
	public static int EFFECT_TICK;
	public static int DEATH_TICKER;
	public static int DRAGON_EGG_BASE;
	public static int DRAGON_EGG_ETHEREAL;
	public static boolean DROP_JEWELRY;
	public static boolean GEN_ENDER_PEARLS;
	public static int JEWELRY_TICK_RATE;
	
	public static int SONIC_MOD;
	public static int LEAP_MOD;
	public static int CAN_WORK_TICK;
	public static boolean GEYSER_ANIM;
	public static boolean FLUDD_BLOCK_ANIM;
	public static boolean ENDER_CONVERTER;
	
	public static final String[] EXCEPTIONS_DEFAULT = new String[] { "nuggetAluminum: nuggetNaturalAluminum", 
		"ingotAluminum: ingotNaturalAluminum", "oreAluminum: oreNaturalAluminum", "blockAluminum: blockNaturalAluminum", 
		"oreBauxite: oreAluminum", "oreBauxite: oreNaturalAluminum", "oreTitanium: oreRutile"};
	
	public static final String[] WHITELIST_DEFAULT = new String[] { "ore", "ingot", "block", "nugget", "dust", "gem", 
		"dyeRed", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange" };
}
