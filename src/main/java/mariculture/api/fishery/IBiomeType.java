package mariculture.api.fishery;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.EnumSalinityType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

@Deprecated
public interface IBiomeType {
	//Deprecated
	@Deprecated
	public void addBiome(BiomeGenBase biome, EnumBiomeType type);
	@Deprecated
	public EnumBiomeType getBiomeType(BiomeGenBase biome);
	@Deprecated
	public EnumBiomeType getBiomeType(World world, int x, int y, int z);
	@Deprecated
	public EnumSalinityType getSalinity(BiomeGenBase biome);
	@Deprecated
	public EnumSalinityType getSalinity(World world, int x, int y, int z);
}
