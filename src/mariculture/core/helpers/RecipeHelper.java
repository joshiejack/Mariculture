package mariculture.core.helpers;

import mariculture.core.Core;
import mariculture.core.lib.FoodMeta;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper {
	public static void addShapedRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
	}
	
	public static void addShapelessRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(result, input));
	}

	public static void addSmelting(int id, int meta, ItemStack stack, float xp) {
		FurnaceRecipes.smelting().addSmelting(id, meta, stack, xp);
	}
}
