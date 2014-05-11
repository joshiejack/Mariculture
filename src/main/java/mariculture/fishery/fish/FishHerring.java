package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletFrozen;
import static mariculture.core.lib.Items.dropletRegen;
import static mariculture.core.lib.Items.dropletWater;
import static mariculture.core.lib.Items.redDye;
import static mariculture.core.lib.Items.redstone;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;

public class FishHerring extends FishSpecies {
	public FishHerring(int id) {
		super(id);
	}
	
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
	public RodQuality getRodNeeded() {
		return RodQuality.OLD;
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
