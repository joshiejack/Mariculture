package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeSmelter {
	public ItemStack input;
	public SmelterOutput output;
	public int temp;
	
	public RecipeSmelter(ItemStack input, int temp, SmelterOutput output) {
		this.input = input;
		this.temp = temp;
		this.output = output;
	}
	
	public static class SmelterOutput {
		public FluidStack fluid;
		public ItemStack output;
		public int chance;
		
		public SmelterOutput(FluidStack fluid, ItemStack output, int chance) {
			this.fluid = fluid;
			this.output = output;
			this.chance = chance;
		}
		
		public SmelterOutput(FluidStack fluid) {
			this.fluid = fluid;
			this.output = null;
			this.chance = 0;
		}
	}
}
