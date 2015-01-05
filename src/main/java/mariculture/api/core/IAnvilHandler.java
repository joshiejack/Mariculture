package mariculture.api.core;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public interface IAnvilHandler {
    public HashMap<String, RecipeAnvil> getRecipes();

    public void addRecipe(RecipeAnvil recipe);

    public ItemStack createWorkedItem(ItemStack stack, int hits);

    public class RecipeAnvil {
        public ItemStack input;
        public ItemStack output;
        public int hits;

        public RecipeAnvil(ItemStack input, ItemStack output, int hits) {
            this.input = input;
            this.output = output;
            this.hits = hits;
        }
    }
}
