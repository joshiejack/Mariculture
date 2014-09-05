package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.BlockMCBaseMeta;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.core.lib.UpgradeMeta;
import joshie.mariculture.core.util.MCTranslate;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockPearlBlock extends BlockMCBaseMeta {
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
