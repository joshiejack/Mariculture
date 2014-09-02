package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.TickingMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockTickingItem extends ItemBlockMariculture {
    public BlockTickingItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case TickingMeta.NET:
                return "net";
            default:
                return getUnlocalizedName();
        }
    }
}
