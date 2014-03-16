package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

public class RecipeSifter {
	public ItemStack bait;
	public ItemStack block;
	public int minCount, maxCount, chance;
	
	public RecipeSifter(ItemStack bait, ItemStack block, int min, int max, int chance) {
		this.bait = bait;
		this.block = block;
		this.minCount = min;
		this.maxCount = max;
		this.chance = chance;
	}
}
