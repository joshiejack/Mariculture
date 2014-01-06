package mariculture.core.util;

import java.util.List;

import net.minecraftforge.fluids.FluidStack;

public interface ITank {
	public FluidStack getFluid(int transfer);

	public int getTankScaled(int i);
	public FluidStack getFluid();
	public String getFluidName();
	public List getFluidQty(List tooltip);
	public void setFluid(FluidStack fluid);
}
