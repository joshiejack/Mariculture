package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.GroundMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockGroundItem extends ItemBlockMariculture {
    public BlockGroundItem(Block block) {
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