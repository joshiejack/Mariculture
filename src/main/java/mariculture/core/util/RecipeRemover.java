package mariculture.core.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeRemover {
    public static void remove(ItemStack resultItem) {
        ItemStack recipeResult = null;
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();

        for (int scan = 0; scan < recipes.size(); scan++) {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            if (tmpRecipe instanceof ShapedRecipes) {
                ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
                recipeResult = recipe.getRecipeOutput();
            }

            if (tmpRecipe instanceof ShapedOreRecipe) {
                ShapedOreRecipe recipe = (ShapedOreRecipe) tmpRecipe;
                recipeResult = recipe.getRecipeOutput();
            }

            if (tmpRecipe instanceof ShapelessRecipes) {
                ShapelessRecipes recipe = (ShapelessRecipes) tmpRecipe;
                recipeResult = recipe.getRecipeOutput();
            }

            if (tmpRecipe instanceof ShapelessOreRecipe) {
                ShapelessOreRecipe recipe = (ShapelessOreRecipe) tmpRecipe;
                recipeResult = recipe.getRecipeOutput();
            }

            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                recipes.remove(scan);
            }
        }
    }
}
