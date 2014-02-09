package mariculture.core.render;

import mariculture.core.blocks.TileTankBlock;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.fluids.FluidStack;

public class RenderCopperTank extends RenderBase {
	public RenderCopperTank(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileTankBlock tank = (TileTankBlock) world.getBlockTileEntity(x, y, z);
			if(tank != null) {
				if(tank.tank.getFluidAmount() > 0) {
					FluidStack fluid = tank.tank.getFluid();
					if(fluid != null) {
						double height = (double)fluid.amount * 1D / tank.getCapacity();
						setTexture(fluid.getFluid().getIcon());
						renderFluidBlock(0, 0, 0, 1, height, 1);
					}
				}
			}
		} 
		
		setTexture(block);
		renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
	}
}
