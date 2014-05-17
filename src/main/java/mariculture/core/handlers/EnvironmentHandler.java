package mariculture.core.handlers;

import java.util.HashMap;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.IEnvironmentHandler;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EnvironmentHandler implements IEnvironmentHandler {
	public static final HashMap<Integer, BiomeData> environments = new HashMap();

	@Override
	public void addEnvironment(BiomeGenBase biome, Salinity salinity, int temperature) {
		environments.put(biome.biomeID, new BiomeData(temperature, salinity));
	}

	@Override
	public Salinity getSalinity(World world, int x, int z) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		BiomeData data = environments.get(biome.biomeID);
		return data == null ? Salinity.FRESH : data.salinity;
	}

	@Override
	public int getBiomeTemperature(World world, int x, int y, int z) {
		int temperature = 10;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		BiomeData data = environments.get(biome.biomeID);
		if (data != null) {
			temperature = data.temperature;
		} else {
			if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) temperature = 100;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.DESERT)) temperature = 50;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.FROZEN)) temperature = -30;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)) temperature = 8;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)) temperature = 35;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.FOREST)) temperature = 10;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.PLAINS)) temperature = 10;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.WASTELAND)) temperature = 45;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.END)) temperature = -10;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.WATER)) temperature = 3;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.MAGICAL)) temperature = 20;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) temperature = 15;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)) temperature = -10;
			else if (BiomeDictionary.isBiomeOfType(biome, Type.HILLS)) temperature = -2;
		}

		return temperature;
	}

	@Override
	public int getTemperature(World world, int x, int y, int z) {
		// Biome temperature increased by 4 degrees
		int temperature = getBiomeTemperature(world, x, y + 1, z) + 4;
		boolean isWater = world.getBlock(x, y + 1, z).getMaterial() == Material.water;
		if (!isWater) {
			temperature -= ((y + 1) * 0.1125F);
		} else {
			temperature -= ((115 - y) * 0.175F);
		}

		if (!isWater) {
			temperature += (getLightBrightnessForSkyBlocks(world, x, y + 1, z, 0) % 15) / 4;
		}

		if (y > 32 || world.canBlockSeeTheSky(x, y + 1, z)) {
			temperature += ((getSunBrightness(world, 1F) * 10) - 5);
		}

		return temperature;
	}

	// Copied from world
	public int getLightBrightnessForSkyBlocks(World world, int par1, int par2, int par3, int par4) {
		int i1 = getSkyBlockTypeBrightness(world, EnumSkyBlock.Sky, par1, par2, par3);
		int j1 = getSkyBlockTypeBrightness(world, EnumSkyBlock.Block, par1, par2, par3);

		if (j1 < par4) {
			j1 = par4;
		}

		return i1 << 20 | j1 << 4;
	}

	//Copied from world
	public int getSkyBlockTypeBrightness(World world, EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4) {
		if (world.provider.hasNoSky && par1EnumSkyBlock == EnumSkyBlock.Sky) {
			return 0;
		} else {
			if (par3 < 0) {
				par3 = 0;
			}

			if (par3 >= 256) {
				return par1EnumSkyBlock.defaultLightValue;
			} else if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000) {
				int l = par2 >> 4;
				int i1 = par4 >> 4;

				if (!world.getChunkProvider().chunkExists(l, i1)) {
					return par1EnumSkyBlock.defaultLightValue;
				} else if (world.getBlock(par2, par3, par4).getUseNeighborBrightness()) {
					int j1 = world.getSavedLightValue(par1EnumSkyBlock, par2, par3 + 1, par4);
					int k1 = world.getSavedLightValue(par1EnumSkyBlock, par2 + 1, par3, par4);
					int l1 = world.getSavedLightValue(par1EnumSkyBlock, par2 - 1, par3, par4);
					int i2 = world.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 + 1);
					int j2 = world.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 - 1);

					if (k1 > j1) {
						j1 = k1;
					}

					if (l1 > j1) {
						j1 = l1;
					}

					if (i2 > j1) {
						j1 = i2;
					}

					if (j2 > j1) {
						j1 = j2;
					}

					return j1;
				} else {
					Chunk chunk = world.getChunkFromChunkCoords(l, i1);
					return chunk.getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
				}
			} else {
				return par1EnumSkyBlock.defaultLightValue;
			}
		}
	}

	// Copied from world
	public float getSunBrightness(World world, float par1) {
		float f1 = world.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.2F);

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		f2 = 1.0F - f2;
		f2 = (float) ((double) f2 * (1.0D - (double) (world.getRainStrength(par1) * 5.0F) / 16.0D));
		f2 = (float) ((double) f2 * (1.0D - (double) (world.getWeightedThunderStrength(par1) * 5.0F) / 16.0D));
		return f2 * 0.8F + 0.2F;
	}

	public static class BiomeData {
		int temperature;
		Salinity salinity;

		public BiomeData(int temperature, Salinity salinity) {
			this.temperature = temperature;
			this.salinity = salinity;
		}
	}

	@Override
	public boolean matches(Salinity salt, int temp, Salinity[] salinity, int[] temperature) {
		if (salinity == null || temperature == null) return false;
		if (temperature.length != 2) return false;
		if (temp >= temperature[0] && temp <= temperature[1]) {
			for (Salinity s : salinity) {
				if (s == salt) return true;
			}
		}

		return false;
	}
}
