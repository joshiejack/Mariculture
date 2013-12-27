package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishingRod;
import mariculture.fishery.RodQualityHandler.FishingRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RodRightClickHandler implements IFishingRod {
	@Override
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player, EnumRodQuality quality, Random itemRand) {
		int baitQuality = 0;

		if (player.fishEntity != null) {
			int i = player.fishEntity.catchFish();
			stack.damageItem(i, player);
			player.swingItem();
		} else {
			int currentSlot = player.inventory.currentItem;
			int foundSlot = -1;

			if (currentSlot > 0) {
				int leftSlot = currentSlot - 1;

				if (player.inventory.getStackInSlot(leftSlot) != null) {
					if (Fishing.quality.canUseBait(player.inventory.getStackInSlot(leftSlot), quality)) {
						baitQuality = Fishing.bait.getEffectiveness(player.inventory.getStackInSlot(leftSlot)) + 1;
						foundSlot = leftSlot;
					}
				}
			}

			if (foundSlot == -1 && currentSlot < 8) {
				int rightSlot = currentSlot + 1;

				if (player.inventory.getStackInSlot(rightSlot) != null) {
					if (Fishing.quality.canUseBait(player.inventory.getStackInSlot(rightSlot), quality)) {
						baitQuality = Fishing.bait.getEffectiveness(player.inventory.getStackInSlot(rightSlot)) + 1;
						foundSlot = rightSlot;
					}
				}
			}
			if (foundSlot != -1 && baitQuality > 0) {
				world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

				if (!world.isRemote) {
					world.spawnEntityInWorld(new EntityFishing(world, player, baitQuality));
				}
				
				if (!player.capabilities.isCreativeMode) {
					player.inventory.decrStackSize(foundSlot, 1);
				}

				player.swingItem();
			}
		}

		return stack;
	}

	@Override
	public void addBaitList(List list, EnumRodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) RodQualityHandler.getCanUseList().clone();
		for (FishingRod loot : lootTmp) {
			if (loot.enumQuality == quality) {
				list.add(loot.itemStack.getItem().getItemDisplayName(loot.itemStack));
			}
		}
	}
}
