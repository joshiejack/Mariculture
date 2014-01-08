package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IIngotCasterHandler {

	/** Add a caster recipe **/
	public void addRecipe(RecipeIngotCasting recipe);
	
	/** Caster Recipes **/
	public RecipeIngotCasting getResult(FluidStack fluid);
}
