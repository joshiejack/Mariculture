package mariculture.core.handlers;

import java.util.HashMap;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.core.IEnvironmentHandler;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EnvironmentHandler implements IEnvironmentHandler {
	public static final HashMap<BiomeGenBase, BiomeData> environments = new HashMap();
	@Override
	public void addEnvironment(BiomeGenBase biome, Salinity salinity, int temperature) {
		environments.put(biome, new BiomeData(temperature, salinity));
	}
	
	@Override
	public Salinity getSalinity(World world, int x, int z) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		BiomeData data = environments.get(biome);		
		return data == null? Salinity.FRESH: data.salinity;
	}
	
	@Override
	public int getBiomeTemperature(World world, int x, int y, int z) {
		int temperature = 10;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		BiomeData data = environments.get(biome);
		if(data != null) {
			temperature = data.temperature;
		} else {
			if(BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) temperature = 100;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.DESERT)) temperature = 50;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.FROZEN)) temperature = -30;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)) temperature = 8;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)) temperature = 35;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.FOREST)) temperature = 10;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.PLAINS)) temperature = 10;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.WASTELAND)) temperature = 45;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.END)) temperature = -10;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.WATER)) temperature = 3;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.MAGICAL)) temperature = 20;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) temperature = 15;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)) temperature = -10;
			else if(BiomeDictionary.isBiomeOfType(biome, Type.HILLS)) temperature = -2;
		}
		
		return temperature;
	}

	@Override
	public int getTemperature(World world, int x, int y, int z) {
		//Biome temperature increased by 4 degrees
		int temperature = getBiomeTemperature(world, x, y + 1, z) + 4;
		boolean isWater = world.getBlockMaterial(x, y + 1, z) == Material.water;
		if(!isWater) {
			temperature -= ((y + 1) * 0.1125F);
		} else {
			temperature -= ((115 -y) * 0.175F);
		}
		
		if(!isWater) {
			temperature += (world.getLightBrightnessForSkyBlocks(x, y + 1, z, 0) % 15) / 4;
		}

		if(y > 32 || world.canBlockSeeTheSky(x, y + 1, z)) {
			temperature += ((world.getSunBrightness(1F) * 10) - 5);
		}
		
		return temperature;
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
		if(temp >= temperature[0] && temp <= temperature[1]) {
			for(Salinity s: salinity) {
				if(s == salt) return true;
			}
		}
		
		return false;
	}
}
