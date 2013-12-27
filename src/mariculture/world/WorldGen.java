package mariculture.world;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.lib.WorldGeneration;
import mariculture.plugins.PluginBiomesOPlenty;
import mariculture.plugins.PluginBiomesOPlenty.Biome;
import mariculture.world.decorate.WorldGenKelp;
import mariculture.world.decorate.WorldGenKelpForest;
import mariculture.world.decorate.WorldGenReef;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;

public class WorldGen implements IWorldGenerator {	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			break;
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
		case 1:
			break;
		default:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	
	private void generateOverworld(World world, Random random, int x, int z) {
		if(MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(x, z)) == EnumBiomeType.OCEAN) {
			generateOceanFeatures(world, random, x, z);
		}
	}

	private void generateOceanFeatures(World world, Random random, int x, int z) {
		try {
			if(WorldGeneration.CORAL_ENABLED)
				generateCoral(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(WorldGeneration.KELP_PATCH_ENABLED)
				generateKelp(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(WorldGeneration.KELP_FOREST_ENABLED)
				generateKelpForest(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateCoral(World world, Random random, int x, int z) {		
		boolean isCoralReef = PluginBiomesOPlenty.isBiome(world, x, z, Biome.CORAL);
		int chance = (Loader.isModLoaded("BiomesOPlenty")) ? WorldGeneration.CORAL_CHANCE: WorldGeneration.CORAL_CHANCE * 10;
		
		chance = (chance <= 1)? 1: chance;
		if(isCoralReef) {
			if (random.nextInt(chance) == 0) {
				int x2 = x + random.nextInt(16) + 8;
				int z2 = z + random.nextInt(16) + 8;
				(new WorldGenReef(128)).generate(world, random, x2, z2);
			}
		}
	}
	
	private void generateKelp(World world, Random random, int x, int z) {
		int chance = WorldGeneration.KELP_CHANCE / 128;
		chance = (chance <= 1)? 1: chance;
		
		if (random.nextInt(chance) == 0) {
			for (int j = 0; j < 5; ++j) {
				int x2 = x + random.nextInt(16) + 8;
				int z2 = z + random.nextInt(16) + 8;
				new WorldGenKelp().generate(world, random, x2, 0, z2);
			}
		}
	}
	
	private void generateKelpForest(World world, Random random, int x, int z) {
		boolean isKelpForest = PluginBiomesOPlenty.isBiome(world, x, z, Biome.KELP);
		int maxHeight = WorldGeneration.KELP_HEIGHT;
		int chance = (Loader.isModLoaded("BiomesOPlenty")) ? WorldGeneration.KELP_CHANCE: WorldGeneration.KELP_CHANCE * 3;
		
		chance = (chance <= 1)? 1: chance;
		maxHeight = (maxHeight <= 1)? 1: maxHeight;
		if(isKelpForest) {
			if ((random.nextInt(chance) + 1) == 1) {
				int x2 = x + random.nextInt(16) + 8;
				int z2 = z + random.nextInt(16) + 8;
				new WorldGenKelpForest().generate(world, random, x2, 0, z2, maxHeight);
			}
		}
	}
}
