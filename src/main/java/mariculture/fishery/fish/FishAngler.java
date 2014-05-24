package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletDestroy;
import static mariculture.core.lib.Items.dropletFlux;
import static mariculture.core.lib.Items.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraftforge.common.ForgeDirection;

public class FishAngler extends FishSpecies {
	public FishAngler(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 0, 12 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE };
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
	public int getFoodConsumption() {
		return 3;
	}

	@Override
	public int getWaterRequired() {
		return 110;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN ? 1 : 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletDestroy, 0.5D);
		addProduct(dropletFlux, 1.5D);
	}

	@Override
	public double getFishOilVolume() {
		return 4.555D;
	}

	@Override
	public int getFishMealSize() {
		return 4;
	}

	@Override
	public int getFoodStat() {
		return 2;
	}

	@Override
	public float getFoodSaturation() {
		return 0.2F;
	}

	@Override
	public int getLightValue() {
		return 3;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.SUPER;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isDeep(height) ? 15D : Height.isCave(height) ? 10D : 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isDeep(height) ? 4D : 0D;
	}
	
	@Override
	public boolean hasGenderIcons() {
		return true;
	}
}
