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

public class FishTetra extends FishSpecies {
	public FishTetra(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.AMAZONIAN;
	}

	@Override
	public int getLifeSpan() {
		return 7;
	}

	@Override
	public int getFertility() {
		return 22;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(127) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if (world.provider.isSurfaceWorld() && rand.nextInt(4) == 0) {
			if (Fishing.fishHelper.biomeMatches(world.getWorldChunkManager().getBiomeGenAt(x, z),
					new EnumBiomeType[] { EnumBiomeType.HOT, EnumBiomeType.ARID })) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.095;
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
