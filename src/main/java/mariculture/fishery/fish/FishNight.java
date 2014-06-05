package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletEnder;
import static mariculture.core.lib.ItemLib.enderPearl;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishNight extends FishSpecies {
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -10, 66 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH, BRACKISH };
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
		return 256;
	}

	@Override
	public int getWaterRequired() {
		return 50;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletEnder, 5D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.333D;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		if (!world.isDaytime()) {
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1800, 0));
		}
	}

	@Override
	public boolean canWork(int time) {
		return !Time.isDay(time);
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public boolean isWorldCorrect(World world) {
		return !world.provider.isHellWorld;
	}

	@Override
	public double getCatchChance(World world, int height, int time) {		
		return world.provider.dimensionId == 1 ? 55D : Height.isCave(height) ? 5D : Time.isMidnight(time) ? 45D : Time.isDusk(time) ? 35D : 0D;
	}

	@Override
	public double getCaughtAliveChance(World world, int height, int time) {
		return world.provider.dimensionId == 1 ? 65D : !Time.isMidnight(time) ? 33D : 0D;
	}
}
