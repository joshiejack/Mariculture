package mariculture.plugins.compatibility;

import java.util.logging.Level;

import tconstruct.library.crafting.FluidType;
import net.minecraftforge.fluids.FluidRegistry;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidDictionary;

public class Compat {
	public static void preInit() {
		CompatBooks.preInit();
		
		if(FluidRegistry.getFluid("milk") != null) {
			FluidDictionary.instance.addFluid("milk", FluidRegistry.getFluid("milk"));
		}
	}
	
	public static void init() {
		if(Modules.fishery.isActive()) {
			try {
				CompatBait.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong when loading the Bait Compatibility Config");
			}
		}
		
		CompatFluids.init();
		CompatBooks.init();
	}
}
