package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeIngotCasting {
	public FluidStack fluid;
	public ItemStack output;
	
	public RecipeIngotCasting(FluidStack fluid, ItemStack output) {
		this.fluid = fluid;
		this.output = output;
	}
}
