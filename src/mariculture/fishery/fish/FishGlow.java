package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishGlow extends FishSpecies {
	public FishGlow(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NETHER;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 83;
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public ItemStack getProduct(Random rand) {
		if (rand.nextInt(38) == 1) {
			return new ItemStack(Item.glowstone);
		}

		return (rand.nextInt(50) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.155;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
