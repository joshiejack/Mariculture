package mariculture.core.render;

import org.lwjgl.opengl.GL11;

import mariculture.core.tile.TileTankBlock;
import net.minecraftforge.fluids.FluidStack;

public class RenderTank extends RenderBase {
    public RenderTank() {}

    @Override
    public void renderBlock() {
        if (!isItem()) {
            TileTankBlock tank = (TileTankBlock) world.getTileEntity(x, y, z);
            if (tank != null) {
                if (tank.tank.getFluidAmount() > 0) {
                    FluidStack fluid = tank.tank.getFluid();
                    if (fluid != null) {
                        double height = fluid.amount * 1D / tank.getCapacity();
                        setTexture(fluid.getFluid().getStillIcon());
                        if (fluid.getFluid().getBlock() != null) {
                            setTexture(fluid.getFluid().getBlock().getIcon(world, x, y, z, 0));
                        }

                        if (fluid.getFluid().isGaseous()) {
                            renderFluidBlock(0, 1 - height, 0, 1, 1, 1);
                        } else {
                            renderFluidBlock(0, 0, 0, 1, height, 1);
                        }
                    }
                }
            }
        }

        setTexture(block, meta);
        renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
        render.clearOverrideBlockTexture();
    }
}
