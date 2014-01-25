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

public class FishTuna extends FishSpecies {
	public FishTuna(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.OCEAN;
	}

	@Override
	public int getLifeSpan() {
		return 22;
	}

	@Override
	public int getFertility() {
		return 110;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 4D);
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if (world.provider.isSurfaceWorld()) {
			if (rand.nextInt(512) == 0 && quality.getRank() >= quality.SUPER.getRank()) {
				if (Fishing.fishHelper.biomeMatches(world.getWorldChunkManager().getBiomeGenAt(x, z), new EnumBiomeType[] {
						EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN })) {
					return true;
				}
			}
		}

		return false;
	}
	
	@Override
	public boolean caughtAsRaw() {
		return true;
	}
	
	@Override
	public double getFishOilVolume() {
		return 4.000;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 3;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 4 };
	}
	
	@Override
	public int getFishMealSize() {
		return 6;
	}
}
