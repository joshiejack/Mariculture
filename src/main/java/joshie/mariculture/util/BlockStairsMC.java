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

    public ItemStack getStack(int amount) {
        return new ItemStack(this, 1, amount);
    }

    public ItemStack getStack() {
        return new ItemStack(this);
    }

    @Override
    public BlockStairsMC setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        return this;
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return sort;
    }
}
