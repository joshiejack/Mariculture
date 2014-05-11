package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.render.RenderBase;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderNet extends RenderBase {
	public RenderNet(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		setTexture(Core.oyster.getIcon(0, BlockOyster.NET));
		renderBlock(0, -0.115, 0, 1, -0.05, 1);
	}
}
