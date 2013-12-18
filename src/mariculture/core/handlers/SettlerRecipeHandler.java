package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.IFreezerHandler;
import mariculture.api.core.RecipeFreezer;
import mariculture.core.helpers.DictionaryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SettlerRecipeHandler implements IFreezerHandler {
	private final Map recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeFreezer recipe) {
		if(recipe.fluid != null) {
			recipes.put(Arrays.asList(recipe.fluid.fluidID, DictionaryHelper.convert(recipe.catalyst)), recipe);
			if(recipe.catalyst != null) {
				recipes.put(DictionaryHelper.convert(recipe.catalyst), recipe);
			}
		}
	}

	@Override
	public RecipeFreezer getResult(ItemStack input, FluidStack fluid) {
		if(fluid == null) {
			RecipeFreezer recipe = (RecipeFreezer) recipes.get(DictionaryHelper.convert(input));
			if(recipe != null) {
				return recipe;
			}
			
			return null;
		}
		
		RecipeFreezer recipe = (RecipeFreezer) recipes.get(Arrays.asList(fluid.fluidID,  DictionaryHelper.convert(input)));
		if(recipe != null) {
			return recipe;
		}

		return null;
	}
}
