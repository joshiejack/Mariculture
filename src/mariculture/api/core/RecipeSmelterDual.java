package mariculture.api.core;

import net.minecraft.item.ItemStack;

public class RecipeSmelterDual extends RecipeSmelter {
	public ItemStack secondary;
	public RecipeSmelterDual(ItemStack input, ItemStack input2, int temp, SmelterOutput output) {
		super(input, temp, output);
		secondary = input2;
	}
}
