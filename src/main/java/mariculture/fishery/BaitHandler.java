package mariculture.fishery;

import java.util.HashMap;

import mariculture.api.fishery.IBaitHandler;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.item.ItemStack;

public class BaitHandler implements IBaitHandler {	
	private final HashMap<String, Integer> baits = new HashMap();

	@Override
	public void addBait(ItemStack bait, Integer catchRate) {
		baits.put(OreDicHelper.convert(bait), catchRate);
	}

	@Override
	public int getBaitQuality(ItemStack bait) {
		if(baits.containsKey(OreDicHelper.convert(bait)))
			return baits.get(OreDicHelper.convert(bait));
		return 0;
	}
}