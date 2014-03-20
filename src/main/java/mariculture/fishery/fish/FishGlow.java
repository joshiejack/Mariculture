package mariculture.fishery.fish;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 7.5D);
		addProduct(new ItemStack(Items.glowstone_dust), 7.5D);
	}
	
	@Override
	public List<EnumBiomeType> getCatchableBiomes() {
		return Arrays.asList(new EnumBiomeType[] { EnumBiomeType.HELL });
	}

	@Override
	public int getCatchChance() {
		return 15;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.GOOD;
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
