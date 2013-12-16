package mariculture.api.core;

import net.minecraftforge.fluids.FluidStack;

public interface IGasTurbine {
	/** Add a liquid to be accepted by the gas turbine
	 * 
	 * @param Fluid Name e.g. water
	 * @param Power Modifier, 1F is default, use lower to have the turbine produce less or higher for more power
	 */
	public void add(String name, float modifier);
	
	/** returns the modifier for this fluid, will return 0F if not valid **/
	public float getModifier(FluidStack fluid);
}
