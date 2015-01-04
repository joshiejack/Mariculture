package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockWood extends ItemBlockMariculture {
    public ItemBlockWood(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case WoodMeta.POLISHED_LOG:
                return "polishedLog";
            case WoodMeta.POLISHED_PLANK:
                return "polishedPlank";
            case WoodMeta.BASE_WOOD:
                return "baseWood";
            default:
                return "woodBlocks";
        }
    }
}
