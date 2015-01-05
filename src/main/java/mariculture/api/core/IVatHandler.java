package mariculture.api.core;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IVatHandler {
    /** add a Vat Recipe **/
    public void addRecipe(RecipeVat recipe);

    /** get the result of the following items, take note all of them can be null **/
    public RecipeVat getResult(FluidStack fluid1, FluidStack fluid2, ItemStack item);

    /** returns the list of recipes **/
    public ArrayList<RecipeVat> getRecipes();
}
