package mariculture.fishery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.fishery.IFishFoodHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.util.FluidDictionary;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FishFoodHandler implements IFishFoodHandler {
	private static Map foodList = new HashMap();

	@Override
	public void addFishFood(ItemStack food, int value) {
		foodList.put(Arrays.asList(food.itemID, food.getItemDamage()), value);
		RecipeHelper.addMelting(food, 150, FluidRegistry.getFluidStack(FluidDictionary.fish_food, value));
	}

	public static boolean isFishFood(ItemStack stack) {
		if (stack == null) {
			return false;
		}

		if (foodList.get(Arrays.asList(stack.itemID, stack.getItemDamage())) != null) {
			return true;
		}

		return false;
	}

	public static int getValue(ItemStack stack) {
		if (stack != null && foodList.get(Arrays.asList(stack.itemID, stack.getItemDamage())) != null) {
			return (Integer) foodList.get(Arrays.asList(stack.itemID, stack.getItemDamage()));
		}

		return 0;
	}
}