package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.fishery.EntityBass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishBass extends FishSpecies {
	public FishBass(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.RIVER;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 125;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 4.0D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 5.0D);
		addProduct(new ItemStack(Items.gunpowder), 7.5D);
	}
	
	@Override
	public int getCatchChance() {
		return 5;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.450;
	}

	@Override
	public ItemStack onRightClick(World world, EntityPlayer player, ItemStack stack, Random rand) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityBass(world, player));
		}

		return stack;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 4 };
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}
}
