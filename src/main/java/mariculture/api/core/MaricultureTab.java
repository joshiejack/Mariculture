package mariculture.api.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MaricultureTab extends CreativeTabs {
    public static MaricultureTab tabCore;
    public static MaricultureTab tabFactory;
    public static MaricultureTab tabFishery;
    public static MaricultureTab tabMagic;
    public static MaricultureTab tabWorld;

    private ItemStack icon = new ItemStack(Items.fish);

    public MaricultureTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        return icon;
    }

    @Override
    public Item getTabIconItem() {
        return icon.getItem();
    }

    public void setIcon(ItemStack stack, boolean forced) {
        if (forced) {
            icon = stack;
        } else if (icon == null) {
            icon = stack;
        }
    }
}
