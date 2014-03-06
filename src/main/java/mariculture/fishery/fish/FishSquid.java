package mariculture.fishery.fish;

import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishSquid extends FishSpecies {
	public FishSquid(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.JELLY;
	}

	@Override
	public int getLifeSpan() {
		return 20;
	}

	@Override
	public int getFertility() {
		return 40;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3.5D);
		addProduct(new ItemStack(Items.dye, 1, Dye.INK), 20D);
	}

	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.BAD;
	}
	
	@Override
	public int getCatchChance() {
		return 35;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getFishMealSize() {
		return 0;
	}
}
