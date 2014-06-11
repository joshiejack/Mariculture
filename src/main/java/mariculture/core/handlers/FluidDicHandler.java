package mariculture.core.handlers;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidDicHandler {
    private static final HashMap<String, FluidDicEntry> entries = new HashMap();

    public static void register(String dictionaryName, String fluid, int ratio) {
        entries.put(fluid, new FluidDicEntry(dictionaryName, ratio));
    }

    public static boolean areSameDicNames(FluidStack stack, FluidStack fluid) {
        if (stack != null && stack.getFluid() != null && fluid != null && fluid.getFluid() != null) {
            String stackName = entries.get(stack.getFluid().getName()) != null ? entries.get(stack.getFluid().getName()).name : null;
            String fluidName = entries.get(fluid.getFluid().getName()) != null ? entries.get(fluid.getFluid().getName()).name : "";
            return stackName != null ? stackName.equals(fluidName) : false;
        } else return false;
    }

    public static Integer getValue(String fluid) {
        return entries.get(fluid) != null ? entries.get(fluid).ratio : 0;
    }

    private static class FluidDicEntry {
        private String name;
        private int ratio;

        private FluidDicEntry(String name, int ratio) {
            this.name = name;
            this.ratio = ratio;
        }
    }

    public static int getPosition(FluidStack fluid) {
        if (fluid != null && fluid.getFluid() != null) {
            int i = 0;
            for (Entry<String, FluidDicEntry> entr : FluidDicHandler.entries.entrySet()) {
                if (entr.getKey().equals(fluid.getFluid().getName())) return i;

                i++;
            }
        }

        return 0;
    }

    public static String getNext(FluidStack fluid) {
        if (fluid != null && fluid.getFluid() != null) {
            boolean found = false;
            for (Entry<String, FluidDicEntry> entr : FluidDicHandler.entries.entrySet()) {
                if (found && FluidRegistry.getFluid(entr.getKey()) != null) return entr.getKey();
                if (entr.getKey().equals(fluid.getFluid().getName())) {
                    found = true;
                }
            }
        }

        return "water";
    }
}
