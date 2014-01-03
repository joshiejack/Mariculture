package mariculture.api.core;

import net.minecraft.item.ItemStack;

public class RecipeOven {
	public ItemStack input;
	public ItemStack output;
	
	public RecipeOven(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
}
