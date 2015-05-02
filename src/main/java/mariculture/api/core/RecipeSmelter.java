package mariculture.api.core;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeSmelter {
    public int temp;
    public ItemStack input;
    public FluidStack fluid;
    public ItemStack output;
    public int chance;
    public FluidStack[] random;
    public Integer[] rands;

    public RecipeSmelter(ItemStack input, ItemStack input2, int temp, FluidStack fluid, ItemStack output, int chance) {
        this(input, temp, fluid, output, chance);
    }

    public RecipeSmelter(ItemStack input, int temp, FluidStack fluid, ItemStack output, int chance) {
        this.input = input;
        this.temp = temp;
        this.fluid = fluid;
        this.output = output;
        this.chance = chance;
    }

    public RecipeSmelter(ItemStack input, int temp, FluidStack[] fluids, Integer rands[], ItemStack output, int chance) {
        fluid = fluids[0];
        this.input = input;
        this.temp = temp;
        random = fluids;
        this.rands = rands;
        this.output = output;
        this.chance = chance;
    }

    public RecipeSmelter(ItemStack input, ItemStack input2, int temp, FluidStack fluid, ItemStack output, int chance, Integer[] rands) {
        this(input, temp, fluid, output, chance);
        this.rands = rands;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fluid == null) ? 0 : fluid.hashCode());
        result = prime * result + ((input == null) ? 0 : input.getItem().hashCode());
        result = prime * result + ((input == null) ? 0 : input.getItemDamage());
        result = prime * result + ((output == null) ? 0 : output.getItem().hashCode());
        result = prime * result + ((output == null) ? 0 : output.getItemDamage());
        result = prime * result + Arrays.hashCode(random);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RecipeSmelter other = (RecipeSmelter) obj;
        if (fluid == null) {
            if (other.fluid != null) return false;
        } else if (!fluid.equals(other.fluid)) return false;
        if (input == null) {
            if (other.input != null) return false;
        } else if (!input.getItem().equals(other.input.getItem()) || input.getItemDamage() != other.input.getItemDamage()) return false;
        if (output == null) {
            if (other.output != null) return false;
        } else if (!output.getItem().equals(other.output.getItem()) || output.getItemDamage() != other.output.getItemDamage()) return false;
        if (!Arrays.equals(random, other.random)) return false;
        return true;
    }
}
