package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletAqua;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;

public class FishCatfish extends FishSpecies {
	public FishCatfish(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 20, 45 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH, BRACKISH, SALINE };
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 22;
	}

	@Override
	public int getFertility() {
		return 4000;
	}

	@Override
	public int getFoodConsumption() {
		return 2;
	}

	@Override
	public int getWaterRequired() {
		return 100;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 8.0D);
		addProduct(dropletAqua, 2.0D);
		addProduct(dropletEarth, 5.0D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.950D;
	}
	
	@Override
	public int getFishMealSize() {
		return 7;
	}

	@Override
	public float getFoodSaturation() {
		return 0.8F;
	}

	@Override
	public int getFoodDuration() {
		return 48;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isShallows(height)? 25D: 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isShallows(height) && !Time.isMidnight(time)? 5D: 0D;
	}
}
