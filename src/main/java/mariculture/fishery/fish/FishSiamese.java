package mariculture.fishery.fish;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishSiamese extends FishSpecies {
	public FishSiamese(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.DOMESTICATED;
	}

	@Override
	public int getLifeSpan() {
		return 50;
	}

	@Override
	public int getFertility() {
		return 250;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 1.500;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 4D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 3D);
	}

	@Override
	public int getCatchChance() {
		return 9;
	}
	
	@Override
	public int getFoodStat() {
		return 2;
	}

	@Override
	public float getFoodSaturation() {
		return 0.65F;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		if(world.isDaytime()) player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
	}
	
	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}
	
	@Override
	public void affectLiving(EntityLivingBase living) {
		if(living instanceof EntityPlayer) {
			return;
		} else {
			living.attackEntityFrom(MaricultureDamage.piranha, 3);
		}
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getFishMealSize() {
		return 2;
	}
}
