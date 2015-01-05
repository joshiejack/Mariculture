package mariculture.api.core;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface ICrucibleHandler {
    /** Add a Melting Recipe
     * Take note that if you set the liquid output to the same as the ore rate, 
     * 	then your item will be affected by the purity upgrade */
    public void addRecipe(RecipeSmelter recipe);

    //Can be either a FluidStack, ItemStack, FluidName or Ore Dictionary Name
    public void addFuel(Object stack, FuelInfo info);

    /** Returns the result of this recipe, ethereal affects the type of the return **/
    public RecipeSmelter getResult(ItemStack input1, ItemStack input2, int temperature, boolean ethereal);

    public FuelInfo getFuelInfo(Object obj);

    /** Get the melting point of an item */
    public int getMeltingPoint(ItemStack stack, boolean ethereal);

    /** returns all the recipes **/
    public ArrayList<RecipeSmelter> getRecipes();

    @Deprecated
    public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp);

    @Deprecated
    public int getMeltingPoint(ItemStack stack);
}
