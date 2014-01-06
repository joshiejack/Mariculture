package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeFreezer {
	public FluidStack fluid;
	public ItemStack catalyst;
	public ItemStack output;
	
	public RecipeFreezer(FluidStack fluid, ItemStack catalyst, ItemStack output) {
		this.fluid = fluid;
		this.catalyst = catalyst;
		this.output = output;
	}
}
