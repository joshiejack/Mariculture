package joshie.mariculture.core.blocks.base;

import joshie.lib.base.BlockBaseMeta;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;
import net.minecraft.block.material.Material;

public abstract class BlockMCBaseMeta extends BlockBaseMeta {
    public BlockMCBaseMeta(Material material) {
        super(material, MCModInfo.MODPATH, MaricultureTab.tabCore);
    }
}
