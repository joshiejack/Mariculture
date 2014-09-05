package joshie.mariculture.core.items;

import joshie.lib.base.ItemBaseDamageable;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMCDamageable extends ItemBaseDamageable {
    public ItemMCDamageable(int dmg) {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore, dmg);
    }
    
    public ItemMCDamageable(String mod, CreativeTabs tab, int dmg) {
        super(mod, tab, dmg);
    }
}
