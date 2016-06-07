package joshie.mariculture.modules.core.blocks;

import joshie.mariculture.modules.core.blocks.BlockLimestone.Type;
import joshie.mariculture.util.BlockMCEnum;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockLimestone extends BlockMCEnum<Type> {
    public enum Type implements IStringSerializable {
        RAW, SMOOTH;

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }

    public BlockLimestone() {
        super(Material.ROCK, Type.class);
    }
}
