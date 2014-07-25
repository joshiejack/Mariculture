package mariculture.world.terrain;

import java.util.Random;

import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.handlers.WorldEventHandler;
import mariculture.world.GenerationHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSandyOcean extends BiomeGenOcean {
    public BiomeGenSandyOcean(int id) {
        super(id);

        theBiomeDecorator.lapisGen = new WorldGenMinable(Blocks.lapis_ore, 8);
    }

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
        return GenerationHandler.kelpGenerator;
    }

    @Override
    public void decorate(World world, Random rand, int x, int z) {
        super.decorate(world, rand, x, z);

        GenerationHandler.generateAncientSand(world, rand, x, z);
        GenerationHandler.generateCoralReef(world, rand, x, z);
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metas, int x, int z, double noise) {
        WorldEventHandler.genBiomeTerrain(world, rand, blocks, metas, x, z, noise, this, ((this.biomeID == BiomeGenBase.deepOcean.biomeID) ? WorldGen.OCEAN_DEEP_LIMESTONE : WorldGen.OCEAN_LIMESTONE));
    }
}
