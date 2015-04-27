package mariculture.plugins.minetweaker;

import java.util.HashMap;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.api.core.RecipeCasting.RecipeNuggetCasting;
import mariculture.plugins.PluginMineTweaker3;
import mariculture.plugins.minetweaker.util.MapAddAction;
import mariculture.plugins.minetweaker.util.MapRemoveAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Casting")
public class Casting {
    @ZenMethod
    public static void addNuggetRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.apply(new Add(new RecipeNuggetCasting(MineTweakerMC.getLiquidStack(input), MineTweakerMC.getItemStack(output)), MaricultureHandlers.casting.getNuggetRecipes()));
    }

    @ZenMethod
    public static void addIngotRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.apply(new Add(new RecipeNuggetCasting(MineTweakerMC.getLiquidStack(input), MineTweakerMC.getItemStack(output)), MaricultureHandlers.casting.getIngotRecipes()));
    }

    @ZenMethod
    public static void addBlockRecipe(ILiquidStack input, IItemStack output) {
        MineTweakerAPI.apply(new Add(new RecipeNuggetCasting(MineTweakerMC.getLiquidStack(input), MineTweakerMC.getItemStack(output)), MaricultureHandlers.casting.getBlockRecipes()));
    }

    private static class Add extends MapAddAction {
        public Add(RecipeCasting recipe, HashMap map) {
            super(map, PluginMineTweaker3.getKey(recipe.fluid), recipe);
        }

        @Override
        public String describe() {
            RecipeCasting recipe = (RecipeCasting) value;
            return "Adding " + recipe.output.getDisplayName() + " as a cooling from " + recipe.fluid.getLocalizedName() + " with Mariculture Casting";
        }

        @Override
        public String describeUndo() {
            RecipeCasting recipe = (RecipeCasting) value;
            return "Removing " + recipe.output.getDisplayName() + " as a cooling from " + recipe.fluid.getLocalizedName() + " with Mariculture Casting";
        }
    }

    @ZenMethod
    public static void removeNuggetRecipe(IItemStack input) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(input), MaricultureHandlers.casting.getNuggetRecipes()));
    }

    @ZenMethod
    public static void removeIngotRecipe(IItemStack input) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(input), MaricultureHandlers.casting.getIngotRecipes()));
    }

    @ZenMethod
    public static void removeBlockRecipe(IItemStack input) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(input), MaricultureHandlers.casting.getBlockRecipes()));
    }

    private static class Remove extends MapRemoveAction {
        private final ItemStack stack;
        public Remove(ItemStack stack, HashMap map) {
            super(map, PluginMineTweaker3.getKey(stack));
            this.stack = stack;
        }

        @Override
        public String describe() {
            return "Removing " + stack.getDisplayName() + " from Mariculture Casting Recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + stack.getDisplayName() + " to Mariculture Casting Recipes";
        }
    }
}
