package joshie.mariculture.modules.sealife.blocks;

import joshie.mariculture.core.lib.CreativeOrder;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.block.BlockMCEnum;
import joshie.mariculture.modules.sealife.blocks.BlockRock.Rock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class BlockRock extends BlockMCEnum<Rock, BlockRock> {
    public BlockRock() {
        super(Material.ROCK, Rock.class, MCTab.getExploration());
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return CreativeOrder.ROCK;
    }

    public enum Rock implements IStringSerializable {
        REEF;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
