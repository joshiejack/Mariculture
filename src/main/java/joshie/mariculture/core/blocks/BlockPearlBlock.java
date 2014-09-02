package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.BlockDecorative;
import joshie.mariculture.core.lib.PearlColor;
import net.minecraft.block.material.Material;

public class BlockPearlBlock extends BlockDecorative {
    public BlockPearlBlock(String prefix) {
        super(Material.rock);
        setResistance(20F);
        setHardness(2F);
        this.prefix = prefix;
    }

    @Override
    public String getToolType(int meta) {
        return "pickaxe";
    }

    @Override
    public int getToolLevel(int meta) {
        return 1;
    }

    @Override
    public int getMetaCount() {
        return PearlColor.COUNT;
    }
}
