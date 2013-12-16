package mariculture.factory.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.util.FluidDictionary;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileTurbineGas extends TileTurbineBase {
	public float getModifier() {
		FluidStack fluid = tank.fluid;
		if(fluid != null) {
			String name = FluidRegistry.getFluidName(fluid);
			if(name != null) {
				if(name.equals("gasCraft_naturalGas")) {
					return 0.8F;
				} else if (name.equals("gasCraft_hydrogen")) {
					return 1.2F;
				}
				
				if(MaricultureHandlers.turbine.getModifier(fluid) > 0F) {
					return MaricultureHandlers.turbine.getModifier(fluid);
				}
			}
		}
		
		return (tank.getFluidID() == FluidRegistry.getFluidID(FluidDictionary.natural_gas))? 1F: 0;
	}
	
	@Override
	public boolean canUseLiquid() {
		return getModifier() > 0;
	}
	
	@Override
	public int getTankCapacity(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 10) + (count * FluidContainerRegistry.BUCKET_VOLUME * 5));
	}
	
	@Override
	public void updateUpgrades() {
		super.updateUpgrades();
		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatCount = MaricultureHandlers.upgrades.getData("temp", this);
		this.purity = purityCount + 1;
		this.heat = (heatCount >= 0)? (heatCount + 1) * 2: 2;
	}

	@Override
	public int maxEnergyStored() {
		return 50000;
	}

	@Override
	public int maxEnergyExtracted() {
		return (int) (getModifier() * 350);
	}
}