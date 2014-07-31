package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.TickingMeta;
import mariculture.core.render.RenderBase;
import net.minecraft.init.Blocks;

public class RenderHatchery extends RenderBase {
    public RenderHatchery() {}

    @Override
    public void renderBlock() {
        setTexture(Core.ticking.getIcon(0, TickingMeta.NET));
        renderBlock(0.05, 0.05, 0.05, 0.95, 0.9, 0.95);
        if (!isItem()) {
            setTexture(Blocks.water);
            renderFluidBlock(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
            renderFluidBlock(0.1, 0.9, 0.1, 0.9, 0.95, 0.9);
        } else {
            setTexture(Blocks.water);
            renderBlock(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
        }
        
        setTexture(Blocks.planks);
        renderBlock(0.9, 0, 0.9, 1, 1, 1);
        renderBlock(0.0, 0, 0.9, 0.1, 1, 1);
        renderBlock(0.0, 0, 0.0, 0.1, 1, 0.1);
        renderBlock(0.9, 0, 0.0, 1, 1, 0.1);
        renderBlock(0.0, 0.025, 0.0, 1, 0.05, 1);
        renderBlock(0.0, 0.9, 0.0, 0.1, 0.965, 1);
        renderBlock(0.9, 0.9, 0.0, 1, 0.965, 1);
        renderBlock(0.0, 0.9, 0.9, 1, 0.965, 1);
        renderBlock(0.0, 0.9, 0.0, 1, 0.965, 0.1);
    }
}
