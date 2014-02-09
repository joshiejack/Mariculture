package mariculture.api.core;

import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public interface IIngotCasterHandler {

	/** Add a caster recipe **/
	public void addRecipe(RecipeIngotCasting recipe);
	
	/** Caster Recipes **/
	public RecipeIngotCasting getResult(FluidStack fluid);
	
	public HashMap<String, RecipeIngotCasting> getRecipes();
}
