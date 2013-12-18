package mariculture.core.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper {
	public static void addShapedRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
	}
	
	public static void addShapelessRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(result, input));
	}
}
