package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class RecipeVat {
	public int cookTime;
	
	/** Never add this type of recipe, it would be redundant
	 *  CookTime is in 'seconds' in the large vat, and in '1 and a half seconds' for the small vat**/
	public RecipeVat(int cookTime) {
		this.cookTime = cookTime;
	}
	
	/** More Advanced Alloying **/
	public static class RecipeVatAlloy extends RecipeVat {
		public FluidStack fluid;
		public FluidStack fluid2;
		public FluidStack newFluid;
		public ItemStack output;
		public int cookTime;
		//This method, takes two liquids, and produces one
		public RecipeVatAlloy(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, int cookTime) {
			super(cookTime);
			this.fluid = fluid;
			this.fluid2 = fluid2;
			this.newFluid = newFluid;
			this.output = null;
		}
		
		//This takes two liquids, produces one and produces an item
		public RecipeVatAlloy(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, ItemStack output, int cookTime) {
			this(fluid2, fluid, newFluid, cookTime);
			this.output = output;
		}
		
		//This takes two liquids and produces an item
		public RecipeVatAlloy(FluidStack fluid, FluidStack fluid2, ItemStack output, int cookTime) {
			this(fluid, fluid2, null, output, cookTime);
		}
	}
 	
	/** Your basic Vat Recipe, takes a liquid, and an item, and outputs an item **/
	public static class RecipeVatItem extends RecipeVat {
		public ItemStack input;
		public FluidStack fluid;
		public ItemStack output;
		
		public RecipeVatItem(ItemStack input, FluidStack fluid, ItemStack output, int cookTime) {
			super(cookTime);
			this.input = input;
			this.fluid = fluid;
			this.output = output;
		}
	}
}
