package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishNight extends FishSpecies {
	public FishNight(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.ENDER;
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
		return 5;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(38) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER): null;
	}
	
	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public boolean isWorldCorrect(World world) {
		return world.provider.dimensionId == 1;
	}

	@Override
	public int getCatchChance() {
		return 65;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.155;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		if (!world.isDaytime()) {
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1800, 0));
		}

		player.getFoodStats().addStats(1, 1F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
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
