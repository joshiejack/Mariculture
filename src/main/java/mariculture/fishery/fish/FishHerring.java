package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletFrozen;
import static mariculture.core.lib.ItemLib.dropletRegen;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.redstone;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;

public class FishHerring extends FishSpecies {
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 10, 18 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE };
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getLifeSpan() {
		return 16;
	}

	@Override
	public int getFertility() {
		return 5000;
	}

	@Override
	public int getFoodConsumption() {
		return 2;
	}

	@Override
	public int getWaterRequired() {
		return 60;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletRegen, 0.5D);
		addProduct(dropletFrozen, 3D);
		addProduct(redstone, 1D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.050D;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(redstone);
	}
	
	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public int getCatchChance() {
		return 7;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isDusk(time) && Height.isShallows(height)? 5D: 0D;
	}
}
