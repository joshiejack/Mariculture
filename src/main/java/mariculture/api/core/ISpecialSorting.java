package mariculture.api.core;

import net.minecraft.item.ItemStack;

public interface ISpecialSorting {
    boolean isSame(ItemStack item, ItemStack stack, boolean isPerfectMatch);
}
