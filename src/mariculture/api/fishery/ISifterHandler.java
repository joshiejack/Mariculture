package mariculture.api.fishery;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface ISifterHandler {
	public void addRecipe(RecipeSifter recipe);
	public ArrayList<RecipeSifter> getResult(ItemStack stack);
}
