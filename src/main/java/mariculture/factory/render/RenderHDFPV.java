package mariculture.factory.render;

import mariculture.core.Core;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.render.RenderBase;
import mariculture.factory.blocks.TileHDFPV;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.fluids.FluidStack;

public class RenderHDFPV extends RenderBase {
	public RenderHDFPV(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileHDFPV tank = (TileHDFPV) world.getBlockTileEntity(x, y, z);
			if(tank != null && tank.fluid != null) {
				if(tank.fluid.amount > 0) {
					FluidStack fluid = tank.fluid;
					if(fluid != null) {
						double height = (double)fluid.amount * 0.8000000D / Integer.MAX_VALUE;
						setTexture(fluid.getFluid().getIcon());
						renderFluidBlock(0.250, 0.100, 0.250, 0.750, 0.100 + height + 0.001, 0.750);
					}
				}
			}
		} 
		
		if(!isItem()) render.clearOverrideBlockTexture();
		else setTexture(block, TankMeta.HDFPV);
		renderBlock(-0.0001D, -0.0001D, -0.0001D, 1.0001D, 1.0001D, 1.0001D);
		setTexture(Core.ores, OresMeta.BASE_IRON);
		renderBlock(0, 0.100, 0, 0.250, 0.900, 0.250);
		renderBlock(0, 0.100, 0.750, 0.250, 0.900, 1.0);
		renderBlock(0.750, 0.100, 0, 1, 0.900, 0.250);
		renderBlock(0.750, 0.100, 0.750, 1, 0.900, 1.0);
		renderBlock(0, 0, 0, 1, 0.100, 1);
		renderBlock(0, 0.900, 0, 1, 1, 1);
	}
}
