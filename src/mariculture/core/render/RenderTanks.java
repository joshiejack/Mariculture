package mariculture.core.render;

import mariculture.core.blocks.TileTankBlock;
import mariculture.core.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import tconstruct.client.TProxyClient;
import tconstruct.client.block.BlockSkinRenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTanks implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int meta, int id, RenderBlocks render) {
		if (id == RenderIds.BLOCK_TANKS) {
			TProxyClient.renderStandardInvBlock(render, block, meta);
			if (meta == 0) {
				render.setRenderBounds(0.1875, 0, 0.1875, 0.8125, 0.125, 0.8125);
				renderDoRe(render, block, meta);
			}
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks render) {
		if (modelID == RenderIds.BLOCK_TANKS) {
			TileTankBlock tank = (TileTankBlock) world.getBlockTileEntity(x, y, z);
			if(tank != null) {
				if(tank.tank.getFluidAmount() > 0) {
					FluidStack fluidStack = tank.tank.getFluid();
					render.setRenderBounds(0.001, 0.001, 0.001, 0.999, tank.getFluidAmountScaled(), 0.999);
					Fluid fluid = fluidStack.getFluid();
					BlockSkinRenderHelper.renderLiquidBlock(fluid.getStillIcon(), fluid.getFlowingIcon(), x, y, z, render, world);
					render.setRenderBounds(00, 0.001, 0.001, 0.999, tank.getFluidAmountScaled(), 0.999);
				}
			}

			// Block
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

	private void renderDoRe(RenderBlocks renderblocks, Block block, int meta) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, 0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
