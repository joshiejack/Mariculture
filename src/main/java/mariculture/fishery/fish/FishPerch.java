package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FishPerch extends FishSpecies {
	public FishPerch(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 8, 15 };
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
		return 10;
	}

	@Override
	public int getFertility() {
		return 2300;
	}

	@Override
	public int getWaterRequired() {
		return 25;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 8D);
		addProduct(dropletEarth, 2.5D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.220D;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	@Override
	public int getFishMealSize() {
		return 3;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.OLD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Time.isDay(time) ? 25D : 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isShallows(height) ? 15D : 0D;
	}
}
