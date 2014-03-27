package mariculture.fishery.fish;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishTang extends FishSpecies {
	public FishTang(int id) {
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
		return 185;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public double getFishOilVolume() {
		return 0.300;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 6.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 4.5D);
		addProduct(new ItemStack(Item.dyePowder, 1, Dye.LAPIS), 2.0D);
	}
	
	@Override
	public int getFoodStat() {
		return 2;
	}

	@Override
	public float getFoodSaturation() {
		return 0.8F;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.jump.id, 80, 0));
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public int getCatchChance() {
		return 4;
	}
	
	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 1 };
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
