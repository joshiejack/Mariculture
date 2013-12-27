package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishManOWar extends FishSpecies {
	public FishManOWar(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.JELLY;
	}

	@Override
	public int getLifeSpan() {
		return 30;
	}

	@Override
	public int getFertility() {
		return 150;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0;
	}

	@Override
	public ItemStack getProduct(Random rand) {
		if (rand.nextInt(900) == 1) {
			return new ItemStack(Item.potion, 1, 16388);
		}

		return (rand.nextInt(100) == 0) ? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK) : null;

	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getFishMealSize() {
		return 0;
	}
}
