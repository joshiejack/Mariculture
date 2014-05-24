package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.blocks.BlockPressurisedWater;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidMari extends Fluid {
	String name;
	private int bottle;
	
	public FluidMari(String name, int bottle) {
		super(name);
		this.name = name;
		this.bottle = bottle;
	}

	@Override
	public IIcon getStillIcon() {
		if(name.contains("dirt")) return Blocks.dirt.getIcon(0, 0);
		if(bottle < 0) return BlockLiquid.getLiquidIcon("water_still");
		return ((BlockPressurisedWater)Core.hpWaterBlock).still[bottle];
	}

	@Override
	public IIcon getFlowingIcon() {
		if(name.contains("dirt")) return Blocks.dirt.getIcon(0, 0);
		if(bottle < 0) return BlockLiquid.getLiquidIcon("water_flow");
		return ((BlockPressurisedWater)Core.hpWaterBlock).flowing[bottle];
	}
}
