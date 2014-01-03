package mariculture.api.core;

import net.minecraft.item.ItemStack;

public interface IOvenHandler {
	public void addRecipe(RecipeOven recipe);
	public RecipeOven getResult(ItemStack input);
}
