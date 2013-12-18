package mariculture.core.handlers;

import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.IBiomeType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeTypeHandler implements IBiomeType {
	private final Map biomeList = new HashMap();

	@Override
	public void addBiome(BiomeGenBase biome, EnumBiomeType type) {
		biomeList.put(biome, type);
	}

	@Override
	public EnumBiomeType getBiomeType(BiomeGenBase biome) {
		if (biomeList.get(biome) != null) {
			return (EnumBiomeType) biomeList.get(biome);
		}

		// Let's guess
		if (BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) {
			return EnumBiomeType.OCEAN;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.DESERT)) {
			return EnumBiomeType.ARID;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.END)) {
			return EnumBiomeType.ENDER;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.FROZEN)) {
			return EnumBiomeType.FROZEN;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST)) {
			return EnumBiomeType.NORMAL;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.FROZEN)) {
			return EnumBiomeType.NORMAL;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.HILLS)) {
			return EnumBiomeType.COLD;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)) {
			return EnumBiomeType.HOT;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.MAGICAL)) {
			return EnumBiomeType.MUSHROOM;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)) {
			return EnumBiomeType.COLD;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) {
			return EnumBiomeType.HELL;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.PLAINS)) {
			return EnumBiomeType.NORMAL;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)) {
			return EnumBiomeType.NORMAL;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.WASTELAND)) {
			return EnumBiomeType.HOT;
		} else if (BiomeDictionary.isBiomeOfType(biome, Type.WATER)) {
			return EnumBiomeType.OCEAN;
		}

		return EnumBiomeType.NORMAL;
	}
}