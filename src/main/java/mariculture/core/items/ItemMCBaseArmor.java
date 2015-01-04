package mariculture.core.items;

import mariculture.api.core.MaricultureTab;
import mariculture.api.core.interfaces.IDisablesHardcoreDiving;
import mariculture.core.lib.MCModInfo;
import mariculture.lib.base.ItemBaseArmor;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMCBaseArmor extends ItemBaseArmor implements IDisablesHardcoreDiving {
    public ItemMCBaseArmor(String mod, CreativeTabs tab, ArmorMaterial material, int j, int k) {
        super(mod, tab, material, j, k);
    }
    
    public ItemMCBaseArmor(ArmorMaterial material, int j, int k) {
        super(MCModInfo.MODPATH, MaricultureTab.tabWorld, material, j, k);
    }
    
    @Override
    public int getItemEnchantability() {
        return 0;
    }
}
