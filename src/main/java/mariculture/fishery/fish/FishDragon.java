package mariculture.fishery.fish;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER), 10D);
		addProduct(new ItemStack(Items.ender_pearl), 10D);
		addProduct(new ItemStack(Blocks.dragon_egg), 0.1D);
	}

	@Override
	public int getTankLevel() {
		return 5;
	}

	@Override
	public int getCatchChance() {
		return 2;
	}
	
	@Override
	public List<EnumBiomeType> getCatchableBiomes() {
		return Arrays.asList(new EnumBiomeType[] { EnumBiomeType.ENDER });
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
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
