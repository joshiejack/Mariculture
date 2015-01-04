package mariculture.plugins.nei;

import java.util.List;

import mariculture.core.items.ItemWorked;
import mariculture.magic.JewelryHandler;
import mariculture.magic.ShapedJewelryRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import codechicken.nei.recipe.ShapedRecipeHandler;

/** Extended from the default recipe handler **/
public class NEIJewelryShapedHandler extends ShapedRecipeHandler {
    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("jewelryCrafting") && getClass() == NEIJewelryShapedHandler.class) {
            for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList())
                if (irecipe instanceof ShapedJewelryRecipe) {
                    CachedShapedRecipe recipe = forgeShapedRecipe((ShapedJewelryRecipe) irecipe);
                    if (recipe == null) {
                        continue;
                    }

                    recipe.computeVisuals();
                    arecipes.add(recipe);
                }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList())
            if (irecipe instanceof ShapedJewelryRecipe) if (irecipe.getRecipeOutput().getItem() instanceof ItemWorked && result.getItem() instanceof ItemWorked) if (JewelryHandler.match(result, irecipe.getRecipeOutput())) {
                CachedShapedRecipe recipe = forgeShapedRecipe((ShapedJewelryRecipe) irecipe);
                if (recipe == null) {
                    continue;
                }

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
            CachedShapedRecipe recipe = null;
            if (irecipe instanceof ShapedJewelryRecipe) {
                recipe = forgeShapedRecipe((ShapedJewelryRecipe) irecipe);
            }

            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem())) {
                continue;
            }

            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    private CachedShapedRecipe forgeShapedRecipe(ShapedJewelryRecipe recipe) {
        int width;
        int height;
        try {
            width = recipe.width;
            height = recipe.height;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Object[] items = recipe.getInput();
        for (Object item : items)
            if (item instanceof List && ((List<?>) item).isEmpty()) // ore
                                                                    // handler,
                                                                    // no ores
            return null;

        return new CachedShapedRecipe(width, height, items, recipe.getRecipeOutput());
    }

    @Override
    public String getOverlayIdentifier() {
        return "jewelryCrafting";
    }

    @Override
    public String getRecipeName() {
        return "Shaped Jewelry Crafting";
    }
}
