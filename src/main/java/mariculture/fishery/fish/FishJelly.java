package mariculture.fishery.fish;

import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishJelly extends FishSpecies {
	public FishJelly(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.JELLY;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 63;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON), 6D);
		addProduct(new ItemStack(Items.slime_ball), 4D);
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
		return 0;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 0;
	}
}
