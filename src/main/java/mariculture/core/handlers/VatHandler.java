package mariculture.core.handlers;

import java.util.ArrayList;

import mariculture.api.core.IVatHandler;
import mariculture.api.core.RecipeVat;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

public class VatHandler implements IVatHandler {
    public static ArrayList<RecipeVat> recipes = new ArrayList();

    @Override
    public void addRecipe(RecipeVat recipe) {
        if (recipe.outputFluid == null && recipe.outputItem == null) {
            LogHandler.log(Level.ERROR, "A mod attempted to add an invalid Vat recipe, with both a null output item and fluid");
            LogHandler.log(Level.ERROR, recipe.toString());
            return;
        }

        recipes.add(recipe);
    }

    @Override
    public RecipeVat getResult(FluidStack fluid1, FluidStack fluid2, ItemStack input) {
        if (fluid1 == null) return null;
        else {
            for (RecipeVat vat : recipes)
                if (FluidHelper.areEqual(fluid1, vat.inputFluid1) && fluid1.amount >= vat.inputFluid1.amount) {
                    if (vat.inputFluid2 != null && !(FluidHelper.areEqual(fluid2, vat.inputFluid2) && fluid2.amount >= vat.inputFluid2.amount)) {
                        continue;
                    }

                    if (vat.inputItem != null && !(ItemHelper.areEqual(input, vat.inputItem) && input.stackSize >= vat.inputItem.stackSize)) {
                        continue;
                    }

                    return vat;
                } else {
                    continue;
                }
        }

        return null;
    }

    @Override
    public ArrayList<RecipeVat> getRecipes() {
        return recipes;
    }
}
