package mariculture.core.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidDictionary {
	public static FluidDictionary instance = new FluidDictionary();

	static HashMap<String, Fluid> metals = new HashMap();

	public void addFluid(String name, Fluid fluid) {
		metals.put(name, fluid);
	}
	
	public static Fluid getFluid(String name) {
		for (Map.Entry<String, Fluid> entry : metals.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(name)) {
				return entry.getValue();
			}
		}

		return FluidRegistry.WATER;
	}

	public boolean metalExists(String name) {
		for (Map.Entry<String, Fluid> entry : metals.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(name)) {
				return true;
			}
		}

		if (name.equals("aluminum")) {
			if (checkInFluidRegistry("aluminum")) {
				return true;
			}
		}

		return checkInFluidRegistry(name);
	}

	public boolean checkInFluidRegistry(String name) {
		if (FluidRegistry.isFluidRegistered("molten" + name)) {
			addFluid(name, FluidRegistry.getFluid("molten" + name));
			return true;
		} else if (FluidRegistry.isFluidRegistered("molten " + name)) {
			addFluid(name, FluidRegistry.getFluid("molten " + name));
			return true;
		}

		return false;
	}

	//Metals and Glass
	public static String aluminum;
	public static String titanium;
	public static String iron;
	public static String gold;
	public static String copper;
	public static String tin;
	public static String magnesium;
	public static String bronze;
	public static String lead;
	public static String silver;
	public static String steel;
	public static String nickel;
	public static String glass;
	public static String rutile;
	//Other
	public static String fish_oil = "fishoil";// = "fishoil";
	public static String fish_food = "fishfood";// = "fishfood";
	public static String hp_water = "fastwater";// = "mariculture_fastwater";
	public static String natural_gas = "naturalgas";// = "naturalgas";
}
