package mariculture.factory.render;

import mariculture.core.Core;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.render.RenderBase;
import mariculture.core.util.Tank;
import mariculture.factory.tile.TileDictionaryFluid;
import net.minecraftforge.fluids.FluidStack;

public class RenderFluidDictionary extends RenderBase {
	public RenderFluidDictionary() {}
	
	private void renderFluid(Tank tank, double i1, double k1, double i2, double k2) {
		if(tank.getFluidAmount() > 0) {
			FluidStack fluid = tank.getFluid();
			if(fluid != null) {
				double height = (double)fluid.amount * 1D / tank.getCapacity();
				setTexture(fluid.getFluid().getIcon());
				renderFluidBlock(i1, 0, k1, i2, 1D, k2);
			}
		}
	}

	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileDictionaryFluid tank = (TileDictionaryFluid) world.getTileEntity(x, y, z);
			if(tank != null) {
				if(tank.tank.getFluidAmount() > 0) {
					FluidStack fluid = tank.tank.getFluid();
					if(fluid != null) {
						setTexture(fluid.getFluid().getIcon());
						renderFluidBlock(0.001, 0.001, 0.001, 0.999, 0.998, 0.999);
					}
				}
			}
		} 
		
		setTexture(block, TankMeta.DIC);
		renderBlock(-0.0D, -0.0D, -0.0D, 1.0D, 0.999D, 1.0D);
		
		setTexture(Core.woods, WoodMeta.BASE_WOOD);
		renderBlock(0.0D, 0.0D, 0.0D, 1.0D, 0.01D, 1.0D);
		renderBlock(0.0D, 0.99D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
}
