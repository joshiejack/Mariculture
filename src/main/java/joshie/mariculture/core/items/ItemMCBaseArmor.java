package joshie.mariculture.core.items;

import joshie.lib.base.ItemBaseArmor;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.lib.MCModInfo;
import joshie.mariculture.diving.IDisablesHardcoreDiving;

public class ItemMCBaseArmor extends ItemBaseArmor implements IDisablesHardcoreDiving {
    public ItemMCBaseArmor(ArmorMaterial material, int j, int k) {
        super(MCModInfo.MODPATH, MaricultureTab.tabWorld, material, j, k);
    }
    
    @Override
    public int getItemEnchantability() {
        return 0;
    }
}
