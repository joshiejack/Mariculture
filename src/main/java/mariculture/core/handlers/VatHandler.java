package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Level;

import mariculture.api.core.IVatHandler;
import mariculture.api.core.RecipeVat;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatHandler implements IVatHandler {
	private final HashMap<List<? extends Object>, RecipeVat> recipes = new HashMap();
	public String getName(FluidStack fluid) {
		return fluid.getFluid().getName();
	}
	
	public String getName(ItemStack stack) {
		return OreDicHelper.convert(stack);
	}
	
	@Override
	public void addRecipe(RecipeVat recipe) {
		if(recipe.outputFluid == null && recipe.outputItem == null) {
			LogHandler.log(Level.ERROR, "A mod attempted to add an invalid Vat recipe, with both a null output item and fluid");
			LogHandler.log(Level.ERROR, recipe.toString());
			return;
		}
		
		if(recipe.inputFluid2 != null && recipe.inputItem != null)
			recipes.put(Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputFluid2), getName(recipe.inputItem)), recipe);
		else if(recipe.inputItem != null)
			recipes.put(Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputItem)), recipe);
		else if(recipe.inputFluid2 != null)
			recipes.put(Arrays.asList(getName(recipe.inputFluid1), getName(recipe.inputFluid2)), recipe);
		else 
			recipes.put(Arrays.asList(recipe.inputFluid1), recipe);
	}
	
	@Override
	public RecipeVat getResult(FluidStack fluid1, FluidStack fluid2, ItemStack input) {
		if(fluid1 == null)
			return null;
		RecipeVat result = null;
		if(result == null && fluid2 != null && input != null) {
			result = (RecipeVat) recipes.get(Arrays.asList(getName(fluid1), getName(fluid2), getName(input)));
		}
		
		if(result == null && input != null) {
			result = (RecipeVat) recipes.get(Arrays.asList(getName(fluid1), getName(input)));
		}
		
		if(result == null && fluid2 != null) {
			result = (RecipeVat) recipes.get(Arrays.asList(getName(fluid1), getName(fluid2)));
		}
		
		if(result == null) {
			result = (RecipeVat) recipes.get(Arrays.asList(getName(fluid1)));
		}
		
		if(result != null) {
			if(fluid1.amount < result.inputFluid1.amount)
				return null;
			if(result.inputFluid2 != null && fluid2.amount < result.inputFluid2.amount)
				return null;
			if(result.inputItem != null && input.stackSize < result.inputItem.stackSize)
				return null;
			return result;
		}
		
		return null;
	}

	@Override
	public HashMap<List<? extends Object>, RecipeVat> getRecipes() {
		return recipes;
	}
}
