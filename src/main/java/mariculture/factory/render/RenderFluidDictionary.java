package mariculture.factory.render;

import mariculture.core.lib.TankMeta;
import mariculture.core.render.RenderBase;
import mariculture.core.util.Tank;
import mariculture.factory.blocks.TileDictionaryFluid;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.fluids.FluidStack;

public class RenderFluidDictionary extends RenderBase {
	public RenderFluidDictionary(RenderBlocks render) {
		super(render);
	}
	
	private void renderFluid(Tank tank, double i1, double k1, double i2, double k2) {
		if(tank.getFluidAmount() >0) {
			FluidStack fluid = tank.getFluid();
			if(fluid != null) {
				double height = (double)fluid.amount * 1D / tank.getCapacity();
				setTexture(fluid.getFluid().getIcon());
				renderFluidBlock(i1, 0, k1, i2, height, k2);
			}
		}
	}

	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileDictionaryFluid tank = (TileDictionaryFluid) world.getTileEntity(x, y, z);
			if(tank != null) {
				renderFluid(tank.tank, 0, 0, 1, 1);
			}
		} 
		
		setTexture(block, TankMeta.DIC);
		renderBlock(0D, 0D, 0D, 1D, 1D, 1D);
	}
}
