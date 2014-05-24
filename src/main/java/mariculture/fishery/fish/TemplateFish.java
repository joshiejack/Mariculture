package mariculture.fishery.fish;

import java.util.ArrayList;
import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.CachedCoords;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TemplateFish extends FishSpecies {
	public TemplateFish(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 20, 30 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { Salinity.SALINE };
	}
	
	@Override
	public boolean isLavaFish() {
		return false;
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
		return 200;
	}

	@Override
	public int getSurvivability() {
		return 150;
	}

	@Override
	public int getBaseProductivity() {
		return 1;
	}

	@Override
	public int getFoodConsumption() {
		return 1;
	}

	@Override
	public boolean requiresFood() {
		return true;
	}

	@Override
	public int getWaterRequired() {
		return 1;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(Items.dropletWater, 5D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.166;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.bone);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 10;
	}

	@Override
	public int getFishMealSize() {
		return 2;
	}

	@Override
	public int getFoodStat() {
		return 1;
	}

	@Override
	public float getFoodSaturation() {
		return 0.3F;
	}

	@Override
	public int getFoodDuration() {
		return 32;
	}

	@Override
	public boolean canAlwaysEat() {
		return false;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		return;
	}

	@Override
	public ItemStack onRightClick(World world, EntityPlayer player, ItemStack stack, Random rand) {
		return stack;
	}

	@Override
	public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
		return;
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		return;
	}

	@Override
	public int getLightValue() {
		return 0;
	}

	@Override
	public boolean canWork(int time) {
		return true;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public boolean isWorldCorrect(World world) {
		return !world.provider.isHellWorld && world.provider.dimensionId != 1;
	}

	@Override
	public double getCatchChance(World world, Salinity salt, int temp, int time, int height) {
		return MaricultureHandlers.environment.matches(salt, temp, salinity, temperature)? getCatchChance(world, height, time): 0D;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return getCatchChance();
	}

	@Override
	public int getCatchChance() {
		return 5;
	}

	@Override
	public double getCaughtAliveChance(World world, Salinity salt, int temp, int time, int height) {
		return isAcceptedTemperature(temp) && salt == salinity[0]? getCaughtAliveChance(height, time): 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return getCaughtAliveChance();
	}

	@Override
	public double getCaughtAliveChance() {
		return 0D;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 3, 5 };
	}
}
