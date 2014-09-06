package joshie.mariculture.plugins.nei;

import java.util.ArrayList;
import java.util.HashMap;

import joshie.mariculture.core.config.GeneralStuff;
import joshie.mariculture.core.lib.Modules;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

    public static final HashMap<String, ArrayList<ItemStack>> containers = new HashMap();

    @Override
    public void loadConfig() {
        NEICleanup.cleanup();
        if (Modules.isActive(Modules.fishery)) {
            API.registerRecipeHandler(new NEIFishProductHandler());
            API.registerUsageHandler(new NEIFishProductHandler());
            API.registerRecipeHandler(new NEISifterRecipeHandler());
            API.registerUsageHandler(new NEISifterRecipeHandler());
            API.registerRecipeHandler(new NEIFishBreedingMutationHandler());
            API.registerUsageHandler(new NEIFishBreedingMutationHandler());
        }

        API.registerRecipeHandler(new NEICrucibleRecipeHandler());
        API.registerUsageHandler(new NEICrucibleRecipeHandler());
        API.registerRecipeHandler(new NEIVatRecipeHandler());
        API.registerUsageHandler(new NEIVatRecipeHandler());
        API.registerRecipeHandler(new NEIAnvilRecipeHandler());
        API.registerUsageHandler(new NEIAnvilRecipeHandler());
        if (GeneralStuff.SHOW_CASTER_RECIPES) {
            API.registerRecipeHandler(new NEIIngotCasterRecipeHandler());
            API.registerUsageHandler(new NEIIngotCasterRecipeHandler());
            API.registerRecipeHandler(new NEIBlockCasterRecipeHandler());
            API.registerUsageHandler(new NEIBlockCasterRecipeHandler());
            API.registerRecipeHandler(new NEINuggetCasterRecipeHandler());
            API.registerUsageHandler(new NEINuggetCasterRecipeHandler());
        }

        FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData().clone();
        for (FluidContainerData container : data) {
            String fluid = container.fluid.getFluid().getName();
            if (containers.containsKey("fluid")) {
                ArrayList<ItemStack> list = containers.get(fluid);
                list.add(container.filledContainer);
            } else {
                ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                list.add(container.filledContainer);
                containers.put(fluid, list);
            }
        }

        if (Modules.isActive(Modules.magic)) {
            API.registerRecipeHandler(new NEIJewelryShapedHandler());
            API.registerUsageHandler(new NEIJewelryShapedHandler());
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
