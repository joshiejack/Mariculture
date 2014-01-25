package mariculture.fishery;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import mariculture.api.fishery.ISifterHandler;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.helpers.OreDicHelper;

public class SifterHandler implements ISifterHandler {
	private final HashMap<String, ArrayList<RecipeSifter>> recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeSifter recipe) {
		String check = OreDicHelper.convert(recipe.block);
		if(recipes.containsKey(check)) {
			ArrayList<RecipeSifter> list = recipes.get(check);
			list.add(recipe);
			recipes.put(check, list);
		} else {
			ArrayList<RecipeSifter> list = new ArrayList<RecipeSifter>();
			list.add(recipe);
			recipes.put(check, list);
		}
	}

	@Override
	public ArrayList<RecipeSifter> getResult(ItemStack stack) {
		return recipes.get(OreDicHelper.convert(stack));
	}

	@Override
	public HashMap<String, ArrayList<RecipeSifter>> getRecipes() {
		return recipes;
	}
}
