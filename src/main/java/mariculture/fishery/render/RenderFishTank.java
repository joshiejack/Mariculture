package mariculture.fishery.render;

import mariculture.core.lib.TankMeta;
import mariculture.core.render.RenderBase;
import net.minecraft.init.Blocks;

public class RenderFishTank extends RenderBase {
	public RenderFishTank() {}

	@Override
	public void renderBlock() {
		setTexture(Blocks.water);
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
