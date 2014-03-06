package mariculture.fishery.fish;

import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FishKoi extends FishSpecies {
	public FishKoi(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.DOMESTICATED;
	}

	@Override
	public int getLifeSpan() {
		return 150;
	}

	@Override
	public int getFertility() {
		return 1500;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 4D);
	}

	@Override
	public int getCatchChance() {
		return 10;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}
	
	@Override
	public double getFishOilVolume() {
		return 2.500;
	}

	@Override
	public void affectLiving(EntityLivingBase living) {
		living.addPotionEffect(new PotionEffect(Potion.regeneration.id, 33, 1, true));
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 2 };
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.NORMAL.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 5;
	}
}
