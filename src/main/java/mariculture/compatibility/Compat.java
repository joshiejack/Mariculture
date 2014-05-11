package mariculture.compatibility;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.util.FluidDictionary;
import net.minecraftforge.fluids.FluidRegistry;

public class Compat extends Module {
	@Override
	public void preInit() {
		if(FluidRegistry.getFluid("milk") != null) {
			FluidDictionary.instance.addFluid("milk", FluidRegistry.getFluid("milk"));
		}
	}
	
	@Override
	public void init() {
		if(Modules.isActive(Modules.fishery)) {
			try {
				CompatBait.init();
			} catch (Exception e) {
				LogHandler.log(Level.INFO, "Mariculture - Something went wrong when loading the Bait Compatibility Config");
			}
		}
		
		CompatFluids.init();
	}
	
	@Override
	public void postInit() {
		setLoaded(null);
	}
}
