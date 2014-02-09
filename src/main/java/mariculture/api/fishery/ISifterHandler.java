package mariculture.api.fishery;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;

public interface ISifterHandler {
	public void addRecipe(RecipeSifter recipe);
	public ArrayList<RecipeSifter> getResult(ItemStack stack);
	public HashMap<String, ArrayList<RecipeSifter>> getRecipes();
}
