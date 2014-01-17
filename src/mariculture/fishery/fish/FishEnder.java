package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.util.Rand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishEnder extends FishSpecies {
	public FishEnder(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.ENDER;
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
	public ItemStack getProduct(Random rand) {
		if (rand.nextInt(19) == 1) {
			return new ItemStack(Item.enderPearl);
		}

		return (rand.nextInt(43) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER): null;
	}

	@Override
	public int getTankLevel() {
		return 5;
	}

	@Override
	public int getCatchChance() {
		return 25;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.GOOD;
	}
	
	@Override
	public boolean isWorldCorrect(World world) {
		return world.provider.dimensionId == 1;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.155;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(2, -0.1F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
		if(!world.isRemote) {
			world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
			int x = (int) ((player.posX) + Rand.rand.nextInt(64) - 32);
			int z = (int) ((player.posZ) + Rand.rand.nextInt(64) - 32);
			if(BlockHelper.chunkExists(world, x, z)) {
				int y = world.getTopSolidOrLiquidBlock(x, z);
				
				if(world.getBlockMaterial(x, y, z) != Material.lava) {
					world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
					player.setPositionAndUpdate(x, y, z);
					world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
				}
			}
		}
	}
}
