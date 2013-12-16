package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFreezerHandler {

	/** Add a freezer recipe **/
	public void addRecipe(RecipeFreezer recipe);
	
	/** returns the recipe for the catalyst + fluid input */
	public RecipeFreezer getResult(ItemStack input, FluidStack fluid);
}
