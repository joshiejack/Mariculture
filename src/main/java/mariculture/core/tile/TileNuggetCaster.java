package mariculture.core.tile;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.recipes.RecipeCasting;
import mariculture.core.lib.MetalRates;

public class TileNuggetCaster extends TileCooling {
    @Override
    public int getInventorySize() {
        return 16;
    }

    @Override
    public int getTankSize() {
        return MetalRates.NUGGET * 16;
    }

    @Override
    public int getTime() {
        return 600;
    }

    @Override
    public RecipeCasting getResult() {
        return MaricultureHandlers.casting.getNuggetResult(tank.getFluid());
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    }
}
