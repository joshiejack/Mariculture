package mariculture.factory.blocks;

import mariculture.core.util.FluidDictionary;
import mariculture.factory.Factory;

public class TileTurbineWater extends TileTurbineBase {	
	@Override
	public int getRFCapacity() {
		return 2500;
	}

	@Override
	public int maxEnergyExtracted() {
		return 40;
	}

	@Override
	public int getEnergyGenerated() {
		return speed * 10;
	}

	@Override
	public boolean canUseFluid() {
		return tank.getFluid() != null && tank.getFluid().getFluid().getName().equals(FluidDictionary.hp_water);
	}

	@Override
	public boolean canUseRotor() {
		return inventory[rotor].itemID != Factory.turbineTitanium.itemID;
	}
}