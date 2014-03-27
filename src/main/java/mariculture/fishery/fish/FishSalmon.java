package mariculture.fishery.fish;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishSalmon extends FishSpecies {
	public FishSalmon(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.RIVER;
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
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 5D);
	}
	
	@Override
	public int getFoodStat() {
		return 5;
	}

	@Override
	public float getFoodSaturation() {
		return 1F;
	}
	
	@Override
	public int getFoodDuration() {
		return 64;
	}
	
	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.900;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 8 };
	}
	
	@Override
	public int getFishMealSize() {
		return 5;
	}
}
