package joshie.mariculture.core.util;

import net.minecraft.item.ItemStack;

/** Implemented on MC Items, and ItemBlocks, to sort in a special order **/
public interface CreativeSorted {
    /** Return a sort value, @default is 500 **/
    int getSortValue(ItemStack stack);
}
