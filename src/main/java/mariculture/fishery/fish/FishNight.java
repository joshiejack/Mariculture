package mariculture.fishery.fish;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER), 5D);
		addProduct(new ItemStack(Items.ender_pearl), 1D);
	}
	
	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public List<EnumBiomeType> getCatchableBiomes() {
		return Arrays.asList(new EnumBiomeType[] { EnumBiomeType.ENDER });
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
