package joshie.mariculture.fishery.render;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.render.RenderBase;

public class RenderFeeder extends RenderBase {
    public RenderFeeder() {}

    @Override
    public void renderBlock() {
        setTexture(Core.renderedMachines, MachineRenderedMeta.FISH_FEEDER);
        renderBlock(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }
}
