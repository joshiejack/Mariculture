package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletPlant;
import static mariculture.core.lib.ItemLib.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishSalmon extends FishSpecies {
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 0, 23 };
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
		return 8;
	}

	@Override
	public int getFertility() {
		return 2500;
	}

	@Override
	public int getWaterRequired() {
		return 40;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 5D);
		addProduct(dropletPlant, 5.0D);
	}

	@Override
	public double getFishOilVolume() {
		return 4.500D;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Items.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	@Override
	public int getFishMealSize() {
		return 5;
	}

	@Override
	public int getFoodStat() {
		return 5;
	}

	@Override
	public float getFoodSaturation() {
		return 0.5F;
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.DIRE;
	}

	@Override
	public int getCatchChance() {
		return 25;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isHigh(height) && Time.isNoon(time) ? 5D : 0D;
	}
}
