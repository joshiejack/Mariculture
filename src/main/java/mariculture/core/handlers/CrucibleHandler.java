package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.ICrucibleHandler;
import mariculture.api.core.IFuelTickHandler;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.ItemHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.util.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleHandler implements ICrucibleHandler {
    private static final Random rand = new Random();
    public static Map fuels = new HashMap();
    public static Map tickHandlers = new HashMap();
    public static ArrayList<RecipeSmelter> recipes = new ArrayList();

    @Override
    public void addRecipe(RecipeSmelter recipe) {
        recipes.add(recipe);
    }

    @Override
    public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp) {
        return getResult(input, input2, temp, false);
    }

    @Override
    public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp, boolean ethereal) {
        if (input == null) return null;
        if (temp < 0 || temp >= 1500) {
            if (ethereal && input != null && input.isItemEnchanted()) {
                FluidStack stack = Fluids.getBalancedStack("xp");
                stack.amount *= EnchantHelper.getEnchantmentValue(input);
                ItemStack in = input.copy();
                in.stackSize = 1;
                ItemStack ret = input.copy();
                ret.stackSize = 1;
                ret.stackTagCompound.removeTag("ench");
                return new RecipeSmelter(in, 1500, stack, ret, 1);
            }
        }

        for (RecipeSmelter recipe : recipes) {
            if (recipe == null) continue;
            if (temp >= 0 && temp < recipe.temp) {
                continue;
            }

            if (ItemHelper.areEqual(recipe.input, input) && input.stackSize >= recipe.input.stackSize) {
                FluidStack fluid = recipe.fluid.copy();
                if (recipe.random != null) {
                    for (int i = 0; i < recipe.random.length; i++) {
                        if (rand.nextInt(recipe.rands[i]) == 0) {
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
        } else  if (fuel instanceof FluidStack) {
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
	public void addFuelHandler(Object fuel, IFuelTickHandler handler) {
		if (fuel instanceof ItemStack) {
            tickHandlers.put(OreDicHelper.convert(fuel), handler);
        } else  if (fuel instanceof FluidStack) {
        	tickHandlers.put(getName((FluidStack) fuel), handler);
        } else if (fuel instanceof String) {
        	tickHandlers.put(fuel, handler);
        }
	}

	@Override
	public IFuelTickHandler getFuelTickHandler(Object obj) {
		if (obj == null) return null;
        if (obj instanceof ItemStack) return (IFuelTickHandler) tickHandlers.get(OreDicHelper.convert(obj));
        if (obj instanceof FluidStack) return (IFuelTickHandler) tickHandlers.get(getName((FluidStack) obj));
        if (obj instanceof String) return (IFuelTickHandler) tickHandlers.get((String)obj);
        return null;
	}

    @Override
    public int getMeltingPoint(ItemStack stack) {
        return getMeltingPoint(stack, false);
    }

    @Override
    public int getMeltingPoint(ItemStack stack, boolean ethereal) {
        if (ethereal && stack != null && stack.isItemEnchanted()) {
            return 1500;
        }

        for (RecipeSmelter recipe : recipes) {
            if (recipe == null) continue;
            if (ItemHelper.areEqual(stack, recipe.input)) return recipe.temp;
        }

        return -1;
    }

    @Override
    public ArrayList<RecipeSmelter> getRecipes() {
        return recipes;
    }
}
