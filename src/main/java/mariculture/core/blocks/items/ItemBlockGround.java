package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.GroundMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockGround extends ItemBlockMariculture {
    public ItemBlockGround(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack itemstack) {
        switch (itemstack.getItemDamage()) {
            case GroundMeta.BUBBLES:
                return "gas";
            case GroundMeta.ANCIENT:
                return "ancientSand";
            default:
                return "groundBlocks";
        }
    }
}