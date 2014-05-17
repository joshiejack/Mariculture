package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.dropletFrozen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishCod extends FishSpecies {
	public FishCod(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -3, 20 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE, BRACKISH, FRESH };
	}
	
	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 20;
	}

	@Override
	public int getFertility() {
		return 5000;
	}
	
	@Override
	public int getWaterRequired() {
		return 20;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletEarth, 3.5D);
		addProduct(dropletFrozen, 2.5D);
	}

	@Override
	public double getFishOilVolume() {
		return 2.500D;
	}

	@Override
	public int getFishMealSize() {
		return 5;
	}

	@Override
	public int getFoodStat() {
		return 1;
	}

	@Override
	public float getFoodSaturation() {
		return 0.15F;
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.DIRE;
	}

	@Override
	public int getCatchChance() {
		return 20;
	}

	@Override
	public double getCaughtAliveChance() {
		return 55D;
	}
}
