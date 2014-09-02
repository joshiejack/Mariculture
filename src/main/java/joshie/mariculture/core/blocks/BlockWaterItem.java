package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.WaterMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockWaterItem extends ItemBlockMariculture {
    public BlockWaterItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case WaterMeta.OYSTER:
                return "oyster";
            default:
                return getUnlocalizedName();
        }
    }
}
