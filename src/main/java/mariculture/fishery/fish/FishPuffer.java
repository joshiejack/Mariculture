package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.dropletDestroy;
import static mariculture.core.lib.Items.dropletPoison;
import static mariculture.core.lib.Items.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishPuffer extends FishSpecies {
	public FishPuffer(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 20, 45 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { BRACKISH, FRESH };
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getLifeSpan() {
		return 10;
	}

	@Override
	public int getFertility() {
		return 3;
	}

	@Override
	public int getFoodConsumption() {
		return 3;
	}

	@Override
	public int getWaterRequired() {
		return 115;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletPoison, 7.5D);
		addProduct(dropletDestroy, 1.5D);
	}

	@Override
	public double getFishOilVolume() {
		return 4.625D;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public int getCatchChance() {
		return 17;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isDawn(time) && Height.isShallows(height)? 5D: 0D;
	}
}
