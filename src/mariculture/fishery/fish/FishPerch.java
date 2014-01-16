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

public class FishPerch extends FishSpecies {
	public FishPerch(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.OCEAN;
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
		return true;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(13) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): null;
	}
	
	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if (world.provider.isSurfaceWorld()) {
			if (rand.nextInt(128) == 0 && quality.getRank() >= quality.GOOD.getRank()) {
				if (!Fishing.fishHelper.biomeMatches(world.getWorldChunkManager().getBiomeGenAt(x, z), new EnumBiomeType[] {
						EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.OCEAN, EnumBiomeType.ARID })) {
					return true;
				}
			}
		}

		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 1.000;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}
}
