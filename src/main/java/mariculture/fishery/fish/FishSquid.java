package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletWater;
import static mariculture.core.lib.Items.ink;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;

public class FishSquid extends FishSpecies {
	public FishSquid(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 3, 20 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE, BRACKISH };
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getLifeSpan() {
		return 5;
	}

	@Override
	public int getFertility() {
		return 2500;
	}

	@Override
	public boolean requiresFood() {
		return true;
	}
	
	@Override
	public int getWaterRequired() {
		return 20;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 1.5D);
		addProduct(ink, 15D);
	}

	@Override
	public double getFishOilVolume() {
		return 0D;
	}

	@Override
	public int getFishMealSize() {
		return 0;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.OLD;
	}

	@Override
	public int getCatchChance() {
		return 10;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isDay(time) ? 75D : 45D;
	}
}
