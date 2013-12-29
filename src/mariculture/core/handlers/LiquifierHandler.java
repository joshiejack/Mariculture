package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mariculture.api.core.ISmelterHandler;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.helpers.DictionaryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class LiquifierHandler implements ISmelterHandler {
	private final Map fuelList = new HashMap();
	private final Map recipes = new HashMap();

	@Override
	public void addRecipe(RecipeSmelter recipe) {
		recipes.put(DictionaryHelper.convert(recipe.input), recipe);
	}

	@Override
	public SmelterOutput getResult(ItemStack input, int temp) {
		RecipeSmelter recipe = (RecipeSmelter) recipes.get(DictionaryHelper.convert(input));
		
		if (recipe != null) {
			if (temp >= recipe.temp || temp < 0) {
				if(input.isItemStackDamageable()) {
					SmelterOutput output = recipe.output;
					double mod = (double)(input.getMaxDamage() - input.getItemDamage()) / input.getMaxDamage();
					int fluid = (int) (output.fluid.amount * mod);
					return new SmelterOutput(new FluidStack(FluidRegistry.getFluid(output.fluid.fluidID), (fluid > 0)? fluid: 1), output.output, output.chance);
				}
				
				return recipe.output;
			}
		}

		return null;
	}

	@Override
	public void addFuel(Object fuel, int perTemp, int maxTemp) {
		fuelList.put(DictionaryHelper.convert(fuel), Arrays.asList(perTemp, maxTemp));
	}

	@Override
	public void removeFuel(Object fuel) {
		fuelList.remove(DictionaryHelper.convert(fuel));
	}

	public int getBurnTemp(ItemStack stack, boolean max, boolean real) {
		final List<Object> result = (List<Object>) fuelList.get(DictionaryHelper.convert(stack));
		if (result != null) {
			if (real) {
				return (Integer) ((max) ? result.get(1) : result.get(0));
			}

			return ((Integer) result.get(0) * TileLiquifier.MAX_TEMP) / 2000;
		}
		
		return -1;
	}

	public int getMeltingPoint(ItemStack stack) {
		RecipeSmelter recipe = (RecipeSmelter) recipes.get(DictionaryHelper.convert(stack));
		
		if (recipe != null) {
			return recipe.temp;
		}

		return -1;
	}

	@Override
	public int getBurnTemp(ItemStack stack, boolean max) {
		List<Object> result = (List<Object>) fuelList.get(DictionaryHelper.convert(stack));
		if (result != null) {
				return (Integer) ((max) ? result.get(1) : result.get(0));
		}

		return -1;
	}
}
