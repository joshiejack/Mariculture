package mariculture.fishery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mariculture.api.fishery.ISifterHandler;
import mariculture.api.fishery.RecipeSifter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SifterHandler implements ISifterHandler {
    private final HashMap<List<? extends Object>, ArrayList<RecipeSifter>> recipes = new HashMap();

    @Override
    public void addRecipe(RecipeSifter recipe) {
        ArrayList<ItemStack> blocks = new ArrayList();
        if (recipe.block == null) {
            blocks = OreDictionary.getOres(recipe.name);
        } else {
            blocks.add(recipe.block);
        }

        for (ItemStack block : blocks) {
            if (block == null || block.getItem() == null) {
                continue;
            }
            List<? extends Object> data = Arrays.asList(Item.itemRegistry.getNameForObject(block.getItem()), block.getItemDamage());
            ArrayList<RecipeSifter> list = recipes.containsKey(data) ? recipes.get(data) : new ArrayList();
            list.add(new RecipeSifter(recipe.bait, block, recipe.minCount, recipe.maxCount, recipe.chance));
            recipes.put(data, list);
        }
    }

    @Override
    public ArrayList<RecipeSifter> getResult(ItemStack stack) {
        ArrayList<RecipeSifter> ret = recipes.get(Arrays.asList(Item.itemRegistry.getNameForObject(stack.getItem()), stack.getItemDamage()));
        return ret == null ? recipes.get(Arrays.asList(Item.itemRegistry.getNameForObject(stack.getItem()), OreDictionary.WILDCARD_VALUE)) : ret;
    }

    @Override
    public HashMap<List<? extends Object>, ArrayList<RecipeSifter>> getRecipes() {
        return recipes;
    }
}
