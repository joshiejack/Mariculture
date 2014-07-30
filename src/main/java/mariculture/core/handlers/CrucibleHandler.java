package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.ICrucibleHandler;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.ItemHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.util.Rand;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleHandler implements ICrucibleHandler {
    public static Map fuels = new HashMap();
    public static ArrayList<RecipeSmelter> recipes = new ArrayList();

    @Override
    public void addRecipe(RecipeSmelter recipe) {
        recipes.add(recipe);
    }

    @Override
    public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp) {
        for (RecipeSmelter recipe : recipes) {
            if (temp >= 0 && temp < recipe.temp) {
                continue;
            }

            if (ItemHelper.areEqual(recipe.input, input) && input.stackSize >= recipe.input.stackSize) {
                FluidStack fluid = recipe.fluid.copy();
                if (recipe.random != null) {
                    for (int i = 0; i < recipe.random.length; i++) {
                        if (Rand.nextInt(recipe.rands[i])) {
                            fluid = recipe.random[i];
                            if (fluid != null) return new RecipeSmelter(recipe.input, null, recipe.temp, fluid, recipe.output, recipe.chance, new Integer[] { 0 });
                        }
                    }

                    return new RecipeSmelter(recipe.input, null, recipe.temp, recipe.random[0], recipe.output, recipe.chance, new Integer[] { 0 });
                } else {
                    if (input.isItemStackDamageable()) {
                        double mod = (double) (input.getMaxDamage() - input.getItemDamage()) / input.getMaxDamage();
                        fluid.amount = (int) (fluid.amount * mod);
                    }

                    return recipe;
                }
            } else {
                continue;
            }
        }

        return null;
    }

    public String getName(FluidStack fluid) {
        if (fluid.getFluid() == null) return "null";
        return fluid.getFluid().getName();
    }

    @Override
    public void addFuel(Object fuel, FuelInfo info) {
        if (fuel instanceof ItemStack) {
            fuels.put(OreDicHelper.convert(fuel), info);
        }
        if (fuel instanceof FluidStack) {
            fuels.put(getName((FluidStack) fuel), info);
        } else if (fuel instanceof String) {
            fuels.put(fuel, info);
        }
    }

    @Override
    public FuelInfo getFuelInfo(Object obj) {
        if (obj == null) return null;
        if (obj instanceof ItemStack) return (FuelInfo) fuels.get(OreDicHelper.convert(obj));
        if (obj instanceof FluidStack) return (FuelInfo) fuels.get(getName((FluidStack) obj));
        return null;
    }

    @Override
    public int getMeltingPoint(ItemStack stack) {
        for (RecipeSmelter recipe : recipes)
            if (ItemHelper.areEqual(stack, recipe.input)) return recipe.temp;

        return -1;
    }

    @Override
    public ArrayList<RecipeSmelter> getRecipes() {
        return recipes;
    }
}
