package mariculture.core.render;

import mariculture.core.tile.TileTankBlock;
import net.minecraftforge.fluids.FluidStack;

public class RenderCopperTank extends RenderBase {
    public RenderCopperTank() {}

    @Override
    public void renderBlock() {
        if (!isItem()) {
            TileTankBlock tank = (TileTankBlock) world.getTileEntity(x, y, z);
            if (tank != null) if (tank.tank.getFluidAmount() > 0) {
                FluidStack fluid = tank.tank.getFluid();
                if (fluid != null) {
                    double height = fluid.amount * 1D / tank.getCapacity();
                    setTexture(fluid.getFluid().getIcon());
                    if(fluid.getFluid().isGaseous()) {
                        renderFluidBlock(0, 1 - height, 0, 1, 1, 1);
                    } else {
                        renderFluidBlock(0, 0, 0, 1, height, 1);
                    }
                }
            }
        }

        setTexture(block);
        renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
    }
}
