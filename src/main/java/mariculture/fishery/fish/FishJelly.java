package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletPoison;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.slimeBall;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
public class FishJelly extends FishSpecies {
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 12, 32 };
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
		return 1;
	}

	@Override
	public int getFertility() {
		return 1;
	}

	@Override
	public int getFoodConsumption() {
		return 0;
	}

	@Override
	public boolean requiresFood() {
		return false;
	}

	@Override
	public int getWaterRequired() {
		return 65;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletPoison, 6D);
		addProduct(slimeBall, 4D);
	}

	@Override
	public double getFishOilVolume() {
		return 0D;
	}

	@Override
	public int getFishMealSize() {
		return 0;
	}

	@Override
	public int getFoodStat() {
		return 1;
	}

	@Override
	public float getFoodSaturation() {
		return 0.1F;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.harm.id, 15, 0));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(Potion.poison.id, 1, 200));
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.GOOD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Time.isDay(time)? 15D: 5D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isNoon(time) && Height.isCave(height)? 5D: 0D;
	}
}
