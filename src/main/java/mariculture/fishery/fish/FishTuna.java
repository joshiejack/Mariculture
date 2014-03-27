package mariculture.fishery.fish;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishTuna extends FishSpecies {
	public FishTuna(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.OCEAN;
	}

	@Override
	public int getLifeSpan() {
		return 22;
	}

	@Override
	public int getFertility() {
		return 110;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 4D);
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.SUPER;
	}
	
	@Override
	public boolean caughtAsRaw() {
		return true;
	}
	
	@Override
	public int getFoodStat() {
		return 4;
	}

	@Override
	public float getFoodSaturation() {
		return 0.5F;
	}
	
	@Override
	public int getFoodDuration() {
		return 48;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 160, 1));
	}
	
	@Override
	public double getFishOilVolume() {
		return 4.000;
	}
	
	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 3;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 4 };
	}
	
	@Override
	public int getFishMealSize() {
		return 6;
	}
}
