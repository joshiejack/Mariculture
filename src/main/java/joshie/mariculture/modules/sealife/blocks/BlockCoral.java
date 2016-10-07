package joshie.mariculture.modules.sealife.blocks;

import joshie.mariculture.core.lib.CreativeOrder;
import joshie.mariculture.core.util.block.BlockAquatic;
import joshie.mariculture.modules.sealife.blocks.BlockCoral.Coral;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockCoral extends BlockAquatic<Coral, BlockCoral> {
    public BlockCoral() {
        super(Coral.class);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return CreativeOrder.CORAL;
    }

    public enum Coral implements IStringSerializable {
        BLUE, CYAN, GREEN, ORANGE, PINK, RED;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
