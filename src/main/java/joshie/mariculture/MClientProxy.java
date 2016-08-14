package joshie.mariculture;


import net.minecraft.client.renderer.block.statemap.StateMap;

import static net.minecraft.block.BlockLiquid.LEVEL;

public class MClientProxy extends MCommonProxy {
    public static final StateMap NO_WATER = new StateMap.Builder().ignore(LEVEL).build();

    @Override
    public boolean isClient() {
        return true;
    }
}