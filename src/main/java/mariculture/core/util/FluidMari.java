package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.blocks.BlockPressurisedWater;
import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidMari extends Fluid {
    String name;
    private int bottle;

    public FluidMari(String name, int bottle) {
        super(name);
        this.name = name;
        this.bottle = bottle;
    }

    @Override
    public IIcon getStillIcon() {
        if (name.contains("dirt")) return Blocks.dirt.getIcon(0, 0);
        if (bottle < 0 && block != null && block instanceof BlockFluid) return ((BlockFluid)block).still;
        else if (bottle < 0) return BlockLiquid.getLiquidIcon("water_still");
        return BlockPressurisedWater.still != null? BlockPressurisedWater.still[bottle]: Blocks.water.getIcon(0, 0);
    }

    @Override
    public IIcon getFlowingIcon() {
        if (name.contains("dirt")) return Blocks.dirt.getIcon(0, 0);
        if (bottle < 0 && block != null && block instanceof BlockFluid) return ((BlockFluid)block).flowing;
        else if (bottle < 0) return BlockLiquid.getLiquidIcon("water_flow");
        return BlockPressurisedWater.flowing != null? BlockPressurisedWater.flowing[bottle]: Blocks.water.getIcon(0, 0);
    }
}
