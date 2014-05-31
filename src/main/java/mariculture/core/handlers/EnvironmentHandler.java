package mariculture.core.handlers;

import java.util.HashMap;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.IEnvironmentHandler;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
			else if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)) temperature = 25;
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
		int temperature = getBiomeTemperature(world, x, y, z) - 3;
		temperature += ((getSunBrightness(world, 1F) * 9) - 2);
		if(y > 60) {
			temperature = (int)((60 - (y)) * 0.11255) + temperature;
		} else if (y < 68) {
			temperature = (int) (temperature - ((68 - (y)) * 0.11255));
		}
		
		temperature -= ((int)((float)world.getRainStrength(1) * 0.15D));
		return temperature;
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
