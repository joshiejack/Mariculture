package mariculture.core.handlers;

import java.util.HashMap;
import java.util.logging.Level;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.EnumSalinityType;
import mariculture.api.core.Environment;
import mariculture.api.fishery.IBiomeType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeTypeHandler implements IBiomeType {
	private final HashMap<BiomeGenBase, Environment> biomes = new HashMap();
	@Override
	public void addBiome(BiomeGenBase biome, EnumBiomeType type) {
		LogHandler.log(Level.WARNING, "A mod attempted to register a biome type with mariculture, but failed due to using an older method");
		LogHandler.log(Level.WARNING, biome.biomeName + " - " + type.name());
	}

	@Override
	public EnumBiomeType getBiomeType(BiomeGenBase biome) {
		return EnumBiomeType.NORMAL;
	}

	@Override
	public EnumBiomeType getBiomeType(World world, int x, int y, int z) {
		return EnumBiomeType.NORMAL;
	}

	@Override
	public EnumSalinityType getSalinity(BiomeGenBase biome) {
		return  EnumSalinityType.FRESH;
	}

	@Override
	public EnumSalinityType getSalinity(World world, int x, int y, int z) {
		return EnumSalinityType.FRESH;
	}
}