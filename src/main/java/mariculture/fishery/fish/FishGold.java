package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.goldNugget;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;

public class FishGold extends FishSpecies {
	public FishGold(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -5, 22 };
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
		return 6;
	}

	@Override
	public int getFertility() {
		return 500;
	}

	@Override
	public int getBaseProductivity() {
		return 2;
	}
	
	@Override
	public int getWaterRequired() {
		return 25;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 4D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.125D;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(goldNugget);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 20;
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public int getCatchChance() {
		return 15;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isNoon(time)? 5D: 0D;
	}
}
