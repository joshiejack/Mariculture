package joshie.mariculture.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public class MCTab extends CreativeTabs {
    private ItemStack icon;

    public MCTab(String label) {
        super(label);
        setBackgroundImageName("mariculture.png");
        setNoTitle();
        icon = new ItemStack(Blocks.SAND); //Default icon
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return MODID + ".tab." + this.getTabLabel();
    }

    @Override
    public ItemStack getIconItemStack() {
        return icon;
    }

    @Override
    public Item getTabIconItem() {
        return icon.getItem();
    }

    public void setStack(ItemStack stack) {
        this.icon = stack;
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    public int getSearchbarWidth() {
        return 69;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(List<ItemStack> list) {
        super.displayAllRelevantItems(list);
        Collections.sort(list, new Alphabetical());
    }

    private static class Alphabetical implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            ItemStack stack1 = ((ItemStack) o1);
            ItemStack stack2 = ((ItemStack) o2);

            Item item1 = stack1.getItem();
            Item item2 = stack2.getItem();

            int value1 = 500;
            int value2 = 500;

            if (item1 instanceof CreativeSorted) {
                value1 = ((CreativeSorted) item1).getSortValue(stack1);
            }

            if (item2 instanceof CreativeSorted) {
                value2 = ((CreativeSorted) item2).getSortValue(stack2);
            }

            return value1 == value2 ? stack1.getDisplayName().compareTo(stack2.getDisplayName()) : value1 > value2 ? 1 : -1;
        }
    }

    private static final Cache<String, MCTab> TABS = CacheBuilder.newBuilder().build();
    public static MCTab getTab(final String name) {
        try {
            return TABS.get(name, new Callable<MCTab>() {
                @Override
                public MCTab call() throws Exception {
                    return new MCTab(name);
                }
            });
        } catch (Exception e) { return null; }
    }
}