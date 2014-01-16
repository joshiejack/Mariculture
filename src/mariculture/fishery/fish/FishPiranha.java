package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPiranha extends FishSpecies {
	public FishPiranha(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.AMAZONIAN;
	}

	@Override
	public int getLifeSpan() {
		return 50;
	}

	@Override
	public int getFertility() {
		return 500;
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public ItemStack getProduct(Random rand) {
		if (rand.nextInt(5) == 1) {
			return new ItemStack(Item.rottenFlesh);
		}

		return (rand.nextInt(86) == 0) ? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK) : null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}

	@Override
	public double getFishOilVolume() {
		return 1.750;
	}
	
	@Override
	public void affectLiving(EntityLivingBase living) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if (player.worldObj.difficultySetting > 0) {
				player.attackEntityFrom(MaricultureDamage.piranha, player.worldObj.difficultySetting);
			}
		} else {
			living.attackEntityFrom(MaricultureDamage.piranha, 2);
		}
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}
}
