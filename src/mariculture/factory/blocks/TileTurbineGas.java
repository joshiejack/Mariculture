package mariculture.factory.blocks;

import java.util.ArrayList;

import mariculture.api.core.IGasTurbine;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class TileTurbineGas extends TileTurbineBase implements IGasTurbine {
	public static ArrayList<String> fluids = new ArrayList<String>();
	
	@Override
	public void add(String str) {
		TileTurbineGas.fluids.add(str);
	}
	
	@Override
	public boolean canUseFluid() {
		if(tank.getFluid() == null)
			return false;
		return fluids.contains(tank.getFluid().getFluid().getName());
	}
	
	@Override
	public int getTankCapacity(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 10) + (count * FluidContainerRegistry.BUCKET_VOLUME * 5));
	}
	
	@Override
	public int getEnergyGenerated() {
		return speed * 160;
	}

	@Override
	public int getRFCapacity() {
		return 50000;
	}

	@Override
	public int maxEnergyExtracted() {
		return (int) (speed * 80);
	}

	public float getExternalAngle() {
		return (float) angle_external;
	}

	@Override
	public boolean canUseRotor() {
		return true;
	}
}