package mariculture.world.terrain;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.config.WorldGeneration.WorldGen;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenRavine;

public class MapGenRavineWaterBOP extends MapGenRavine {
    protected void recursiveGenerate(World world, int par2, int par3, int chunkX, int chunkZ, Block[] data) {
        if (MaricultureHandlers.environment.getSalinity(world, chunkX * 16, chunkZ * 16) == Salinity.SALINE) {
            if (rand.nextInt(WorldGen.RAVINE_CHANCE) == 0) {
                double d0 = par2 * 16 + rand.nextInt(16);
                double d1 = rand.nextInt(rand.nextInt(80) + 16) + 40;
                double d2 = par3 * 16 + rand.nextInt(16);
                byte b0 = 1;

                for (int i1 = 0; i1 < b0; ++i1) {
                    float f = rand.nextFloat() * (float) Math.PI * 2.0F;
                    float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                    float f2 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
                    try {
                        func_151540_a(rand.nextLong(), chunkX, chunkZ, data, d0, d1, d2, f2, f, f1, 0, 0, 15.0D);
                    } catch (Exception e) {
                        try {
                            func_151540_a(rand.nextLong(), chunkX, chunkZ, data, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } else {
            super.func_151538_a(world, par2, par3, chunkX, chunkZ, data);
        }
    }

    private boolean isTopBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ) {
        BiomeGenBase biome = worldObj.getWorldChunkManager().getBiomeGenAt(x + chunkX * 16, z + chunkZ * 16);
        return isExceptionBiome(biome) ? data[index] == Blocks.grass : data[index] == biome.topBlock;
    }

    private boolean isExceptionBiome(BiomeGenBase biome) {
        if (biome == BiomeGenBase.mushroomIsland) return true;
        if (biome == BiomeGenBase.beach) return true;
        if (biome == BiomeGenBase.desert) return true;
        return false;
    }

    @Override
    protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
        BiomeGenBase biome = worldObj.getWorldChunkManager().getBiomeGenAt(x + chunkX * 16, z + chunkZ * 16);
        Block top = isExceptionBiome(biome) ? Blocks.grass : biome.topBlock;
        Block filler = isExceptionBiome(biome) ? Blocks.dirt : biome.fillerBlock;
        Block block = data[index];

        if (block == Blocks.stone || block == filler || block == top) {
            data[index] = Blocks.water;
            if (foundTop && data[index - 1] == filler) {
                data[index - 1] = top;
            }
        }

    }
}
