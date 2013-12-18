package mariculture.factory.blocks;

import mariculture.core.Core;

public class TileTurbineWater extends TileTurbineBase {	
	@Override
	public boolean canUseLiquid() {
		return tank.getFluidID() == Core.highPressureWater.getID();
	}

	@Override
	public int maxEnergyStored() {
		return 2500;
	}

	@Override
	public int maxEnergyExtracted() {
		return 20;
	}
}