package mariculture.fishery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.IBaitHandler;
import mariculture.core.helpers.OreDicHelper;
import mariculture.fishery.FishingRodHandler.FishingRod;
import net.minecraft.item.ItemStack;

public class BaitHandler implements IBaitHandler {	
	private final HashMap<String, Integer> baits = new HashMap();

	@Override
	public void addBaitType(ItemStack bait, Integer catchRate) {
		baits.put(OreDicHelper.convert(bait), catchRate);
	}

	@Override
	public int getBaitQuality(ItemStack bait) {
		if(baits.containsKey(OreDicHelper.convert(bait)))
			return baits.get(OreDicHelper.convert(bait));
		return 0;
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
		FishingRodHandler.canUseBait.add(new FishingRod(bait, quality));
	}

	@Override
	public void removeBaitForQuality(ItemStack bait, EnumRodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) FishingRodHandler.canUseBait.clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(bait, quality)) {
				FishingRodHandler.canUseBait.remove(loot);
			}
		}
	}
}