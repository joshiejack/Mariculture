package mariculture.core.blocks.base;

import mariculture.api.core.MaricultureTab;
import mariculture.lib.base.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public abstract class ItemBlockMariculture extends ItemBlockBase {
    public ItemBlockMariculture(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] { MaricultureTab.tabCore, MaricultureTab.tabFactory, MaricultureTab.tabFishery, MaricultureTab.tabMagic, MaricultureTab.tabWorld };
    }
}
