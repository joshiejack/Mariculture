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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;

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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 2.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 5D);
		addProduct(new ItemStack(Items.rotten_flesh), 15.0D);
	}

	@Override
	public int getCatchChance() {
		return 8;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}

	@Override
	public double getFishOilVolume() {
		return 1.750;
	}
	
	@Override
	public void affectLiving(EntityLivingBase living) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			EnumDifficulty difficulty = player.worldObj.difficultySetting;
			if (difficulty != EnumDifficulty.PEACEFUL) {
				player.attackEntityFrom(MaricultureDamage.piranha, difficulty.getDifficultyId());
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
