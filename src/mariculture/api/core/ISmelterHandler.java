package mariculture.api.core;

import mariculture.api.core.RecipeSmelter.SmelterOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ISmelterHandler {
	/** Add a fuel by ItemStack or OreDictionary name */
	public void addFuel(Object item, int perTemp, int maxTemp);
	/** Remove a fuel by ItemStack or OreDictionary name */
	public void removeFuel(Object item);
	
	/** Add a Melting Recipe
	 * Take note that if you set the liquid output to the same as the ore rate, 
	 * 	then your item will be affected by the purity upgrade */
	public void addRecipe(RecipeSmelter recipe);
	
	/** Add a duel melting recipe **/
	public void addDualRecipe(RecipeSmelterDual recipe);
	
	/** Get the result of a recipe **/
	public SmelterOutput getResult(ItemStack input, int temp);
	
	/** Dual melt Recipe **/
	public SmelterOutput getResult(ItemStack input1, ItemStack input2, int temp);
	/** Get the maximum burn temperature for a stack, max to true will give you the maximum
	 * temperature this fuel can go up to, while max to false will give how much each piece of fuel gives */
	public int getBurnTemp(ItemStack stack, boolean max);
	
	/** Old Compatibility */
	@Deprecated
	public int getBurnTemp(ItemStack stack, boolean max, boolean real);
	
	/** Get the melting point of an item */
	public int getMeltingPoint(ItemStack stack);
}
