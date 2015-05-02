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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fluid == null) ? 0 : fluid.hashCode());
        result = prime * result + ((output == null) ? 0 : output.getItem().hashCode());
        result = prime * result + ((output == null) ? 0 : output.getItemDamage());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RecipeCasting other = (RecipeCasting) obj;
        if (fluid == null) {
            if (other.fluid != null) return false;
        } else if (!fluid.equals(other.fluid)) return false;
        if (output == null) {
            if (other.output != null) return false;
        } else if (!output.getItem().equals(other.output.getItem()) || output.getItemDamage() != other.output.getItemDamage()) return false;
        return true;
    }
}
