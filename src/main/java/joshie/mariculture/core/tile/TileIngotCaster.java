package joshie.mariculture.core.tile;

import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.api.core.RecipeCasting;
import joshie.mariculture.core.lib.MetalRates;

public class TileIngotCaster extends TileCooling {
    @Override
    public int getInventorySize() {
        return 4;
    }

    @Override
    public int getTankSize() {
        return MetalRates.INGOT * 4;
    }

    @Override
    public int getTime() {
        return 800;
    }

    @Override
    public RecipeCasting getResult() {
        return MaricultureHandlers.casting.getIngotResult(tank.getFluid());
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1, 2, 3 };
    }
}
