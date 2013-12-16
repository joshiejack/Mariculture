package mariculture.core.util;

import net.minecraftforge.fluids.FluidStack;

public interface ITank {
	public FluidStack getFluid(int transfer);
}
