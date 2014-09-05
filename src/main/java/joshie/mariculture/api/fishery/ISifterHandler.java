package joshie.mariculture.api.fishery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

public interface ISifterHandler {
    public void addRecipe(RecipeSifter recipe);

    public ArrayList<RecipeSifter> getResult(ItemStack stack);

    public HashMap<List<? extends Object>, ArrayList<RecipeSifter>> getRecipes();
}
