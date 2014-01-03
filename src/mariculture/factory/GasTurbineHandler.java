package mariculture.factory;

import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.IGasTurbine;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GasTurbineHandler implements IGasTurbine {
	public static Map gas = new HashMap();
	
	public void add(String str, float modifier) {
		gas.put(str, modifier);
	}

	@Override
	public float getModifier(FluidStack fluid) {
		if(fluid != null) {
			if(fluid.amount > 0) {
				String name = FluidRegistry.getFluidName(fluid);
				if(name != null) {
					if(gas.get(name) != null) {
						return (Float) gas.get(name);
					}
				}
			}
		}
		
		return 0F;
	}
}
