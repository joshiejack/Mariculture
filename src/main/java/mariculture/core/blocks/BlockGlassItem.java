package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.GlassMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockGlassItem extends ItemBlockMariculture {
    public BlockGlassItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack itemstack) {
        switch (itemstack.getItemDamage()) {
            case GlassMeta.HEAT:
                return "heatglass";
            default:
                return "heatglass";
        }
    }
}