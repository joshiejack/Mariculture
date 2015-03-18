package maritech.handlers;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.Environment.Salinity;
import mariculture.core.Core;
import mariculture.core.config.WorldGeneration.OreGen;
import mariculture.core.lib.AirMeta;
import maritech.world.WorldGenGas;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class MTWorldGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1:
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
            case 1:
                break;
            default:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    private void generateSurface(World world, Random random, int x, int z) {
        generateGas(world, random, x, z);
    }

    public static void generateGas(World world, Random random, int x, int z) {
        int posX, posY, posZ;
        //Layers 16-25 for Gas
        if (OreGen.NATURAL_GAS_ON && random.nextInt(OreGen.NATURAL_GAS_CHANCE) == 0) {
        	if (MaricultureHandlers.environment.getSalinity(world, x, z) == Salinity.SALINE) {
	            posX = x + random.nextInt(16);
	            posY = OreGen.NATURAL_GAS_MIN + random.nextInt(OreGen.NATURAL_GAS_MAX - OreGen.NATURAL_GAS_MIN);
	            posZ = z + random.nextInt(16);
	            new WorldGenGas(Core.air, AirMeta.NATURAL_GAS, OreGen.NATURAL_GAS_VEIN, Blocks.stone).generate(world, random, posX, posY, posZ);
	        }
        }
    }
}
