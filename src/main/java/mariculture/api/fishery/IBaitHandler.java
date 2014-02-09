package mariculture.api.fishery;

import java.util.Random;

import net.minecraft.item.ItemStack;

public interface IBaitHandler {	
	public void addBait(ItemStack bait, Integer catchRate);
	public int getBaitQuality(ItemStack bait);
}
