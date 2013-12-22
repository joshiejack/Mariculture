package mariculture.core.util;

import net.minecraftforge.fluids.FluidStack;

public interface ITank {
	public FluidStack getFluid(int transfer);

	public int getTankScaled(int i);
	public FluidStack getFluid();
	public String getLiquidName();
	public String getLiquidQty();
}
