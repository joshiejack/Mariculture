package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishDragon extends FishSpecies {
	public FishDragon(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.ENDER;
	}

	@Override
	public int getLifeSpan() {
		return 300;
	}

	@Override
	public int getFertility() {
		return 6000;
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public ItemStack getProduct(Random rand) {
		if (rand.nextInt(7200) == 1) {
			return new ItemStack(Block.dragonEgg);
		}

		return (rand.nextInt(20) == 0) ? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER) : null;

	}

	@Override
	public int getTankLevel() {
		return 5;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}

	@Override
	public double getFishOilVolume() {
		return 5.000;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.LAZY.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 6;
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(12, 0.5F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2000, 0));
	}
}
