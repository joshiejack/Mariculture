package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletAqua;
import static mariculture.core.lib.ItemLib.dropletMagic;
import static mariculture.core.lib.ItemLib.dropletRegen;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.orangeDye;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;

public class FishClown extends FishSpecies {
	public FishClown(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 24, 27 };
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
		return 7;
	}

	@Override
	public int getFertility() {
		return 2000;
	}

	@Override
	public int getBaseProductivity() {
		return 0;
	}

	@Override
	public int getFoodConsumption() {
		return 2;
	}

	@Override
	public int getWaterRequired() {
		return 200;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletAqua, 2D);
		addProduct(dropletRegen, 2.5D);
		addProduct(dropletMagic, 1.0D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.800D;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return orangeDye;
	}
	
	@Override
	public int getLiquifiedProductChance() {
		return 3;
	}

	@Override
	public boolean canWork(int time) {
		return !Time.isMidnight(time);
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.DIRE;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isShallows(height)? 20D: 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isDawn(time) && height >= 48 && height <= 64? 5D: 0D;
	}
}
