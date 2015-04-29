package mariculture.magic;

import mariculture.core.items.ItemFluidStorage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemInfiwater extends ItemFluidStorage {
    public ItemInfiwater() {
        super(1000);
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        return new FluidStack(FluidRegistry.WATER, 1000);
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        return getFluid(container);
    }
}
