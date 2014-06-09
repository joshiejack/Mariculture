package mariculture.core.world;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.config.GeneralStuff;
import mariculture.core.lib.GroundMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenGas extends WorldGenerator {
    /** The block ID of the ore to be placed using this generator. */
    private Block mineableBlock;
    private int minableBlockMeta = 0;

    /** The number of blocks to generate. */
    private int numberOfBlocks;

    public WorldGenGas(Block par1, int par2) {
        this(par1, par2, Blocks.stone);
    }

    public WorldGenGas(Block par1, int par2, Block block) {
        mineableBlock = par1;
        numberOfBlocks = par2;
    }

    public WorldGenGas(Block block, int meta, int number, Block target) {
        this(block, number, target);
        minableBlockMeta = meta;
    }

    @Override
    public boolean generate(World world, Random par2Random, int par3, int par4, int par5) {
        boolean floor = false;
        int selectX = par3;
        int selectY = par4;
        int selectZ = par5;
        int xLast = 0;
        int zLast = 0;

        float f = par2Random.nextFloat() * (float) Math.PI;
        double d0 = par3 + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d1 = par3 + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d2 = par5 + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d3 = par5 + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d4 = par4 + par2Random.nextInt(3) - 2;
        double d5 = par4 + par2Random.nextInt(3) - 2;

        for (int l = 0; l <= numberOfBlocks; ++l) {
            double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
            double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
            double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
            double d9 = par2Random.nextDouble() * numberOfBlocks / 16.0D;
            double d10 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int x2 = i1; x2 <= l1; ++x2) {
                double d12 = (x2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int y2 = j1; y2 <= i2; ++y2) {
                        double d13 = (y2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int z3 = k1; z3 <= j2; ++z3) {
                                double d14 = (z3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) if (world.getBlock(x2, y2, z3) != null) if (world.getBlock(x2, y2, z3).isSideSolid(world, x2, y2, z3, ForgeDirection.UNKNOWN)) {
                                    world.setBlock(x2, y2, z3, mineableBlock, minableBlockMeta, 2);
                                    xLast = x2;
                                    zLast = z3;
                                }
                            }
                        }
                    }
                }
            }
        }

        world.setBlock(xLast, world.getTopSolidOrLiquidBlock(xLast, zLast) - 1, zLast, Core.sands, GroundMeta.BUBBLES, 2);
        if (GeneralStuff.DEBUG_ON) {
            world.setBlock(xLast, 65, zLast, Blocks.obsidian, 1, 2);
        }

        return true;
    }
}
