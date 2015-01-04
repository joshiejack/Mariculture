package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.TickingMeta;
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
