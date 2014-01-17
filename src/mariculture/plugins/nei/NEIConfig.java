package mariculture.plugins.nei;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	public static final HashMap<String, ArrayList<ItemStack>> containers = new HashMap();
	
	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new NEILiquifierRecipeHandler());
		API.registerUsageHandler(new NEILiquifierRecipeHandler());
		API.registerRecipeHandler(new NEIVatRecipeHandler());
		API.registerUsageHandler(new NEIVatRecipeHandler());
		API.registerRecipeHandler(new NEIIngotCasterRecipeHandler());
		API.registerUsageHandler(new NEIIngotCasterRecipeHandler());
		
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData().clone();
		for(FluidContainerData container: data) {
			String fluid = container.fluid.getFluid().getName();
			if(containers.containsKey("fluid")) {
				ArrayList<ItemStack> list = containers.get(fluid);
				list.add(container.filledContainer);
			} else {
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				list.add(container.filledContainer);
				containers.put(fluid, list);
			}
		}
	}

	@Override
	public String getName() {
		return "Mariculture NEI";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
