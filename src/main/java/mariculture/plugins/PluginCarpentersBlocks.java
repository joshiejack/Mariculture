package mariculture.plugins;

import mariculture.core.blocks.BlockAir;
import mariculture.plugins.Plugins.Plugin;

public class PluginCarpentersBlocks extends Plugin {
    public PluginCarpentersBlocks(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        BlockAir.flammables.add(getBlock("blockCarpentersTorch"));
    }

    @Override
    public void postInit() {
        return;
    }
}
