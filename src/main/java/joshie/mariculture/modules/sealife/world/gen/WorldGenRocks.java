package joshie.mariculture.modules.sealife.world.gen;

import joshie.mariculture.modules.sealife.Sealife;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenRocks extends WorldGenerator {
    private static final IBlockState REEF = Sealife.ROCKS.getDefaultState();

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (true) {
            label0:
            {
                if (position.getY() > 3) {
                    if (worldIn.getBlockState(position.down()) == Blocks.WATER) {
                        break label0;
                    }

                    Block block = worldIn.getBlockState(position.down()).getBlock();
                    if (block != Blocks.SAND && block != Blocks.SANDSTONE) {
                        break label0;
                    }
                }

                if (position.getY() <= 3) {
                    return false;
                }

                int i1 = 1;

                for (int i = 0; i1 >= 0 && i < 3; ++i) {
                    int j = i1 + rand.nextInt(2);
                    int k = i1 + rand.nextInt(2);
                    int l = i1 + rand.nextInt(2);
                    float f = (float) (j + k + l) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l))) {
                        if (blockpos.distanceSq(position) <= (double) (f * f)) {
                            if (blockpos.getY() <= 60) {
                                worldIn.setBlockState(blockpos, REEF, 4);
                            }
                        }
                    }

                    position = position.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
                }

                return true;
            }

            position = position.down();
        }
    }
}
