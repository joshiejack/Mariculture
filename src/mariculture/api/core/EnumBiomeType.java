package mariculture.api.core;

import mariculture.api.fishery.fish.EnumSalinityType;

public enum EnumBiomeType {
	OCEAN(0, -7, 10, 6, EnumSalinityType.SALT), // Ocean Biomes - Saltwater
	NORMAL(0, -1, 1, 5, EnumSalinityType.FRESH), // Plains, Swamps, Forests, River - Fresh Water
	ARID(14, 14, 28, 2, EnumSalinityType.FRESH), // Desert - Fresh Water
	HOT(3, 3, 13, 3, EnumSalinityType.FRESH), // Jungle - Fresh Water
	FROZEN(-14, -100, -10, 9, EnumSalinityType.FRESH), // Frozen River, Tundra - Fresh Water
	HELL(28, 28, 100, 1, EnumSalinityType.MAGIC), // The Nether - Fresh Water
	COLD(-3, -10, -1, 8, EnumSalinityType.FRESH), // Mountains, Taiga - Fresh Water
	FROZEN_OCEAN(-10, -65, -1, 10, EnumSalinityType.SALT), // Frozen Ocean - Saltwater
	MUSHROOM(0, -1, 2, 4, EnumSalinityType.MAGIC), // Mushroom Biome - Fresh Water
	ENDER(-28, -50, -28, 7, EnumSalinityType.FRESH); // The End - Fresh Water

	private int baseTemp;
	private int minTemp;
	private int maxTemp;
	private int cooling;
	private EnumSalinityType salinity;
	
	/**
	 * EnumBiomeType is used to determine whether fish can live in biomes and how fast machines work
	 * @param base		The base temperature of the biome
	 * @param min		If the temperature goes below this then it will no longer be considered this biome
	 * @param max		If the temperature goes above this, it's no longer considered this biome
	 * @param cool		How fast Smelters cool down and Freezers freeze
	 * @param salt: 	Whether this biome is salt water
	 * @param special:	Whether this biome requires the Ethereal upgrade for any fish to live in it
	 */
	private EnumBiomeType(int base, int min, int max, int cool, EnumSalinityType salinity) {
		this.baseTemp = base;
		this.minTemp = min;
		this.maxTemp = max;
		this.cooling = cool;
		this.salinity = salinity;
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
	
	public EnumSalinityType getSalinity() {
		return this.salinity;
	}
	
	public int getCoolingSpeed() {
		return this.cooling;
	}
}
