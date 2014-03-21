package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.WaterMeta;
import mariculture.core.render.RenderBase;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderNet extends RenderBase {
	public RenderNet() {}
	
	@Override
	public void renderBlock() {
		setTexture(Core.water.getIcon(0, WaterMeta.NET));
		renderBlock(0, -0.115, 0, 1, -0.05, 1);
	}
}
