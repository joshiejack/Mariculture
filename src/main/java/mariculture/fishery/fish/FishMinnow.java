package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;

public class FishMinnow extends FishSpecies {
	public FishMinnow(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -1, 45 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH };
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 5;
	}

	@Override
	public int getFertility() {
		return 1000;
	}

	@Override
	public int getBaseProductivity() {
		return 2;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 15D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.240D;
	}

	@Override
	public int getFishMealSize() {
		return 1;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.OLD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		if (Time.isDay(time) && height > 70) {
			return 33D;
		} else return 5D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return !Time.isMidnight(time) && height > 70 ? 75D : 20D;
	}
}
