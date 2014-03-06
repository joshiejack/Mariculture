package mariculture.fishery.fish;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
	public LootQuality getLootQuality() {
		return LootQuality.GOOD;
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
