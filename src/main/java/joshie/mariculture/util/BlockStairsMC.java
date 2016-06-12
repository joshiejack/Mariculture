package joshie.mariculture.util;

import joshie.mariculture.lib.CreativeOrder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStairsMC extends BlockStairs implements MCBlock<BlockStairsMC> {
    private final IBlockState original;
    private final int sort;

    public BlockStairsMC(IBlockState modelState) {
        super(modelState);
        this.sort = CreativeOrder.STAIRS + modelState.getBlock().getMetaFromState(modelState);
        this.original = modelState;
        this.setCreativeTab(MCTab.getTab("core"));
        this.setHarvestLevel("pickaxe", 0, getDefaultState());
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return sort;
    }

    @Override
    public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
        return original.getBlockHardness(worldIn, pos);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 0;
    }
}
