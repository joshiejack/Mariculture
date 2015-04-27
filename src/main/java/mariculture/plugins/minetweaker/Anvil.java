package mariculture.plugins.minetweaker;

import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.MaricultureHandlers;
import mariculture.plugins.PluginMineTweaker3;
import mariculture.plugins.minetweaker.util.MapAddAction;
import mariculture.plugins.minetweaker.util.MapRemoveAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Anvil")
public class Anvil {
    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack output, int hits) {
        MineTweakerAPI.apply(new Add(new RecipeAnvil(MineTweakerMC.getItemStack(input), MineTweakerMC.getItemStack(output), hits)));
    }

    private static class Add extends MapAddAction {
        public Add(RecipeAnvil recipe) {
            super(MaricultureHandlers.anvil.getRecipes(), PluginMineTweaker3.getKey(recipe.input), recipe);
        }

        @Override
        public String describe() {
            RecipeAnvil recipe = (RecipeAnvil) value;
            return "Adding " + recipe.input.getDisplayName() + " to the anvil to make " + recipe.output.getDisplayName() + " taking " + recipe.hits + " hits";
        }

        @Override
        public String describeUndo() {
            RecipeAnvil recipe = (RecipeAnvil) value;
            return "Removing " + recipe.input.getDisplayName() + " from the anvil to make " + recipe.output.getDisplayName() + " taking " + recipe.hits + " hits";
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(input)));
    }

    private static class Remove extends MapRemoveAction {
        private final ItemStack stack;
        public Remove(ItemStack stack) {
            super(MaricultureHandlers.anvil.getRecipes(), PluginMineTweaker3.getKey(stack));
            this.stack = stack;
        }

        @Override
        public String describe() {
            return "Removing " + stack.getDisplayName() + " as an input for the Blacksmith's Anvil";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + stack.getDisplayName() + " as an input for the Blacksmith's Anvil";
        }
    }
}
