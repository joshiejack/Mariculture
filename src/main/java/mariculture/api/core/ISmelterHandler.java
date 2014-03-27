package mariculture.api.core;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public interface ISmelterHandler {	
	/** Add a Melting Recipe
	 * Take note that if you set the liquid output to the same as the ore rate, 
	 * 	then your item will be affected by the purity upgrade */
	public void addRecipe(RecipeSmelter recipe);
	
	//The Crucible calls the dual smelting first to check if that's an option, if that fails it moves on to the normal result
	public RecipeSmelter getDualResult(ItemStack input, ItemStack input2, int temp);
	public RecipeSmelter getResult(ItemStack input, int temp);
	
	//Can be either a FluidStack, ItemStack, FluidName or Ore Dictionary Name
	public void addFuel(Object stack, FuelInfo info);
	
	public FuelInfo getFuelInfo(Object obj);
	/** Get the melting point of an item */
	public int getMeltingPoint(ItemStack stack);
	
	/** returns all the recipes **/
	public HashMap<String, RecipeSmelter> getRecipes();
}
