package mariculture.world;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.plugins.PluginBiomesOPlenty;
import mariculture.plugins.PluginBiomesOPlenty.Biome;
import mariculture.world.decorate.WorldGenAncientSand;
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
		if(WorldGenHandler.isOceanBiome(world, x, z)) {
			generateOceanFeatures(world, random, x, z);
		}
	}

	private void generateOceanFeatures(World world, Random random, int x, int z) {
		try {
			if(WorldGeneration.CORAL_ENABLED) generateCoral(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(WorldGeneration.ANCIENT_SAND_ENABLED) generateAncientSand(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateAncientSand(World world, Random random, int x, int z) {
		if(random.nextInt(2) == 0) {
	    	int j = x + random.nextInt(16) + 8;
	    	int k = z + random.nextInt(16) + 8;
	        new WorldGenAncientSand(Core.groundBlocks, GroundMeta.ANCIENT, 4).generate(world, random, j, world.getTopSolidOrLiquidBlock(j, k), k);
		}
	}
	
	public static void generateCoral(World world, Random random, int x, int z) {		
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
}
