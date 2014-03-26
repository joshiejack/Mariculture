package mariculture.fishery.fish;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishManOWar extends FishSpecies {
	public FishManOWar(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.JELLY;
	}

	@Override
	public int getLifeSpan() {
		return 30;
	}

	@Override
	public int getFertility() {
		return 150;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getCatchChance() {
		return 7;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.SUPER;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 4.5D);
	}
	
	@Override
	public int getFoodStat() {
		return 1;
	}

	@Override
	public float getFoodSaturation() {
		return 0.1F;
	}
	
	@Override
	public int getFoodDuration() {
		return 8;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.harm.id, 5, 1));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 1000, 1));
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getFishMealSize() {
		return 0;
	}
}
