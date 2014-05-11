package mariculture.core.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.lib.MetalRates;

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
