package mariculture.factory.blocks;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.items.ItemRotor;

public class TileTurbineWater extends TileTurbineBase {	
	@Override
	public int getTankCapacity() {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 4) + (storage * FluidContainerRegistry.BUCKET_VOLUME * 2));
	}
	
	@Override
	public int getRFCapacity() {
		return 20000 + rf;
	}
	
	@Override
	public int getEnergyGenerated() {
		return 3 + (speed * 3);
	}

	@Override
	public int getEnergyTransferMax() {
		return 75;
	}
	
	@Override
	public boolean canOperate() {
		if(inventory[6] == null)
			return false;
		if(!mode.canWork(this, mode))
			return false;
		ItemStack rotor = inventory[6];
		if(rotor.getItem() instanceof ItemRotor) {
			return ((ItemRotor)inventory[6].getItem()).isTier(2);
		}
		
		return false;
	}

	@Override
	public void addPower() {
		FluidStack fluid = tank.getFluid();
		if(fluid != null && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
			String name = fluid.getFluid().getName();
			if(name.equals(FluidDictionary.hp_water)) {
				if(onTick(20)) {
					if(inventory[6].attemptDamageItem(1, Rand.rand)) {
						inventory[6] = null;
						return;
					}
				}
				
				isCreatingPower = true;
				if(Rand.rand.nextInt(((purity * 10) >= 1)? (purity * 10): 1) < 1)
					tank.drain(speed, true);
				energyStorage.modifyEnergyStored(getEnergyGenerated());
			} else {
				isCreatingPower = false;
			}
		} else {
			isCreatingPower = false;
		}
	}
}