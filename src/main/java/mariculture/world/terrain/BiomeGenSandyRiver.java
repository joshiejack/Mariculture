package mariculture.world.terrain;

import java.util.Random;

import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.handlers.WorldEventHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.gen.feature.WorldGenSand;

public class BiomeGenSandyRiver extends BiomeGenRiver {
    public BiomeGenSandyRiver(int id) {
        super(id);

        theBiomeDecorator.gravelAsSandGen = new WorldGenSand(Blocks.gold_block, 6);
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metas, int x, int z, double noise) {
        WorldEventHandler.genBiomeTerrain(world, rand, blocks, metas, x, z, noise, this, WorldGen.RIVER_LIMESTONE);
    }
}
