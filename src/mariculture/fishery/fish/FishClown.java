package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.world.WorldPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishClown extends FishSpecies {
	public FishClown(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NEMO;
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
		if (Modules.world.isActive()) {
			if (rand.nextInt(25) == 1) {
				return new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_ORANGE);
			}
		} else {
			if (rand.nextInt(25) == 1) {
				return new ItemStack(Item.dyePowder, 1, Dye.ORANGE);
			}
		}

		return (rand.nextInt(673) == 0) ? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC) : null;
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.195;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 1 };
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.LAZY.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
