package mariculture.core.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.lib.MetalRates;

public class TileBlockCaster extends TileCooling {
	@Override
	public int getInventorySize() {
		return 1;
	}
	
	@Override
	public int getTankSize() {
		return MetalRates.BLOCK + MetalRates.INGOT;
	}
	
	@Override
	public int getTime() {
		return 1200;
	}
	
	@Override
	public RecipeCasting getResult() {
		return MaricultureHandlers.casting.getBlockResult(tank.getFluid());
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}
}
