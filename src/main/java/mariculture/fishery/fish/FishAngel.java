package mariculture.fishery.fish;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishAngel extends FishSpecies {
	public FishAngel(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.TROPICAL;
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
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 1.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 2.0D);
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.800;
	}
	
	@Override
	public int getCatchChance() {
		return 8;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.GOOD;
	}

	@Override
	public ItemStack onRightClick(World world, EntityPlayer player, ItemStack stack, Random rand) {
		if (!world.isRemote) {
			List list = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(64D, 64D, 64D));

			for (Object i : list) {
				EntityItem item = (EntityItem) i;
				item.setPosition(player.posX, player.posY, player.posZ);
			}
		}

		return stack;
	}

	@Override
	public void onConsumed(final World world, final EntityPlayer player) {
		player.getFoodStats().addStats(2, 2F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 2;
	}
}
