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
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class FishPuffer extends FishSpecies {
	public FishPuffer(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.TROPICAL;
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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON), 7.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 1.5D);
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
	}

	@Override
	public int getCatchChance() {
		return 15;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}
	
	@Override
	public double getFishOilVolume() {
		return 3.000;
	}
	
	@Override
	public String getPotionEffect(ItemStack stack) {
		return PotionHelper.field_151423_m;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getFishMealSize() {
		return 4;
	}
}
