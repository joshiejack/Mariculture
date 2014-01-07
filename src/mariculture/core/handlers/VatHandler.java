package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.IVatHandler;
import mariculture.api.core.RecipeVat;
import mariculture.api.core.RecipeVat.RecipeVatAlloy;
import mariculture.api.core.RecipeVat.RecipeVatItem;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class VatHandler implements IVatHandler {
	private final Map recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeVat recipe) {
		if(recipe instanceof RecipeVatItem) {
			RecipeVatItem vat = (RecipeVatItem) recipe;
			recipes.put(Arrays.asList(vat.fluid.getFluid().getName(), DictionaryHelper.convert(vat.input)), recipe);
		} else if (recipe instanceof RecipeVatAlloy) {
			RecipeVatAlloy vat = (RecipeVatAlloy) recipe;
			recipes.put(Arrays.asList(vat.fluid2.getFluid().getName(), vat.fluid.getFluid().getName()), recipe);
		}
	}

	@Override
	public RecipeVat getAlloyResult(FluidStack fluid1, FluidStack fluid2) {
		if (fluid1 == null || fluid2 == null)
			return null;
		RecipeVat recipe = (RecipeVat) recipes.get(Arrays.asList(fluid1.getFluid().getName(), fluid2.getFluid().getName()));
		if(recipe != null) {
			ItemStack res = ((RecipeVatAlloy)recipe).output;
			if(fluid1.amount >= ((RecipeVatAlloy)recipe).fluid.amount && fluid2.amount >= ((RecipeVatAlloy)recipe).fluid2.amount)
				return recipe;
		}
		
		return null;
	}

	@Override
	public RecipeVat getItemResult(ItemStack stack, FluidStack fluid) {
		if (stack == null || fluid == null)
			return null;
		RecipeVat recipe = (RecipeVat) recipes.get(Arrays.asList(fluid.getFluid().getName(), DictionaryHelper.convert(stack)));
		if(recipe != null) {
			if(fluid.amount >= ((RecipeVatItem)recipe).fluid.amount)
				return recipe;
		}
		
		return null;
	}

}
