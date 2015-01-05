package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeVat {
    /** The first input for a fluid can NOT be null, this first fluid must always exist **/
    public FluidStack inputFluid1;
    /** The second fluid in the recipe, can be null **/
    public FluidStack inputFluid2;
    /** The input item, can be null, itemstack or string **/
    public Object inputItem;
    /** Output Fluid: Either this or the item can be null but not both **/
    public FluidStack outputFluid;
    /** Output Item: Either this or the fluid can be null but not both **/
    public ItemStack outputItem;
    /** The total time this takes to process in seconds **/
    public int processTime;

    public RecipeVat(FluidStack fluid, int time) {
        inputFluid1 = fluid;
        processTime = time;
    }

    // Takes a fluid and makes an item
    public RecipeVat(FluidStack fluid, ItemStack output, int time) {
        this(fluid, time);
        outputItem = output;
    }

    // Takes a fluid and an item and makes an item
    public RecipeVat(Object input, FluidStack fluid, ItemStack output, int time) {
        this(fluid, time);
        inputItem = input;
        outputItem = output;
    }

    // Takes a fluid and an item and makes a fluid
    public RecipeVat(Object input, FluidStack fluid, FluidStack newFluid, int time) {
        this(fluid, time);
        inputItem = input;
        outputFluid = newFluid;
    }

    // Takes two Fluids and makes an item
    public RecipeVat(FluidStack fluid, FluidStack fluid2, ItemStack output, int time) {
        this(fluid, time);
        inputFluid2 = fluid2;
        outputItem = output;
    }

    // Takes two fluids and makes a fluid
    public RecipeVat(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, int time) {
        this(fluid, time);
        inputFluid2 = fluid2;
        outputFluid = newFluid;
    }

    // Takes two fluids and makes a fluid and an item
    public RecipeVat(FluidStack fluid, FluidStack fluid2, FluidStack newFluid, ItemStack output, int time) {
        this(fluid, time);
        inputFluid2 = fluid2;
        outputFluid = newFluid;
        outputItem = output;
    }

    // Takes two fluids and an item and makes a fluid
    public RecipeVat(Object input, FluidStack fluid, FluidStack fluid2, FluidStack newFluid, int time) {
        this(fluid, time);
        inputItem = input;
        inputFluid2 = fluid2;
        outputFluid = newFluid;
    }

    // Takes two fluids and an item and make an item
    public RecipeVat(Object input, FluidStack fluid, FluidStack fluid2, ItemStack output, int time) {
        this(fluid, time);
        inputItem = input;
        inputFluid2 = fluid2;
        outputItem = output;
    }

    // Takes two fluids and an item and makes a fluid and an item
    public RecipeVat(Object input, FluidStack fluid, FluidStack fluid2, FluidStack newFluid, ItemStack output, int time) {
        this(fluid, time);
        inputItem = input;
        inputFluid2 = fluid2;
        outputFluid = newFluid;
        outputItem = output;
    }

    public ItemStack getItem() {
        return inputItem instanceof ItemStack ? (ItemStack) inputItem : OreDictionary.getOres((String) inputItem).get(0);
    }
}
