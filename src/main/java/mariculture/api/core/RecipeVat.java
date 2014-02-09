package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeVat {
	/** The first input for a fluid can NOT be null, this first fluid must always exist **/
	public FluidStack inputFluid1;
	/** The second fluid in the recipe, can be null **/
	public FluidStack inputFluid2;
	/** The input item, can be null **/
	public ItemStack inputItem;
	/** Output Fluid: Either this or the item can be null but not both **/
	public FluidStack outputFluid;
	/** Output Item: Either this or the fluid can be null but not both **/
	public ItemStack outputItem;
	/** The total time this takes to process in seconds **/
	public int processTime;

	public RecipeVat(FluidStack fluid, int time) {
		this.inputFluid1 = fluid;
		this.processTime = time;
	}

	// Takes a fluid and makes an item
	public RecipeVat(FluidStack fluid, ItemStack output, int time) {
		this(fluid, time);
		this.outputItem = output;
	}

	// Takes a fluid and an item and makes an item
	public RecipeVat(ItemStack input, FluidStack fluid, ItemStack output, int time) {
		this(fluid, time);
		this.inputItem = input;
		this.outputItem = output;
	}

	// Takes a fluid and an item and makes a fluid
	public RecipeVat(ItemStack input, FluidStack fluid, FluidStack newFluid, int time) {
		this(fluid, time);
		this.inputItem = input;
		this.outputFluid = newFluid;
	}

	// Takes two Fluids and makes an item
	public RecipeVat(FluidStack fluid, FluidStack fluid2, ItemStack output, int time) {
		this(fluid, time);
		this.inputFluid2 = fluid2;
		this.outputItem = output;
	}

	// Takes two fluids and makes a fluid
	public RecipeVat(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, int time) {
		this(fluid, time);
		this.inputFluid2 = fluid2;
		this.outputFluid = newFluid;
	}

	// Takes two fluids and makes a fluid and an item
	public RecipeVat(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, ItemStack output, int time) {
		this(fluid, time);
		this.inputFluid2 = fluid2;
		this.outputFluid = newFluid;
		this.outputItem = output;
	}

	// Takes two fluids and an item and makes a fluid
	public RecipeVat(ItemStack input, FluidStack fluid, FluidStack fluid2, FluidStack newFluid, int time) {
		this(fluid, time);
		this.inputItem = input;
		this.inputFluid2 = fluid2;
		this.outputFluid = newFluid;
	}

	// Takes two fluids and an item and make an item
	public RecipeVat(ItemStack input, FluidStack fluid, FluidStack fluid2, ItemStack output, int time) {
		this(fluid, time);
		this.inputItem = input;
		this.inputFluid2 = fluid2;
		this.outputItem = output;
	}

	// Takes two fluids and an item and makes a fluid and an item
	public RecipeVat(ItemStack input, FluidStack fluid, FluidStack fluid2, FluidStack newFluid, ItemStack output, int time) {
		this(fluid, time);
		this.inputItem = input;
		this.inputFluid2 = fluid2;
		this.outputFluid = newFluid;
		this.outputItem = output;
	}
}
