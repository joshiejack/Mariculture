package joshie.mariculture.core.items;

import joshie.lib.base.ItemBaseSingle;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;

public class ItemMCBaseSingle extends ItemBaseSingle {
    public ItemMCBaseSingle() {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore);
    }
}
