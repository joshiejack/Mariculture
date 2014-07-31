package mariculture.world.terrain;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.handlers.WorldEventHandler;
import mariculture.core.lib.GroundMeta;
import mariculture.world.decorate.WorldGenAncientSand;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBeach;

public class BiomeGenSandyBeach extends BiomeGenBeach {
    public BiomeGenSandyBeach(int id) {
        super(id);

        theBiomeDecorator.sandGen = new WorldGenAncientSand();
        theBiomeDecorator.gravelAsSandGen = new WorldGenAncientSand();
        theBiomeDecorator.deadBushPerChunk = 1;
        theBiomeDecorator.reedsPerChunk = 1;
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metas, int x, int z, double noise) {
        WorldEventHandler.genBiomeTerrain(world, rand, blocks, metas, x, z, noise, this, WorldGen.BEACH_LIMESTONE);
    }
}