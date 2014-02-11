package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

public interface IBaitHandler {	
	public void addBait(ItemStack bait, Integer catchRate);
	public int getBaitQuality(ItemStack bait);
}
