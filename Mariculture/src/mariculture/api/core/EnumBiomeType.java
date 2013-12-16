package mariculture.api.core;

public enum EnumBiomeType {
	OCEAN(0, -7, 10, 3, true, false), // Ocean Biomes - Saltwater
	NORMAL(0, -1, 1, 3, false, false), // Plains, Swamps, Forests, River - Fresh Water
	ARID(14, 14, 28, 1, false, false), // Desert - Fresh Water
	HOT(3, 3, 13, 2, false, false), // Jungle - Fresh Water
	FROZEN(-14, -100, -10, 5, false, false), // Frozen River, Tundra - Fresh Water
	HELL(28, 28, 100, 1, false, true), // The Nether - Fresh Water
	COLD(-3, -10, -1, 4, false, false), // Mountains, Taiga - Fresh Water
	FROZEN_OCEAN(-10, -65, -1, 5, true, false), // Frozen Ocean - Saltwater
	MUSHROOM(0, -1, 2, 3, false, true), // Mushroom Biome - Fresh Water
	ENDER(-28, -50, -28, 4, false, true); // The End - Fresh Water

	private int baseTemp;
	private int minTemp;
	private int maxTemp;
	private int cooling;
	private boolean saltWater;
	private boolean special;
	
	/**
	 * EnumBiomeType is used to determine whether fish can live in biomes and how fast machines work
	 * @param base		The base temperature of the biome
	 * @param min		If the temperature goes below this then it will no longer be considered this biome
	 * @param max		If the temperature goes above this, it's no longer considered this biome
	 * @param cool		How fast Smelters cool down and Freezers freeze
	 * @param salt: 	Whether this biome is salt water
	 * @param special:	Whether this biome requires the Ethereal upgrade for any fish to live in it
	 */
	private EnumBiomeType(int base, int min, int max, int cool, boolean salt, boolean special) {
		this.baseTemp = base;
		this.minTemp = min;
		this.maxTemp = max;
		this.cooling = cool;
		this.saltWater = salt;
		this.special = special;
	}
	
	public int baseTemp() {
		return this.baseTemp;
	}
	
	public int minTemp() {
		return this.minTemp;
	}
	
	public int maxTemp() {
		return this.maxTemp;
	}
	
	public boolean isSaltWater() {
		return saltWater;
	}
	
	public boolean isSpecial() {
		return this.special;
	}
	
	public int getCoolingSpeed() {
		return this.cooling;
	}
}
