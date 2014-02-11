package mariculture.core.world;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GroundMeta;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenGas extends WorldGenerator
{
    /** The block ID of the ore to be placed using this generator. */
    private int minableBlockId;
    private int minableBlockMeta = 0;

    /** The number of blocks to generate. */
    private int numberOfBlocks;

    /** The block ID of the block to be replaced with the ore (usually stone) */
    private int blockToReplace;

    public WorldGenGas(int par1, int par2, int par3)
    {
        this.minableBlockId = par1;
        this.numberOfBlocks = par2;
        this.blockToReplace = par3;
    }

    public WorldGenGas(int id, int meta, int number, int target)
    {
        this(id, number, target);
        this.minableBlockMeta = meta;
    }

    public boolean generate(World world, Random par2Random, int par3, int par4, int par5)
    {
    	boolean floor = false;
    	int selectX = par3;
    	int selectY = par4;
    	int selectZ = par5;
    	int xLast = 0;
    	int zLast = 0;
    	
        float f = par2Random.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(par3 + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(par3 + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(par5 + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(par5 + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(par4 + par2Random.nextInt(3) - 2);
        double d5 = (double)(par4 + par2Random.nextInt(3) - 2);

        for (int l = 0; l <= this.numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * (double)l / (double)this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
            double d9 = par2Random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int x2 = i1; x2 <= l1; ++x2)
            {
                double d12 = ((double)x2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int y2 = j1; y2 <= i2; ++y2)
                    {
                        double d13 = ((double)y2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int z3 = k1; z3 <= j2; ++z3)
                            {
                                double d14 = ((double)z3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                	int blockID = world.getBlockId(x2, y2, z3);
                                	if(Blocks.blocksList[blockID] != null) {
                                		if(Blocks.blocksList[blockID].isBlockSolidOnSide(world, x2, y2, z3, ForgeDirection.UNKNOWN)) {
                                			world.setBlock(x2, y2, z3, this.minableBlockId, minableBlockMeta, 2);
                                			xLast = x2;
                                			zLast = z3;
                                		}
                                	}
                                }
                            }
                        }
                    }
                }
            }
        }
        
        world.setBlock(xLast, world.getTopSolidOrLiquidBlock(xLast, zLast) - 1, zLast, Core.groundBlocks.blockID, GroundMeta.BUBBLES, 2);
        if(Extra.DEBUG_ON)
        	world.setBlock(xLast, 65, zLast, Blocks.obsidian.blockID, 1, 2);

        return true;
    }
}
