package joshie.mariculture.core.blocks.base;

import joshie.lib.base.ItemBlockBase;
import joshie.mariculture.api.core.MaricultureTab;
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
