package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishCatfish extends FishSpecies {
	public FishCatfish(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.AMAZONIAN;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 135;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(111) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if (world.provider.isSurfaceWorld()) {
			if (quality.getRank() >= EnumRodQuality.GOOD.getRank()) {
				EnumBiomeType biome = MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(x, z));
				if (biome.isSaltWater()) {
					if (rand.nextInt(256) == 0) {
						return true;
					}
				} else {
					if (rand.nextInt(128) == 0) {
						return true;
					}
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
	public void onConsumed(final World world, final EntityPlayer player) {
		player.getFoodStats().addStats(3, 0.5F);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 5 };
	}
	
	@Override
	public int getFishMealSize() {
		return 4;
	}
}
