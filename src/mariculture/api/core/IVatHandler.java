package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IVatHandler {
	public void addRecipe(RecipeVat recipe);
	public RecipeVat getAlloyResult(FluidStack fluid1, FluidStack fluid2);
	public RecipeVat getItemResult(ItemStack stack, FluidStack fluid);
}
