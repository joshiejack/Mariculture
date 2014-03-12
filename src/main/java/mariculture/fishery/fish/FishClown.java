package mariculture.fishery.fish;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.world.WorldPlus;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishClown extends FishSpecies {
	public FishClown(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NEMO;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 370;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 2D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 2.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC), 1.0D);
		addProduct(new ItemStack(Items.dye, 1, Dye.ORANGE), 10.0D);
		if(Modules.world.isActive()) addProduct(new ItemStack(WorldPlus.plantStatic, 1, CoralMeta.ORANGE), 5.0D);
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public int getCatchChance() {
		return 2;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.195;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 1 };
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.LAZY.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
