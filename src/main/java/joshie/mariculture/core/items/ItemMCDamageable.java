package joshie.mariculture.core.items;

import joshie.lib.base.ItemBaseDamageable;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;

public class ItemMCDamageable extends ItemBaseDamageable {
    public ItemMCDamageable(int dmg) {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore, dmg);
    }
}
