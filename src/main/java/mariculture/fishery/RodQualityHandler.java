package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.IRodQuality;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class RodQualityHandler implements IRodQuality {
	private static ArrayList<FishingRod> canUseBait = new ArrayList<FishingRod>();

	static ArrayList<FishingRod> getCanUseList() {
		return canUseBait;
	}

	@Override
	public EnumRodQuality addRodQuality(String name, int maxUses) {
		return EnumHelper.addEnum(EnumRodQuality.class, name, maxUses);
	}

	@Override
	public void addBaitForQuality(ItemStack bait, List<EnumRodQuality> rods) {
		for (int i = 0; i < rods.size(); i++) {
			if (rods.get(i) instanceof EnumRodQuality) {
				addBaitForQuality(bait, rods.get(i));
			}
		}
	}

	@Override
	public void removeBaitForQuality(ItemStack bait, List<EnumRodQuality> rods) {
		for (int i = 0; i < rods.size(); i++) {
			if (rods.get(i) instanceof EnumRodQuality) {
				removeBaitForQuality(bait, rods.get(i));
			}
		}
	}

	@Override
	public void addBaitForQuality(ItemStack bait, EnumRodQuality quality) {
		canUseBait.add(new FishingRod(bait, quality));
	}

	@Override
	public void removeBaitForQuality(ItemStack bait, EnumRodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) canUseBait.clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(bait, quality)) {
				canUseBait.remove(loot);
			}
		}
	}

	static class FishingRod {
		ItemStack itemStack;
		EnumRodQuality enumQuality;

		private FishingRod(ItemStack item, EnumRodQuality quality) {
			this.itemStack = item;
			this.enumQuality = quality;
		}

		private boolean equals(ItemStack item, EnumRodQuality quality) {
			return (quality == enumQuality && item.isItemEqual(this.itemStack));
		}
	}

	@Override
	public boolean canUseBait(ItemStack stack, EnumRodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) canUseBait.clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(stack, quality)) {
				return true;
			}
		}

		return false;
	}
}
