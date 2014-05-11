package mariculture.fishery.fish;

import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishMantaRay extends FishSpecies {
	public FishMantaRay(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.FLATFISH;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 185;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 2D);
	}
	
	@Override
	public int getFoodStat() {
		return 3;
	}

	@Override
	public float getFoodSaturation() {
		return 0.7F;
	}
	
	@Override
	public int getFoodDuration() {
		return 96;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 1));
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
		return 0.250;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 2;
	}
}
