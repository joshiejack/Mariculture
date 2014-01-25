package mariculture.api.fishery.fish;

import net.minecraft.item.ItemStack;

public class FishProduct {
	public ItemStack product;
	public double chance;
	public FishProduct(ItemStack product, double chance) {
		this.product = product;
		this.chance = chance;
	}
}