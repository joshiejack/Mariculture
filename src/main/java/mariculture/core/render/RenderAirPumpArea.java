package mariculture.core.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderAirPumpArea extends RenderBase {
	public RenderAirPumpArea(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		setTexture(Block.anvil);
		renderBlock(0, 0, 0, 1, 1, 1);
	}
}
