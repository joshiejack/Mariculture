package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class RecipeCasting {
    public FluidStack fluid;
    public ItemStack output;

    public RecipeCasting(FluidStack fluid, ItemStack output) {
        this.fluid = fluid;
        this.output = output;
    }

    public static class RecipeNuggetCasting extends RecipeCasting {
        public RecipeNuggetCasting(FluidStack fluid, ItemStack output) {
            super(fluid, output);
        }
    }

    public static class RecipeIngotCasting extends RecipeCasting {
        public RecipeIngotCasting(FluidStack fluid, ItemStack output) {
            super(fluid, output);
        }
    }

    public static class RecipeBlockCasting extends RecipeCasting {
        public RecipeBlockCasting(FluidStack fluid, ItemStack output) {
            super(fluid, output);
        }
    }
}
