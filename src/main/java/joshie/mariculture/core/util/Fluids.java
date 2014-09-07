package joshie.mariculture.core.util;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class Fluids {
    private static HashMap<String, BalancedFluid> fluids = new HashMap();

    private static class BalancedFluid {
        Fluid fluid;
        int volume;

        public BalancedFluid(Fluid fluid, int volume) {
            this.fluid = fluid;
            this.volume = volume;
        }
    }

    public static boolean add(String name, Fluid fluid, int volume) {
        return add(name, fluid, volume, false);
    }

    public static boolean add(String name, Fluid fluid, int volume, boolean override) {
        if ((!fluids.containsKey(name) && fluid != null) || override) {
            fluids.put(name, new BalancedFluid(fluid, volume));
            return true;
        }

        return false;
    }

    public static boolean setBlock(String fluid, Block block) {
        if (!fluids.containsKey(fluid)) {
            return false;
        } else {
            Fluid f = getFluid(fluid);
            f.setBlock(block);
            fluids.put(fluid, new BalancedFluid(f, getBalancedVolume(fluid)));
            return true;
        }
    }

    public static FluidStack getFluidStack(String fluid, int volume) {
        return new FluidStack(getFluid(fluid), volume);
    }

    public static Fluid getFluid(String fluid) {
        return fluids.get(fluid) != null ? fluids.get(fluid).fluid : null;
    }

    public static int getFluidID(String fluid) {
        return getFluid(fluid).getID();
    }

    public static String getFluidName(String fluid) {
        return getFluid(fluid).getName();
    }

    public static boolean isRegistered(String fluid) {
        return getFluid(fluid) != null;
    }

    public static Block getFluidBlock(String fluid) {
        return getFluid(fluid).getBlock();
    }

    public static int getBalancedVolume(String fluid) {
        return fluids.get(fluid).volume;
    }

    public static FluidStack getBalancedStack(String fluid) {
        return new FluidStack(getFluid(fluid), getBalancedVolume(fluid));
    }
    
    public static boolean isBlood(String blood) {        
        return blood.equals("life essence") || blood.equals("blood");
    }
}
