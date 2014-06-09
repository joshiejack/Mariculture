package mariculture.core.util;

import mariculture.core.handlers.OreDicHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public final class RecipeItem {
    /** This determines whether the two items are equivalent **/
    public static boolean equals(Object checkItem, Object recipeItem) {
        if (recipeItem instanceof String) {
            if (checkItem instanceof ItemStack) return OreDicHandler.areEqual((ItemStack) checkItem, (String) recipeItem);
            if (checkItem instanceof FluidStack) {
                FluidStack stack = (FluidStack) checkItem;
                if (stack.getFluid() == null) return false;
                else return stack.getFluid().getName().equals(recipeItem);
            }
        } else if (recipeItem instanceof ItemStack) if (checkItem instanceof ItemStack) {
            ItemStack recipe = (ItemStack) recipeItem;
            ItemStack check = (ItemStack) checkItem;
            if (check.getItem() == recipe.getItem()) {
                if (recipe.getItemDamage() == OreDictionary.WILDCARD_VALUE) return true;
                else return recipe.getItemDamage() == check.getItemDamage();
            } else return false;
        } else return false;

        return false;
    }
}
