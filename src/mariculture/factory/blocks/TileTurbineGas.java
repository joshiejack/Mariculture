package mariculture.factory.blocks;

import java.util.ArrayList;

import mariculture.api.core.IGasTurbine;
import mariculture.core.util.Rand;
import mariculture.factory.items.ItemRotor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileTurbineGas extends TileTurbineBase implements IGasTurbine {
	public static ArrayList<String> fluids = new ArrayList<String>();
	
	@Override
	public void add(String str) {
		TileTurbineGas.fluids.add(str);
	}
	
	@Override
	public int getTankCapacity() {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 10) + (storage * FluidContainerRegistry.BUCKET_VOLUME * 5));
	}
	
	@Override
	public int getRFCapacity() {
		return 50000 + rf;
	}
	
	@Override
	public int getEnergyGenerated() {
		return 60 + (speed * 10);
	}

	@Override
	public int getEnergyTransferMax() {
		return 1000;
	}
	
	@Override
	public boolean canOperate() {
		if(inventory[6] == null)
			return false;
		if(!mode.canWork(this, mode))
			return false;
		ItemStack rotor = inventory[6];
		if(rotor.getItem() instanceof ItemRotor) {
			return ((ItemRotor)inventory[6].getItem()).isTier(3);
		}

		return false;
	}

	@Override
	public void addPower() {
		FluidStack fluid = tank.getFluid();
		if(fluid != null && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
			String name = fluid.getFluid().getName();
			if(fluids.contains(name)) {
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