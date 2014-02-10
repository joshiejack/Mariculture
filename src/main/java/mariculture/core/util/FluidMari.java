package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.blocks.BlockFluidMari;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidMari extends Fluid {
	private int bottle;
	
	public FluidMari(String name, int bottle) {
		super(name);
		this.bottle = bottle;
	}

	@Override
	public IIcon getStillIcon() {
		if(bottle < 0)
			return BlockFluid.getFluidIcon("water_still");
		return ((BlockFluidMari)Core.highPressureWaterBlock).still[bottle];
	}

	@Override
	public IIcon getFlowingIcon() {
		if(bottle < 0)
			return BlockFluid.getFluidIcon("water_flow");
		return ((BlockFluidMari)Core.highPressureWaterBlock).flowing[bottle];
	}
}
