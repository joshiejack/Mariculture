package mariculture.magic;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import mariculture.api.core.MaricultureTab;
import mariculture.core.items.ItemMCDamageable;
import mariculture.core.util.MCTranslate;

public class ItemWaterPlacer extends ItemMCDamageable {
    public ItemWaterPlacer(int dmg) {
        super(dmg);
        setCreativeTab(MaricultureTab.tabMagic);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add(MCTranslate.translate("placer.description1"));
        list.add(MCTranslate.translate("placer.description2"));
    }
}
