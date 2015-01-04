package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.render.RenderBase;

public class RenderFeeder extends RenderBase {
    public RenderFeeder() {}

    @Override
    public void renderBlock() {
        setTexture(Core.renderedMachines, MachineRenderedMeta.FISH_FEEDER);
        renderBlock(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
}
