package joshie.mariculture.util;

import joshie.mariculture.lib.CreativeOrder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockStairsMC extends BlockStairs implements MCBlock<BlockStairsMC> {
    private final int sort;
    public BlockStairsMC(IBlockState modelState, int sort) {
        super((modelState));
        this.sort = CreativeOrder.LIMESTONE_STAIRS + sort;
        this.setCreativeTab(MCTab.getTab("core"));
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return sort;
    }
}
