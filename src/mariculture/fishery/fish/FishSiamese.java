package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(60) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
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
