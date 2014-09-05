package joshie.mariculture.core.items;

import joshie.lib.base.ItemBaseMeta;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;
import net.minecraft.creativetab.CreativeTabs;

public abstract class ItemMCMeta extends ItemBaseMeta {
    public ItemMCMeta() {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] { MaricultureTab.tabCore, MaricultureTab.tabFactory, MaricultureTab.tabFishery, MaricultureTab.tabMagic, MaricultureTab.tabWorld };
    }
}
