package mariculture.fishery.render;

import mariculture.core.render.RenderBase;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderFishTank extends RenderBase {
	public RenderFishTank() {}

	@Override
	public void renderBlock() {		
		if(isItem()) {
			setTexture(FluidRegistry.WATER.getIcon());
			renderBlock(0, 0, 0, 1, 1, 1);
			render.clearOverrideBlockTexture();
			renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
		} else {
			IIcon iicon = render.getBlockIcon(block, render.blockAccess, x, y, z, 0);
			double offset = 0.0625;
			Block down = world.getBlock(x, y - 1, z);
			int downMeta = world.getBlockMetadata(x, y - 1, z);
			Block up = world.getBlock(x, y + 1, z);
			int upMeta = world.getBlockMetadata(x, y + 1, z);
			Block xPlus1 = world.getBlock(x + 1, y, z);
			int xPlus1Meta = world.getBlockMetadata(x + 1, y, z);
			Block xMinus1 = world.getBlock(x - 1, y, z);
			int xMinus1Meta = world.getBlockMetadata(x - 1, y, z);
			Block zPlus1 = world.getBlock(x, y, z + 1);
			int zPlus1Meta = world.getBlockMetadata(x, y, z + 1);
			Block zMinus1 = world.getBlock(x, y, z - 1);
			int zMinus1Meta = world.getBlockMetadata(x, y, z - 1);
			
			/* if(!(down == block && downMeta == TankMeta.FISH)) {
				//render.renderFaceYNeg(block, x, y, z, iicon);
				renderFace(0, -offset, 0, 0, -offset, 0, 0, -offset, 0, 0, -offset, 0);
			}
			
			if(!(up == block && upMeta == TankMeta.FISH)) {
				//render.renderFaceYPos(block, x, y, z, iicon);
				renderFace(0, 1 - offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0);
			}
			
			if(!(xPlus1 == block && xPlus1Meta == TankMeta.FISH)) {
				//render.renderFaceXPos(block, x, y, z, iicon);
				renderFace(0, -offset, 0, 0, -offset, 0, 1, 1 - offset, 0, 1, 1 - offset, 0);
			}
			
			if(!(xMinus1 == block && xMinus1Meta == TankMeta.FISH)) {
				//render.renderFaceXNeg(block, x, y, z, iicon);
				renderFace(-1, -offset, 0, -1, -offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0);
			}
			
			if(!(zPlus1 == block && zPlus1Meta == TankMeta.FISH)) {
				//render.renderFaceZPos(block, x, y, z, iicon);
				renderFace(0, -offset, 1, -1, -offset, 0, 0, 1-offset, 0, 1, 1 - offset, 1);
			}
			
			if(!(zMinus1 == block && zMinus1Meta == TankMeta.FISH)) {
				//render.renderFaceZNeg(block, x, y, z, iicon);
				renderFace(0, -offset, 0, -1, -offset, -1, 0, 1-offset, -1, 1, 1 - offset, 0);
			} */
			
			setTexture(FluidRegistry.WATER.getIcon());
			renderBlock(0.005, 0.005, 0.005, 0.995, 0.995, 0.995);
			render.clearOverrideBlockTexture();
			renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
			
			/*
			setTexture(block, TankMeta.FISH);
			render.clearOverrideBlockTexture();
			if(!(down == block && downMeta == TankMeta.FISH)) {
				render.renderFaceXNeg(block, x, y, z, p_147798_8_);
				renderFace(0, -offset, 0, 0, -offset, 0, 0, -offset, 0, 0, -offset, 0);
			}
			
			if(!(up == block && upMeta == TankMeta.FISH)) {
				renderFace(0, 1 - offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0);
			}
			
			if(!(xPlus1 == block && xPlus1Meta == TankMeta.FISH)) {
				renderFace(0, -offset, 0, 0, -offset, 0, 1, 1 - offset, 0, 1, 1 - offset, 0);
			}
			
			if(!(xMinus1 == block && xMinus1Meta == TankMeta.FISH)) {
				renderFace(-1, -offset, 0, -1, -offset, 0, 0, 1 - offset, 0, 0, 1 - offset, 0);
			}
			
			if(!(zPlus1 == block && zPlus1Meta == TankMeta.FISH)) {
				renderFace(0, -offset, 1, -1, -offset, 0, 0, 1-offset, 0, 1, 1 - offset, 1);
			}
			
			if(!(zMinus1 == block && zMinus1Meta == TankMeta.FISH)) {
				renderFace(0, -offset, 0, -1, -offset, -1, 0, 1-offset, -1, 1, 1 - offset, 0);
			} */
		}
		//renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
	}
}
