package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.TankMeta;
import mariculture.core.render.RenderBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderFishTank extends RenderBase {
	public RenderFishTank(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		setTexture(Block.waterStill);
		if(!isItem()) {
			renderBlock(0, 0, 0, 1, 1, 1);
		}
		
		if(isItem()) {
			setTexture(block, TankMeta.FISH);
		}
		
		render.clearOverrideBlockTexture();
		renderBlock(0, 0, 0, 1, 1, 1);
	}
}
