package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.GlassMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockGlass extends ItemBlockMariculture {
    public ItemBlockGlass(Block block) {
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