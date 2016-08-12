package joshie.mariculture.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemComponent<E extends Enum<E>> extends ItemMCEnum<ItemComponent, E> {
    private final int sort;

    public ItemComponent(CreativeTabs tab, int sort, Class<E> clazz) {
        super(tab, clazz);
        this.sort = sort;
    }

    public ItemComponent(int sort, Class<E> clazz) {
        this(MCTab.getExploration(), sort, clazz);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return this.sort;
    }
}
