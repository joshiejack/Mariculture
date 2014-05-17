package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletNether;
import static mariculture.core.lib.ItemLib.netherWart;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishNether extends FishSpecies {
	public FishNether(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 45, 100 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH };
	}

	@Override
	public boolean isLavaFish() {
		return true;
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
		return 666;
	}

	@Override
	public int getWaterRequired() {
		return 55;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletNether, 5D);
		addProduct(netherWart, 1D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.666D;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120, 0));
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public boolean isWorldCorrect(World world) {
		return world.provider.isHellWorld;
	}

	@Override
	public int getCatchChance() {
		return 45;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isShallows(height) ? 55D : 5D;
	}
}
