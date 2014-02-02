package mariculture.api.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MaricultureTab extends CreativeTabs
{
	public static MaricultureTab tabMariculture;
	public static MaricultureTab tabFish;
	public static MaricultureTab tabJewelry;
	
    public ItemStack icon;

    public MaricultureTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack () {
        return icon;
    }
}
