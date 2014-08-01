package mariculture.world;

import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.handlers.WorldEventHandler;
import mariculture.world.decorate.WorldGenAncientSand;
import mariculture.world.decorate.WorldGenKelp;
import mariculture.world.decorate.WorldGenReef;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class GenerationHandler implements IWorldGenerator {
    public static WorldGenReef coralGenerator = new WorldGenReef();
    public static WorldGenKelp kelpGenerator = new WorldGenKelp();
    public static WorldGenAncientSand sandGen = new WorldGenAncientSand();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (!WorldEventHandler.isBlacklisted(world.provider.dimensionId)) {
            try {
                Salinity salinity = MaricultureHandlers.environment.getSalinity(world, chunkX * 16, chunkZ * 16);
                if (salinity == Salinity.SALINE) {
                    generateAncientSand(world, random, chunkX * 16, chunkZ * 16);
                    generateCoralReef(world, random, chunkX * 16, chunkZ * 16);
                }

                if (salinity != Salinity.FRESH) {
                    generateKelpPlants(world, random, chunkX * 16, chunkZ * 16);
                }
            } catch (Exception e) {}
        }
    }

    public static void generateAncientSand(World world, Random rand, int x, int z) {
        if (WorldGen.ANCIENT_SAND_ENABLED) {
            if (rand.nextInt(Math.max(1, WorldGen.ANCIENT_SAND_CHANCE)) == 0) {
                int j = x + rand.nextInt(16) + 8;
                int k = z + rand.nextInt(16) + 8;
                sandGen.generate(world, rand, j, world.getTopSolidOrLiquidBlock(j, k), k);
            }
        }
    }

    private static boolean isCoralReef = false;

    public static void generateCoralReef(World world, Random rand, int x, int z) {
        if (WorldGen.CORAL_REEF_ENABLED) {
            if (!isCoralReef && rand.nextInt(WorldGen.CORAL_REEF_START_CHANCE) == 0) {
                isCoralReef = true;
            }
            if (isCoralReef && rand.nextInt(WorldGen.CORAL_REEF_END_CHANCE) == 0) {
                isCoralReef = false;
            }
            if (isCoralReef) {
                for (int i = 0; i < 5; i++) {
                    coralGenerator.generate(world, rand, x + rand.nextInt(16) + 8, 64, z + rand.nextInt(16) + 8);
                }
            }
        }
    }

    public static void generateKelpPlants(World world, Random rand, int x, int z) {
        int k = x + rand.nextInt(16) + 8;
        int l = z + rand.nextInt(16) + 8;
        int i1 = rand.nextInt(world.getHeightValue(k, l) * 2);

        GenerationHandler.kelpGenerator.generate(world, rand, k, i1, l);
    }
}
