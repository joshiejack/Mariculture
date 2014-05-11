package mariculture.fishery.fish;

import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishTetra extends FishSpecies {
	public FishTetra(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.AMAZONIAN;
	}

	@Override
	public int getLifeSpan() {
		return 7;
	}

	@Override
	public int getFertility() {
		return 22;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 4.0D);
	}

	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public int getFoodDuration() {
		return 4;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 160, 0));
	}
	
	@Override
	public int getCatchChance() {
		return 65;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.095;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.HARDWORKER.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
