package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishMinnow extends FishSpecies {
	public FishMinnow(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.RIVER;
	}

	@Override
	public int getLifeSpan() {
		return 5;
	}

	@Override
	public int getFertility() {
		return 16;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(15) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): null;
	}

	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public int getCatchChance() {
		return 45;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.075;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.HARDWORKER.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
