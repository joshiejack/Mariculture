package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.dropletAqua;
import static mariculture.core.lib.Items.dropletDestroy;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletWater;
import static mariculture.core.lib.Items.rottenFlesh;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.MaricultureDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishPiranha extends FishSpecies {
	public FishPiranha(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 21, 28 };
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
		return 10;
	}

	@Override
	public int getFertility() {
		return 5000;
	}

	@Override
	public int getFoodConsumption() {
		return 3;
	}

	@Override
	public int getWaterRequired() {
		return 250;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletAqua, 2.5D);
		addProduct(dropletDestroy, 5D);
		addProduct(dropletEarth, 4.0D);
		addProduct(rottenFlesh, 15.0D);
	}

	@Override
	public double getFishOilVolume() {
		return 3.500D;
	}

	@Override
	public int getFishMealSize() {
		return 3;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.harm.id, 20, 0));
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.worldObj.difficultySetting > 0) {
				player.attackEntityFrom(MaricultureDamage.piranha, player.worldObj.difficultySetting);
			}
		} else {
			entity.attackEntityFrom(MaricultureDamage.piranha, 2);
		}
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.FLUX;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Time.isDusk(time) ? 35D : 3D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return height > 70 && height < 80 && Time.isDusk(time) ? 5D : 0D;
	}
}
