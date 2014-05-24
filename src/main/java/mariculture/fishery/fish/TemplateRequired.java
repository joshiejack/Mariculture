package mariculture.fishery.fish;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.ItemLib;

//TODO: Temperature, Lifespan, Fertility + Survivability + Fish Oil + Fish Meal, of all Fish Based on Real Details
public class TemplateRequired extends FishSpecies {	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 20, 30 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { Salinity.SALINE };
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 200;
	}
	
	@Override
	public int getFoodConsumption() {
		return 1;
	}

	@Override
	public void addFishProducts() {
		addProduct(ItemLib.dropletWater, 5D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.166;
	}

	@Override
	public int getFishMealSize() {
		return 2;
	}
	
	@Override
	public RodType getRodNeeded() {
		return RodType.GOOD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return getCatchChance();
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return getCaughtAliveChance();
	}
}
