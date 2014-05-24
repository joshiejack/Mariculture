package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.fermentedEye;
import static mariculture.core.lib.ItemLib.spiderEye;
import static mariculture.core.lib.ItemLib.string;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishSpider extends FishSpecies {
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -5, 20 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH, BRACKISH };
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 15;
	}

	@Override
	public int getFertility() {
		return 4500;
	}

	@Override
	public int getWaterRequired() {
		return 30;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN ? 5 : 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 2D);
		addProduct(string, 4D);
		addProduct(spiderEye, 2.5D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.350D;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(fermentedEye);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 12;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1, true));
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 1, true));
		}
	}

	@Override
	public boolean canWork(int time) {
		return !Time.isNoon(time);
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isCave(height) || Time.isMidnight(time) ? 25D : 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isCave(height)? 65D: 0D;
	}
}
