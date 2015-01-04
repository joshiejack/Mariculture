package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.WaterMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockWater extends ItemBlockMariculture {
    public ItemBlockWater(Block block) {
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
