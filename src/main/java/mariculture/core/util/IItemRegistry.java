package mariculture.core.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemRegistry {
    public void register(Item item);

    public int getMetaCount();

    public String getName(ItemStack stack);
}
