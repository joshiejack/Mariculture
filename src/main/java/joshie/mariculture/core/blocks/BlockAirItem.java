package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.AirMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockAirItem extends ItemBlockMariculture {
    public BlockAirItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case AirMeta.NATURAL_GAS:
                return "naturalGas";
            case AirMeta.FAKE_AIR:
                return "air";
            default:
                return "air";
        }
    }
}