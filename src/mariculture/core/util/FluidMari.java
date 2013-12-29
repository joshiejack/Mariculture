package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.blocks.BlockPressuredWater;
import net.minecraft.block.BlockFluid;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidMari extends Fluid {
	private int bottle;
	
	public FluidMari(String name, int bottle) {
		super(name);
		this.bottle = bottle;
	}

	public Icon getStillIcon() {
		if(bottle < 0)
			return BlockFluid.getFluidIcon("water_still");
		return ((BlockPressuredWater)Core.highPressureWaterBlock).still[bottle];
	}

	public Icon getFlowingIcon() {
		if(bottle < 0)
			return BlockFluid.getFluidIcon("water_flow");
		return ((BlockPressuredWater)Core.highPressureWaterBlock).flowing[bottle];
	}
}
