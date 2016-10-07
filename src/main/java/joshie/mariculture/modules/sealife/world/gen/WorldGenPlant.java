package joshie.mariculture.modules.sealife.world.gen;

import joshie.mariculture.modules.sealife.Sealife;
import joshie.mariculture.modules.sealife.blocks.BlockPlant.Plant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPlant extends WorldGenerator {
    private static final IBlockState SAND = Blocks.SAND.getDefaultState();
    private static final IBlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
    private int frequency = 64;
    private IBlockState top;
    private IBlockState middle;
    private IBlockState bottom;

    public WorldGenPlant(Plant top, Plant middle, Plant bottom) {
        this.top = Sealife.PLANTS.getStateFromEnum(top);
        this.middle = Sealife.PLANTS.getStateFromEnum(middle);
        this.bottom = Sealife.PLANTS.getStateFromEnum(bottom);
    }

    public WorldGenPlant(Plant bottom) {
        this.bottom = Sealife.PLANTS.getStateFromEnum(bottom);
    }

    public WorldGenPlant(Plant top, Plant bottom) {
        this.top = Sealife.PLANTS.getStateFromEnum(top);
        this.bottom = Sealife.PLANTS.getStateFromEnum(bottom);
    }

    public WorldGenPlant setFrequency(int i) {
        this.frequency = i;
        return this;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < frequency; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), worldIn.getTopSolidOrLiquidBlock(position).getY(), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255)) {
                if (top != null) {
                    if (isAcceptablePosition(worldIn, blockpos, bottom) && isAcceptablePosition(worldIn, blockpos.up(1), null)) {
                        if (bottom != null) {
                            worldIn.setBlockState(blockpos, bottom, 2);
                            worldIn.setBlockState(blockpos.down(), SAND, 2);
                            worldIn.setBlockState(blockpos.down(2), SANDSTONE, 2);
                        }

                        int j = 1;
                        if (middle != null) {
                            for (j = 1; j < rand.nextInt(16) + 1; j++) {
                                if (isAcceptablePosition(worldIn, blockpos.up(j), middle)) {
                                    worldIn.setBlockState(blockpos.up(j), middle, 2);
                                } else break;
                            }

                            worldIn.setBlockState(blockpos.up(j - 1), top, 2);
                        } else if (isAcceptablePosition(worldIn, blockpos.up(j), top)) worldIn.setBlockState(blockpos.up(j), top, 2);
                    }
                } else if (bottom != null && isAcceptablePosition(worldIn, blockpos, bottom)) {
                    worldIn.setBlockState(blockpos, bottom, 2);
                }
            }
        }

        return true ;
    }

    private boolean isAcceptablePosition(World worldIn, BlockPos blockpos, IBlockState placing) {
        if (blockpos.getY() < 200 && worldIn.getBlockState(blockpos).getBlock() == Blocks.WATER && worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.WATER) {
            if (placing == null) return true;
            Plant plant = Sealife.PLANTS.getEnumFromMeta(Sealife.PLANTS.getMetaFromState(placing));
            if (plant == Plant.GRASS_DOUBLE || plant == Plant.GRASS_SHORT || plant == Plant.KELP_BOTTOM) {
                return worldIn.getBlockState(blockpos.down()).isSideSolid(worldIn, blockpos.down(), EnumFacing.DOWN);
            } else if (plant == Plant.GRASS_TOP) {
                IBlockState state = worldIn.getBlockState(blockpos.down());
                if (state.getBlock() == Sealife.PLANTS) {
                    Plant plant2 = Sealife.PLANTS.getEnumFromMeta(Sealife.PLANTS.getMetaFromState(state));
                    return plant2 == Plant.GRASS_DOUBLE;
                }
            } else if (plant == Plant.KELP_MIDDLE || plant == Plant.KELP_TOP) {
                IBlockState state = worldIn.getBlockState(blockpos.down());
                if (state.getBlock() == Sealife.PLANTS) {
                    Plant plant2 = Sealife.PLANTS.getEnumFromMeta(Sealife.PLANTS.getMetaFromState(state));
                    return plant2 == Plant.KELP_MIDDLE || plant2 == Plant.KELP_BOTTOM;
                }
            }
        }

        return false;
    }
}
