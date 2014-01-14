package mariculture.core.render;

import mariculture.core.blocks.TileTankBlock;
import mariculture.core.helpers.RenderHelper;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.fishery.TileFishTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTanks implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int meta, int id, RenderBlocks render) {
		if (id == RenderIds.BLOCK_TANKS) {
			RenderHelper.renderInvBlock(render, block, meta);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int id, RenderBlocks render) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == TankMeta.BOTTLE) {
			new RenderVoidBottle(render).setCoords(world, x, y, z).render();
			return true;
		} else if(world.getBlockTileEntity(x, y, z) instanceof TileTankBlock) {
			//Tank
			TileTankBlock tank = (TileTankBlock) world.getBlockTileEntity(x, y, z);
			if(tank != null) {
				if(tank.tank.getFluidAmount() > 0) {
					FluidStack fluidStack = tank.tank.getFluid();
					Fluid fluid = fluidStack.getFluid();
					RenderHelper.renderFluid(world, x, y, z, tank.getFluidAmountScaled(), fluid.getStillIcon(), fluid.getFlowingIcon(), render);
				}
			}

			// Block
			render.setRenderBounds(0, 0, 0, 1, 1, 1);
			render.renderStandardBlock(block, x, y, z);
		} else if(meta == TankMeta.FISH) {
			TileFishTank tank = (TileFishTank) world.getBlockTileEntity(x, y, z);
			if(tank != null) {
				if(tank.tank.getFluidAmount() > 0) {
					FluidStack fluidStack = tank.tank.getFluid();
					Fluid fluid = fluidStack.getFluid();
					RenderHelper.renderFluid(world, x, y, z, tank.getFluidAmountScaled(), fluid.getStillIcon(), fluid.getFlowingIcon(), render);
				}
			}
			
			render.setRenderBounds(0, 0, 0, 1, 1, 1);
			render.renderStandardBlock(block, x, y, z);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_TANKS;
	}
}
