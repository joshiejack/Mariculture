package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.bone;
import static mariculture.core.lib.Items.bonemeal;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.skull;
import static mariculture.core.lib.Items.witherSkull;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.CachedCoords;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Rand;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class FishBoneless extends FishSpecies {
	public FishBoneless(int id) {
		super(id);
	}

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
		return false;
	}

	@Override
	public int getLifeSpan() {
		return 30;
	}

	@Override
	public int getFertility() {
		return 50;
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
		return 150;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? 1 : 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletEarth, 1D);
		addProduct(bonemeal, 5D);
		addProduct(bone, 1.5D);
		addProduct(skull, 1D);
		addProduct(witherSkull, 0.1D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.0D;
	}

	@Override
	public int getLiquifiedProductChance() {
		return 1;
	}

	@Override
	public int getFishMealSize() {
		return 0;
	}

	@Override
	public int getFoodStat() {
		return 0;
	}

	@Override
	public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
		if (Rand.nextInt(500)) {
			EntitySkeleton skeleton = new EntitySkeleton(world);
			skeleton.setPosition(x, y, z);
			if (Rand.nextInt(5000)) {
				skeleton.setSkeletonType(1);
				skeleton.setCurrentItemOrArmor(0, new ItemStack(Item.swordStone));
				skeleton.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(4.0D);
			}

			world.spawnEntityInWorld(skeleton);
		}
	}

	@Override
	public boolean canWork(int time) {
		return Time.isNoon(time);
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isCave(height) ? 10D : Time.isMidnight(time) ? 10D : 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Height.isDeep(height) ? 5D : 0D;
	}
}
