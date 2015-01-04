package mariculture.core.util;

import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public class XPRegistry {
    private static final HashMap<String, Float> map = new HashMap();

    public static void register(String fluid, float f) {
        map.put(fluid, f);
    }

    public static boolean isXP(FluidStack fluid) {
        return fluid != null && fluid.getFluid() != null && fluid.amount > 0 && map.containsKey(fluid.getFluid().getName());
    }

    public static int getXPValueOfFluid(FluidStack fluid) {
        float value = map.get(fluid.getFluid().getName());
        float ret = (float) fluid.amount / value;
        return (int) ret;
    }

    public static int getFluidValueOfXP(FluidStack fluid, int xp) {
        float value = map.get(fluid.getFluid().getName());
        return (int) (value * xp);
    }
}
