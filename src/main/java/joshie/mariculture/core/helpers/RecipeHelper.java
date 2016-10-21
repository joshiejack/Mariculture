package joshie.mariculture.core.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeHelper {
    public static void addShaped(ItemStack result, Object... input) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, changeValues(input)));
    }

    public static void addSmelting(ItemStack output, ItemStack input, float xp) {
        FurnaceRecipes.instance().addSmeltingRecipe(input, output, xp);
    }

    public static void add4x4Recipe(ItemStack result, ItemStack input) {
        addShaped(result, "##", "##", '#', input);
    }

    public static void addStairRecipe(ItemStack result, ItemStack input) {
        addShaped(result, "#  ", "## ", "###", '#', input);
    }

    public static void addSlabRecipe(ItemStack result, ItemStack input) {
        addShaped(result, "###", '#', input);
    }

    private static Object[] changeValues(Object... original) {
        Object[] input = new Object[original.length];
        for (int i = 0; i < input.length; i++) {
            input[i] = changeValue(original[i]);
        }

        return input;
    }

    private static Object changeValue(Object object) {
        if (object instanceof String) {
            String string = (String) object;
            if (string.equals("ingotCopper") && !OreDictionary.doesOreNameExist("ingotCopper")) return "ingotIron";
            if (string.equals("ingotLead") && !OreDictionary.doesOreNameExist("ingotLead")) return "obsidian";
        }

        return object;
    }
}
