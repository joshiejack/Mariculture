package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletPoison;

import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishStingRay extends FishSpecies {
	public FishStingRay(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 19, 29 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { BRACKISH, SALINE, FRESH };
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 1;
	}
	
	@Override
	public int getWaterRequired() {
		return 20;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletPoison, 5D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.725D;
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 800, 1));
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			Random rand = new Random();
			int difficulty = player.worldObj.difficultySetting;
			if (difficulty > 0) {
				int chance = 40 - (difficulty * 10);
				if (rand.nextInt(chance) == 0) {
					player.addPotionEffect(new PotionEffect(Potion.poison.id, difficulty * 100, 1, true));
				}
			}
		}
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.OLD;
	}

	@Override
	public int getCatchChance() {
		return 35;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isDay(time) ? 50D : 25D;
	}
}
