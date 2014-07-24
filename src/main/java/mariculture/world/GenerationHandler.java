package mariculture.world;

import java.util.Random;

import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.world.terrain.BiomeGenSandyOcean;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class GenerationHandler implements IWorldGenerator {
    private boolean isBlacklisted(int i) {
        for (int j : WorldGen.OCEAN_BLACKLIST) {
            if (i == j) return true;
        }

        return false;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (!isBlacklisted(world.provider.dimensionId)) {
            try {
                generateAncientSand(world, random, chunkX * 16, chunkZ * 16);
                generateCoralReef(world, random, chunkX * 16, chunkZ * 16);
                generateKelpPlants(world, random, chunkX * 16, chunkZ * 16);
            } catch (Exception e) {}
        }
    }

    public static void generateAncientSand(World world, Random rand, int x, int z) {
        BiomeGenSandyOcean.generateSand(world, rand, x, z);
    }

    public static void generateCoralReef(World world, Random rand, int x, int z) {
        BiomeGenSandyOcean.generateCoral(world, rand, x, z);
    }

    public static void generateKelpPlants(World world, Random rand, int x, int z) {
        int k = x + rand.nextInt(16) + 8;
        int l = z + rand.nextInt(16) + 8;
        int i1 = rand.nextInt(world.getHeightValue(k, l) * 2);
        BiomeGenSandyOcean.kelpGenerator.generate(world, rand, k, i1, l);
    }
}
