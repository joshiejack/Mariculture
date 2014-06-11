package mariculture.plugins.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.RecipeCasting;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public abstract class NEICastingRecipeHandler extends NEIBase {
    public class CachedCasterRecipe extends CachedRecipe {
        FluidStack fluid;
        PositionedStack output;

        public CachedCasterRecipe(FluidStack fluid, ItemStack output) {
            this.fluid = fluid.copy();
            this.output = new PositionedStack(output.copy(), 119, 25);
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

        @Override
        public PositionedStack getIngredient() {
            return null;
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier()) && getClass() == thisClass()) {
            HashMap<String, RecipeCasting> recipes = getRecipes();
            for (Entry<String, RecipeCasting> recipe : recipes.entrySet()) {
                arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        boolean isSecondSearch = isSecondSearch(inputId, ingredients);
        if ((inputId.equals(getOverlayIdentifier()) || isSecondSearch) && getClass() == thisClass()) {
            HashMap<String, RecipeCasting> recipes = getRecipes();
            for (Entry<String, RecipeCasting> recipe : recipes.entrySet())
                if (!isSecondSearch || isSecondSearch && recipe.getValue().fluid.getFluid().getName().equals(ingredients[1])) {
                    arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
                }
        } else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        HashMap<String, RecipeCasting> recipes = getRecipes();
        for (Entry<String, RecipeCasting> recipe : recipes.entrySet())
            if (OreDicHelper.convert(result).equals(OreDicHelper.convert(recipe.getValue().output))) {
                arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
            }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        HashMap<String, RecipeCasting> recipes = getRecipes();
        for (Entry<String, RecipeCasting> recipe : recipes.entrySet()) {
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(ingredient);
            if (fluid == null || fluid.getFluid() == null) {
                continue;
            }
            if (fluid.getFluid().getName().equals(recipe.getValue().fluid)) {
                arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
            }
        }
    }

    @Override
    public void drawExtras(int id) {
        CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
        drawFluidRect(22, 15, cache.fluid, getTankType());
    }

    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
        currenttip = super.handleTooltip(gui, currenttip, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return currenttip;

        CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
        String fluid = FluidHelper.getFluidName(cache.fluid);

        int yLow = id % 2 == 0 ? 26 : 89;
        int yHigh = id % 2 == 0 ? 73 : 138;

        if (mouse.x >= 20 && mouse.x <= 68 && mouse.y >= yLow && mouse.y <= yHigh) {
            currenttip.add(fluid);
            FluidHelper.getFluidQty(currenttip, cache.fluid, -1);
        }

        return currenttip;
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int id) {
        super.mouseClicked(gui, button, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
        int yLow = id % 2 == 0 ? 26 : 89;
        int yHigh = id % 2 == 0 ? 73 : 138;

        if (mouse.x >= 20 && mouse.x <= 68 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsButton(button, cache.fluid);
        }

        return false;
    }

    @Override
    public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int id) {
        super.keyTyped(gui, keyChar, keyCode, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
        int yLow = id % 2 == 0 ? 26 : 89;
        int yHigh = id % 2 == 0 ? 73 : 138;

        if (mouse.x >= 20 && mouse.x <= 68 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsKey(keyCode, cache.fluid);
        }

        return false;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(77, 26, 22, 16), getOverlayIdentifier()));
    }

    @Override
    public abstract String getOverlayIdentifier();

    public abstract Class thisClass();

    public abstract HashMap<String, RecipeCasting> getRecipes();

    @Override
    public abstract String getRecipeName();

    @Override
    public abstract String getGuiTexture();

    public abstract TankSize getTankType();

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 15, 166, 73);
        drawExtras(recipe);
    }

    @Override
    public void drawForeground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GuiDraw.changeTexture(getGuiTexture());
    }

    @Override
    public boolean isOverItem(GuiRecipe gui, int id) {
        CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
        return cache.output != null && gui.isMouseOver(cache.output, id);
    }
}
