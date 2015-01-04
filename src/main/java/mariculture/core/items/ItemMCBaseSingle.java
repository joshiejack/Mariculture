package mariculture.core.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MCModInfo;
import mariculture.lib.base.ItemBaseSingle;

public class ItemMCBaseSingle extends ItemBaseSingle {
    public ItemMCBaseSingle() {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore);
    }
}
