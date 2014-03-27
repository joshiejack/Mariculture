package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.ISmelterHandler;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.util.Rand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class LiquifierHandler implements ISmelterHandler {
	private final Map fuels = new HashMap();
	private final HashMap<String, RecipeSmelter> recipes = new HashMap();

	@Override
	public void addRecipe(RecipeSmelter recipe) {
		if (recipe.input2 != null) {
			String input = "";
			String input2 = "";
			if (recipe.input instanceof String) input = input + recipe.input;
			if (recipe.input instanceof ItemStack) input = input + OreDicHandler.convert((ItemStack) recipe.input);
			if (recipe.input2 instanceof String) input2 = input2 + recipe.input2;
			if (recipe.input2 instanceof ItemStack) input2 = input2 + OreDicHandler.convert((ItemStack) recipe.input2);
			recipes.put(input + "|" + input2, recipe);
		} else {
			if (recipe.input instanceof String) recipes.put((String) recipe.input, recipe);
			if (recipe.input instanceof ItemStack) recipes.put(OreDicHandler.convert((ItemStack) recipe.input), recipe);
		}
	}

	//Dual Recipes
	public RecipeSmelter getRecipe(ItemStack input, ItemStack input2) {
		RecipeSmelter recipe = null;
		ArrayList<String> input1Names = OreDicHandler.all.get(OreDicHandler.convert(input));
		ArrayList<String> input2Names = OreDicHandler.all.get(OreDicHandler.convert(input2));
		if (input1Names != null) {
			for (String name1 : input1Names) {
				if (input2Names != null) {
					for (String name2 : input2Names) {
						recipe = recipes.get(name1 + "|" + name2);
						if (recipe != null) return recipe;
						recipe = recipes.get(OreDicHandler.convert(input) + "|" + name2);
						if (recipe != null) return recipe;
					}
				}

				recipe = recipes.get(name1 + "|" + OreDicHandler.convert(input2));
				if (recipe != null) return recipe;
			}
		}

		recipe = recipes.get(OreDicHandler.convert(input) + "|" + OreDicHandler.convert(input2));
		if (recipe != null) return recipe;
		return recipe;
	}
	
	//Single Recipes
	public RecipeSmelter getRecipe(ItemStack input) {
		RecipeSmelter recipe;
		ArrayList<String> input1Names = OreDicHandler.all.get(OreDicHandler.convert(input));
		if (input1Names != null) {
			for (String name : input1Names) {
				recipe = recipes.get(name);
				if (recipe != null) return recipe;
				}
		}

		recipe = recipes.get(OreDicHandler.convert(input));
		if (recipe != null) return recipe;
		return recipe;
	}
	
	@Override
	public RecipeSmelter getDualResult(ItemStack input, ItemStack input2, int temp) {
		RecipeSmelter recipe = getRecipe(input, input2);
		if(recipe != null) {
			return getResult(recipe, input, temp);
		} else return null;
	}
	
	@Override
	public RecipeSmelter getResult(ItemStack input, int temp) {
		RecipeSmelter recipe = getRecipe(input);
		if (recipe != null) {
			return getResult(recipe, input, temp);
		} else return null;
	}
	
	public RecipeSmelter getResult(RecipeSmelter recipe, ItemStack input, int temp) {
		FluidStack fluid = recipe.fluid.copy();
		if (temp < recipe.temp && temp != -1)
			return null;
		if (recipe.random != null) {
			for (int i = 0; i < recipe.random.length; i++) {
				if (Rand.nextInt(recipe.rands[i])) {
					fluid = recipe.random[i];
					if (fluid != null)
						return new RecipeSmelter(recipe.input, null, recipe.temp, fluid, recipe.output, recipe.chance, new Integer[] { 0 });
				}
			}

			return new RecipeSmelter(recipe.input, null, recipe.temp, recipe.random[0], recipe.output, recipe.chance, new Integer[] { 0 });
		}

		if (input != null && input.isItemStackDamageable()) {
			double mod = (double) (input.getMaxDamage() - input.getItemDamage()) / input.getMaxDamage();
			fluid.amount = (int) (fluid.amount * mod);
		}

		return new RecipeSmelter(recipe.input, recipe.input2, recipe.temp, fluid, recipe.output, recipe.chance);
	}

	public boolean equals(Object obj, ItemStack stack) {
		if (obj instanceof Item) {
			return (Item) obj == stack.getItem();
		} else if (obj instanceof String) {
			ArrayList<String> names = OreDicHandler.all.get(OreDicHandler.convert(stack));
			for (String name : names) {
				return ((String) obj).equals(name);
			}
		}

		return false;
	}

	public String getName(FluidStack fluid) {
		if (fluid.getFluid() == null)
			return "null";
		return fluid.getFluid().getName();
	}

	@Override
	public void addFuel(Object fuel, FuelInfo info) {
		if (fuel instanceof ItemStack)
			fuels.put(OreDicHelper.convert((ItemStack) fuel), info);
		if (fuel instanceof FluidStack)
			fuels.put(getName((FluidStack) fuel), info);
		else if (fuel instanceof String)
			fuels.put((String) fuel, info);
	}

	@Override
	public FuelInfo getFuelInfo(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof ItemStack)
			return (FuelInfo) fuels.get(OreDicHelper.convert((ItemStack) obj));
		if (obj instanceof FluidStack)
			return (FuelInfo) fuels.get(getName((FluidStack) obj));
		return null;
	}

	public int getMeltingPoint(ItemStack stack) {
		RecipeSmelter recipe = getRecipe(stack);
		if (recipe != null) {
			return recipe.temp;
		}

		return -1;
	}

	@Override
	public HashMap<String, RecipeSmelter> getRecipes() {
		return (HashMap<String, RecipeSmelter>) recipes;
	}
}
