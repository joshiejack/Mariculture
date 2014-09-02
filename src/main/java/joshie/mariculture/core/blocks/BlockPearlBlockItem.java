package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockPearlBlockItem extends ItemBlockMariculture {
    public BlockPearlBlockItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        return PearlColor.get(stack.getItemDamage());
    }
}