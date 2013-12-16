package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeFreezer {
	/** fluid: Fluid input of the recipe
	 *  catalyst: Any item that's an 'extra', e.g. string for making golden silk (can be null)
	 *  output: The resulting stack for this recipe
	 */
	public FluidStack fluid;
	public ItemStack catalyst;
	public ItemStack output;
	
	public RecipeFreezer(FluidStack fluid, ItemStack catalyst, ItemStack output) {
		this.fluid = fluid;
		this.catalyst = catalyst;
		this.output = output;
	}
}
