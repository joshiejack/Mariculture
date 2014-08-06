package mariculture.core.util;

import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public class XPRegistry {
    private static final HashMap<String, Float> map = new HashMap();

    public static void register(String fluid, float f) {
        map.put(fluid, f);
    }

    public static boolean isXP(FluidStack fluid) {
        return fluid != null && fluid.getFluid() != null && map.containsKey(fluid.getFluid().getName());
    }

    public static int getXPValueOfFluid(FluidStack fluid) {
        return (int) Math.floor(fluid.amount / map.get(fluid.getFluid().getName()));
    }
}
