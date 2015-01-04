package mariculture.core.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MCModInfo;
import mariculture.lib.base.ItemBaseDamageable;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMCDamageable extends ItemBaseDamageable {
    public ItemMCDamageable(int dmg) {
        super(MCModInfo.MODPATH, MaricultureTab.tabCore, dmg);
    }
    
    public ItemMCDamageable(String mod, CreativeTabs tab, int dmg) {
        super(mod, tab, dmg);
    }
}
