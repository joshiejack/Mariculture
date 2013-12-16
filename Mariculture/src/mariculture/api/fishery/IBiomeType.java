package mariculture.api.fishery;

import mariculture.api.core.EnumBiomeType;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBiomeType {

	/**
	 * Allows you add custom biomes to be classed a specific type of biome
	 * 
	 * @param biome
	 * @param EnumBiomeType
	 */
	public void addBiome(BiomeGenBase biome, EnumBiomeType type);

	/**
	 * Returns the biome type
	 * 
	 * @param biomeGenBase
	 * @return
	 */
	public EnumBiomeType getBiomeType(BiomeGenBase biome);
}
