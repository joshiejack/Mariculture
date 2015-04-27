package mariculture.plugins.minetweaker;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.plugins.minetweaker.util.CollectionAddAction;
import mariculture.plugins.minetweaker.util.CollectionRemoveAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Vat")
public class Vat {
    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, fluid2, null, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, null, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, null, outputFluid, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, null, input, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, IItemStack outputItem, int time) {
        addRecipe(fluid1, null, input, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        addRecipe(fluid1, null, input, outputFluid, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, fluid2, input, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, input, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        MineTweakerAPI.apply(new Add(new RecipeVat(MineTweakerMC.getItemStack(input), MineTweakerMC.getLiquidStack(fluid1), MineTweakerMC.getLiquidStack(fluid2), MineTweakerMC.getLiquidStack(outputFluid), MineTweakerMC.getItemStack(outputItem), time)));
    }

    // Passes the list to the base list implementation, and adds the recipe
    private static class Add extends CollectionAddAction {
        public Add(RecipeVat recipe) {
            super("Mariculture Vat", MaricultureHandlers.vat.getRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            if (((RecipeVat) recipe).outputItem != null) return ((RecipeVat) recipe).outputItem.getDisplayName();
            else return ((RecipeVat) recipe).outputFluid.getFluid().getLocalizedName(((RecipeVat) recipe).outputFluid);
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack outputItem) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(outputItem), null));
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack outputFluid) {
        MineTweakerAPI.apply(new Remove(null, MineTweakerMC.getLiquidStack(outputFluid)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack outputItem, ILiquidStack outputFluid) {
        MineTweakerAPI.apply(new Remove(MineTweakerMC.getItemStack(outputItem), MineTweakerMC.getLiquidStack(outputFluid)));
    }

    private static class Remove extends CollectionRemoveAction {
        private FluidStack fluid = null;

        public Remove(ItemStack stack, FluidStack fluid) {
            super("Mariculture Vat", MaricultureHandlers.vat.getRecipes(), stack);
            if (fluid != null) this.fluid = fluid;
        }

        @Override
        public String getRecipeInfo() {
            return stack != null ? stack.getDisplayName() : fluid.getFluid().getLocalizedName(fluid);
        }

        @Override
        public boolean matches(Object object) {
            RecipeVat recipe = (RecipeVat) object;
            if (recipe.outputItem != null && stack != null && stack.isItemEqual(recipe.outputItem)) {
                return recipe.outputFluid == null || (fluid != null && recipe.outputFluid.isFluidEqual(fluid));
            }

            return (recipe.outputFluid != null && fluid != null && recipe.outputFluid.isFluidEqual(fluid));
        }
    }
}
