package mariculture.fishery.fish;

import mariculture.api.fishery.ILootHandler.LootQuality;
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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 8.0D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 2.0D);
	}
	
	@Override
	public int getCatchChance() {
		return 4;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.GOOD;
	}
	
	@Override
	public double getFishOilVolume() {
		return 1.000;
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
