package joshie.mariculture.modules.sealife.world.gen;

import joshie.mariculture.modules.sealife.Sealife;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenStarfish extends WorldGenerator {
    @Override //Base on cactus generation
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 20; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block down = worldIn.getBlockState(blockpos.down()).getBlock();
            Block up = worldIn.getBlockState(blockpos.up()).getBlock();
            if (down == Blocks.SAND && up == Blocks.WATER) {
                worldIn.setBlockState(blockpos, Sealife.ANIMAL.getDefaultState(), 2);
            }
        }

        return true;
    }
}
