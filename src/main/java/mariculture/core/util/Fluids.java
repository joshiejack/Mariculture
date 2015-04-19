package mariculture.core.util;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
        Fluid fluid2 = FluidRegistry.getFluid(fluid.getName()); //If there is already a fluid registered
        if (fluid2 != null) {
            fluid = fluid2; //Use the registered fluid no matter what
        }
        
        if ((!fluids.containsKey(name) && fluid != null) || override) {
            fluids.put(name, new BalancedFluid(fluid, volume));
            return fluid != fluid2; //Return true if we should register our own fluid
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

    public static boolean isEnder(Block block) {
        return block == getFluidBlock("ender");
    }

    public static boolean isHalfway(Block block) {
        return block == getFluidBlock("custard");
    }
}
