package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPuffer extends FishSpecies {
	public FishPuffer(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.TROPICAL;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 370;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(44) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON): null;
	}

	@Override
	public int getCatchChance() {
		return 15;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.SUPER;
	}
	
	@Override
	public double getFishOilVolume() {
		return 3.000;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getFishMealSize() {
		return 4;
	}
}
