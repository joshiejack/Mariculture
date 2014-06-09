package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.TickingMeta;
import mariculture.core.render.RenderBase;

public class RenderNet extends RenderBase {
    public RenderNet() {}

    @Override
    public void renderBlock() {
        setTexture(Core.ticking.getIcon(0, TickingMeta.NET));
        renderBlock(0, -0.115, 0, 1, -0.05, 1);
    }
}
