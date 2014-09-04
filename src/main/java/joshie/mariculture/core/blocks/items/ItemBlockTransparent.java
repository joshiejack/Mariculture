package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockTransparent extends ItemBlockMariculture {
    public ItemBlockTransparent(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack itemstack) {
        switch (itemstack.getItemDamage()) {
            case TransparentMeta.PLASTIC:
                return "plastic";
            default:
                return "plastic";
        }
    }
}