package mariculture.core.blocks.base;

import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MCModInfo;
import mariculture.lib.base.BlockBaseMeta;
import net.minecraft.block.material.Material;

public abstract class BlockMCBaseMeta extends BlockBaseMeta {
    public BlockMCBaseMeta(Material material) {
        super(material, MCModInfo.MODPATH, MaricultureTab.tabCore);
    }
}
