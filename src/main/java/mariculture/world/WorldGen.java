package mariculture.world;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.Rand;
import mariculture.world.decorate.WorldGenAncientSand;
import mariculture.world.decorate.WorldGenCoralReef;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

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
			if(WorldGeneration.ANCIENT_SAND_ENABLED) generateAncientSand(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(WorldGeneration.CORAL_REEF_ENABLED) generateCoralReef(world, random, x, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isCoralReef = false;
	private static WorldGenCoralReef coralGen = new WorldGenCoralReef();
	public static void generateCoralReef(World world, Random random, int x, int z) {
		if(!isCoralReef && Rand.nextInt(256)) isCoralReef = true;
		if(isCoralReef && Rand.nextInt(40)) isCoralReef = false;
		if(isCoralReef) {
			for(int i = 0; i < 5; i++) {
				coralGen.generate(world, random, x + random.nextInt(16) + 8, 64, z + random.nextInt(16) + 18);
			}
		}
	}
	
	public static void generateAncientSand(World world, Random random, int x, int z) {
		if(random.nextInt(2) == 0) {
	    	int j = x + random.nextInt(16) + 8;
	    	int k = z + random.nextInt(16) + 8;
	        new WorldGenAncientSand(Core.sands, GroundMeta.ANCIENT, 4).generate(world, random, j, world.getTopSolidOrLiquidBlock(j, k), k);
		}
	}
}
