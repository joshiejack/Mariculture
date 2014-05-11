package mariculture.fishery.fish;

import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishDamsel extends FishSpecies {
	public FishDamsel(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.TROPICAL;
	}

	@Override
	public int getLifeSpan() {
		return 17;
	}

	@Override
	public int getFertility() {
		return 44;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 4.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 0.5D);
	}
	
	@Override
	public boolean caughtAsRaw() {
		return false;
	}

	@Override
	public int getCatchChance() {
		return 45;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.450;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0));
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
