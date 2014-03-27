package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.IRodQuality;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;

public class RodQualityHandler implements IRodQuality {
	private static ArrayList<FishingRod> canUseBait = new ArrayList<FishingRod>();

	static ArrayList<FishingRod> getCanUseList() {
		return canUseBait;
	}

	@Override
	public void addBaitForQuality(ItemStack bait, List<RodQuality> rods) {
		for (int i = 0; i < rods.size(); i++) {
			if (rods.get(i) instanceof RodQuality) {
				addBaitForQuality(bait, rods.get(i));
			}
		}
	}

	@Override
	public void removeBaitForQuality(ItemStack bait, List<RodQuality> rods) {
		for (int i = 0; i < rods.size(); i++) {
			if (rods.get(i) instanceof RodQuality) {
				removeBaitForQuality(bait, rods.get(i));
			}
		}
	}

	@Override
	public void addBaitForQuality(ItemStack bait, RodQuality quality) {
		canUseBait.add(new FishingRod(bait, quality));
	}

	@Override
	public void removeBaitForQuality(ItemStack bait, RodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) canUseBait.clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(bait, quality)) {
				canUseBait.remove(loot);
			}
		}
	}

	static class FishingRod {
		ItemStack itemStack;
		RodQuality enumQuality;

		private FishingRod(ItemStack item, RodQuality quality) {
			this.itemStack = item;
			this.enumQuality = quality;
		}

		private boolean equals(ItemStack item, RodQuality quality) {
			return (quality == enumQuality && item.isItemEqual(this.itemStack));
		}
	}

	@Override
	public boolean canUseBait(ItemStack stack, RodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) canUseBait.clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(stack, quality)) {
				return true;
			}
		}

		return false;
	}
}
