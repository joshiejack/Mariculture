package mariculture.fishery.fish;

import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishGold extends FishSpecies {
	public FishGold(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.DOMESTICATED;
	}

	@Override
	public int getLifeSpan() {
		return 3;
	}

	@Override
	public int getFertility() {
		return 14;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 4D);
	}

	@Override
	public int getCatchChance() {
		return 3;
	}
	
	@Override
	public double getFishOilVolume() {
		return 1.125;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Items.gold_nugget);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 20;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 5 };
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.HARDWORKER.getMultiplier();
	}
}
