package joshie.mariculture.modules.abyssal.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenRavine;

import static joshie.mariculture.modules.abyssal.Abyssal.*;
import static joshie.mariculture.modules.abyssal.gen.WorldGenOverworld.BEDROCK;
import static joshie.mariculture.modules.abyssal.gen.WorldGenOverworld.WATER;

public class MapGenTrench extends MapGenRavine {
    @Override
    public void generate(World worldIn, int x, int z, ChunkPrimer primer) {
        worldObj = worldIn;
        rand.setSeed(worldIn.getSeed());
        long j = rand.nextLong();
        long k = rand.nextLong();

        for (int l = x - DEEP_SEA_LENGTH; l <= x + DEEP_SEA_LENGTH; ++l) {
            for (int i1 = z - DEEP_SEA_LENGTH; i1 <= z + DEEP_SEA_LENGTH; ++i1) {
                long j1 = (long)l * j;
                long k1 = (long)i1 * k;
                rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
                recursiveGenerate(worldIn, l, i1, x, z, primer);
            }
        }
    }

    @Override
    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ) {
        return false;
    }

    @Override
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int int1, int in2, ChunkPrimer primer) {
        if (rand.nextInt(DEEP_SEA_RARITY) == 0) {
            double d1 = findFirstSolidBlock(primer, rand.nextInt(16), rand.nextInt(16));
            double d0 = (double) (chunkX * 16 + rand.nextInt(16));
            double d2 = (double) (chunkZ * 16 + rand.nextInt(16));
            float f = rand.nextFloat() * ((float) Math.PI * DEEP_SEA_WIDTH);
            float f1 = (rand.nextFloat() - DEEP_SEA_WIDTH_3) * DEEP_SEA_WIDTH / DEEP_SEA_WIDTH_2;
            float f2 = (rand.nextFloat() * DEEP_SEA_WIDTH + rand.nextFloat()) * DEEP_SEA_WIDTH;
            addTunnel(rand.nextLong(), int1, in2, primer, d0, d1, d2, f2, f, f1, 0, 0, d2 / DEEP_SEA_DEPTH);
        }
    }

    @Override
    protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
        Biome biome = worldObj.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        if (biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN || biome == Biomes.FROZEN_OCEAN) {
            IBlockState state = data.getBlockState(x, y, z);
            if (state.getBlock() == Blocks.STONE || state == OCEAN_SURFACE || state == OCEAN_FILLER || (state == BEDROCK && y > 1)) {
                if (y - 1 < 4) {
                    data.setBlockState(x, y, z, OCEAN_SURFACE);
                } else {
                    if (y < worldObj.getSeaLevel())
                        data.setBlockState(x, y, z, WATER);
                    else data.setBlockState(x, y, z, AIR);
                    if (y < 6 && y > 1) {
                        data.setBlockState(x, y - 1, z, OCEAN_FILLER);
                    }
                }
            }
        }
    }

    private int findFirstSolidBlock(ChunkPrimer primer, int x, int z) {
        for (int y = 255; y >= 0; --y) {
            IBlockState iblockstate = primer.getBlockState(x, y, z);
            if (iblockstate != AIR && iblockstate.getBlock() != Blocks.WATER) {
                return y;
            }
        }

        return 0;
    }
}
