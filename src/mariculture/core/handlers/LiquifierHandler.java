package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.ISmelterHandler;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.util.Rand;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LiquifierHandler implements ISmelterHandler {
	private final Map fuels = new HashMap();
	private final Map recipes = new HashMap();

	@Override
	public void addRecipe(RecipeSmelter recipe) {
		if(recipe.input2 != null)
			recipes.put(Arrays.asList(DictionaryHelper.convert(recipe.input), DictionaryHelper.convert(recipe.input2)), recipe);
		recipes.put(DictionaryHelper.convert(recipe.input), recipe);
	}

	@Override
	public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp) {
		RecipeSmelter recipe = (RecipeSmelter) recipes.get(Arrays.asList(DictionaryHelper.convert(input), DictionaryHelper.convert(input2)));
		if(recipe == null)
			recipe = (RecipeSmelter) recipes.get(DictionaryHelper.convert(input));
		if(recipe != null) {
			FluidStack fluid = recipe.fluid.copy();
			if(temp < recipe.temp && temp != -1)
				return null;
			if(recipe.input2 != null) {
				if(input2.stackSize < recipe.input.stackSize)
					return null;
			} else {
				if(input.stackSize < recipe.input.stackSize)
					return null;
				
				if(recipe.random != null) {
					for(int i = 0; i < recipe.random.length; i++) {
						if(Rand.nextInt(recipe.rands[i])) {
							fluid = recipe.random[i];
							if(fluid != null)
								return new RecipeSmelter(recipe.input, null, recipe.temp, fluid, recipe.output, recipe.chance, new int[] { 0 });
						}
					}
					
					return new RecipeSmelter(recipe.input, null, recipe.temp, recipe.random[0], recipe.output, recipe.chance, new int[] { 0 });
				}
				
				if(input.isItemStackDamageable()) {
					double mod = (double)(input.getMaxDamage() - input.getItemDamage()) / input.getMaxDamage();
					fluid.amount = (int) (fluid.amount * mod);
				}
			}
			
			return new RecipeSmelter(recipe.input, recipe.input2, recipe.temp, fluid, recipe.output, recipe.chance);
		}

		return null;
	}
	
	public String getName(FluidStack fluid) {
		return fluid.getFluid().getName();
	}
	
	@Override
	public void addSolidFuel(ItemStack stack, FuelInfo info) {
		fuels.put(DictionaryHelper.convert(stack), info);
	}
	
	@Override
	public void addLiquidFuel(FluidStack fluid, FuelInfo info) {
		fuels.put(getName(fluid), info);
	}

	@Override
	public FuelInfo getFuelInfo(Object obj) {
		if(obj instanceof ItemStack)
			return (FuelInfo)fuels.get(DictionaryHelper.convert((ItemStack)obj));
		if(obj instanceof FluidStack)
			return (FuelInfo)fuels.get(getName((FluidStack)obj));
		return null;
	}

	public int getMeltingPoint(ItemStack stack) {
		RecipeSmelter recipe = (RecipeSmelter) recipes.get(DictionaryHelper.convert(stack));
		
		if (recipe != null) {
			return recipe.temp;
		}

		return -1;
	}
}
