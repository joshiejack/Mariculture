package mariculture.factory;

import java.util.HashMap;

import mariculture.api.core.IOvenHandler;
import mariculture.api.core.RecipeOven;
import mariculture.core.helpers.DictionaryHelper;
import net.minecraft.item.ItemStack;

public class OvenRecipeHandler implements IOvenHandler {
	public HashMap recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeOven recipe) {
		recipes.put(DictionaryHelper.convert(recipe.input), recipe);
	}

	@Override
	public RecipeOven getResult(ItemStack input) {
		if(recipes.containsKey(DictionaryHelper.convert(input)))
			return (RecipeOven) recipes.get(DictionaryHelper.convert(input));
		return null;
	}
}
