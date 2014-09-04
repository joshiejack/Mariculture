package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockPearlBlock extends ItemBlockMariculture {
    public ItemBlockPearlBlock(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        return PearlColor.get(stack.getItemDamage());
    }
}