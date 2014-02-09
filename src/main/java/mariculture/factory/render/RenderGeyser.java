package mariculture.factory.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.common.ForgeDirection;
import mariculture.core.render.RenderBase;

public class RenderGeyser extends RenderBase {
	public RenderGeyser(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		if(dir == ForgeDirection.NORTH) {
			setTexture(Block.hopperBlock);
			renderBlock(0.1, 0.1, 0.85, 0.9, 0.9, 1);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.25, 0.25, 0.76, 0.75, 0.75, 0.85);
			setTexture(Block.waterStill);
			renderBlock(0.35, 0.35, 0.75, 0.65, 0.65, 0.76);
		} else if(dir == ForgeDirection.SOUTH) {
			setTexture(Block.hopperBlock);
			renderBlock(0.1, 0.1, 0, 0.9, 0.9, 0.15);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.25, 0.25, 0.15, 0.75, 0.75, 0.24);
			setTexture(Block.waterStill);
			renderBlock(0.35, 0.35, 0.24, 0.65, 0.65, 0.25);
		} else if (dir == ForgeDirection.WEST) {
			setTexture(Block.hopperBlock);
			renderBlock(0.85, 0.1, 0.1, 1, 0.9, 0.9);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.76, 0.25, 0.25, 0.85, 0.75, 0.75);
			setTexture(Block.waterStill);
			renderBlock(0.75, 0.35, 0.35, 0.76, 0.65, 0.65);
		} else if(dir == ForgeDirection.EAST) {
			setTexture(Block.hopperBlock);
			renderBlock(0, 0.1, 0.1, 0.15, 0.9, 0.9);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.15, 0.25, 0.25, 0.24, 0.75, 0.75);
			setTexture(Block.waterStill);
			renderBlock(0.24, 0.35, 0.35, 0.25, 0.65, 0.65);
		} else if(dir == ForgeDirection.UP) {
			setTexture(Block.hopperBlock);
			renderBlock(0.1, 0, 0.1, 0.9, 0.15, 0.9);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.25, 0.15, 0.25, 0.75, 0.24, 0.75);
			setTexture(Block.waterStill);
			renderBlock(0.35, 0.24, 0.35, 0.65, 0.25, 0.65);
		} else if(dir == ForgeDirection.DOWN) {
			setTexture(Block.hopperBlock);
			renderBlock(0.1, 0.85, 0.1, 0.9, 1, 0.9);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.25, 0.76, 0.25, 0.75, 0.85, 0.75);
			setTexture(Block.waterStill);
			renderBlock(0.35, 0.75, 0.35, 0.65, 0.76, 0.65);
		} else {
			setTexture(Block.hopperBlock);
			renderBlock(0D, 0D, 0D, 1D, 0.1D, 1D);
			setTexture(Block.stoneSingleSlab);
			renderBlock(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
			setTexture(Block.waterStill);
			renderBlock(0.25D, 0.15D, 0.25D, 0.75D, 0.16D, 0.75D);
		}
	}
}
