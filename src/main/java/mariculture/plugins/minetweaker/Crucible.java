package mariculture.plugins.minetweaker;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.handlers.CrucibleHandler;
import mariculture.plugins.PluginMineTweaker3;
import mariculture.plugins.minetweaker.util.CollectionAddAction;
import mariculture.plugins.minetweaker.util.CollectionRemoveAction;
import mariculture.plugins.minetweaker.util.MapAddAction;
import mariculture.plugins.minetweaker.util.MapRemoveAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Crucible")
public class Crucible {
    @ZenMethod
    public static void addRecipe(int temp, IItemStack input, ILiquidStack fluid, @Optional IItemStack output, @Optional int chance) {
        ItemStack out = output != null ? MineTweakerMC.getItemStack(output) : null;
        MineTweakerAPI.apply(new AddRecipe(new RecipeSmelter(MineTweakerMC.getItemStack(input), null, temp, MineTweakerMC.getLiquidStack(fluid), out, chance)));
    }

    private static class AddRecipe extends CollectionAddAction {
        public AddRecipe(RecipeSmelter recipe) {
            super("Mariculture Crucible", MaricultureHandlers.crucible.getRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            return ((RecipeSmelter) recipe).input.getDisplayName();
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack input) {
        MineTweakerAPI.apply(new RemoveRecipe(MineTweakerMC.getItemStack(input)));
    }

    private static class RemoveRecipe extends CollectionRemoveAction {
        public RemoveRecipe(ItemStack stack) {
            super("Mariculture Crucible", MaricultureHandlers.crucible.getRecipes(), stack);
        }

        @Override
        public String getRecipeInfo() {
            return stack.getDisplayName();
        }

        @Override
        public boolean matches(Object object) {
            RecipeSmelter recipe = (RecipeSmelter) object;
            return recipe.input != null && stack != null && recipe.input.isItemEqual(stack);
        }
    }

    @ZenMethod
    public static void addFuel(IItemStack input, int max, int per, int time) {
        MineTweakerAPI.apply(new AddFuel(MineTweakerMC.getItemStack(input), new FuelInfo(max, per, time)));
    }

    @ZenMethod
    public static void addFuel(ILiquidStack input, int max, int per, int time) {
        MineTweakerAPI.apply(new AddFuel(MineTweakerMC.getLiquidStack(input), new FuelInfo(max, per, time)));
    }

    @ZenMethod
    public static void addFuel(String input, int max, int per, int time) {
        MineTweakerAPI.apply(new AddFuel(input, new FuelInfo(max, per, time)));
    }

    private static class AddFuel extends MapAddAction {
        private final String name;

        public AddFuel(Object input, FuelInfo info) {
            super(CrucibleHandler.fuels, PluginMineTweaker3.getKey(input), info);
            if (input instanceof ItemStack) {
                name = ((ItemStack)input).getDisplayName();
            } else if (input instanceof FluidStack) {
                name = ((FluidStack)input).getLocalizedName();
            } else if (input instanceof String) {
                name = (String) input;
            } else name = "null";
        }

        @Override
        public String describe() {
            return "Adding " + name + " as a Crucible Furnace fuel";
        }

        @Override
        public String describeUndo() {
            return "Removing " + name + " as a Crucible Furnace fuel";
        }
    }
    
    @ZenMethod
    public static void removeFuel(IItemStack fuel) {
        MineTweakerAPI.apply(new RemoveFuel(fuel));
    }

    @ZenMethod
    public static void removeFuel(ILiquidStack fuel) {
        MineTweakerAPI.apply(new RemoveFuel(fuel));
    }

    @ZenMethod
    public static void removeFuel(String fuel) {
        MineTweakerAPI.apply(new RemoveFuel(fuel));
    }
   
    private static class RemoveFuel extends MapRemoveAction {
        private final String name;

        public RemoveFuel(Object input) {
            super(CrucibleHandler.fuels, PluginMineTweaker3.getKey(input));
            if (input instanceof ItemStack) {
                name = ((ItemStack)input).getDisplayName();
            } else if (input instanceof FluidStack) {
                name = ((FluidStack)input).getLocalizedName();
            } else if (input instanceof String) {
                name = (String) input;
            } else {
                name = "null";
            }
        }

        @Override
        public String describe() {
            return "Removing " + name + " as a Crucible Furnace fuel";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + name + " as a Crucible Furnace fuel";
        }
    }
}
