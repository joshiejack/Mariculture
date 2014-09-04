package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.TickingMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockTicking extends ItemBlockMariculture {
    public ItemBlockTicking(Block block) {
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
