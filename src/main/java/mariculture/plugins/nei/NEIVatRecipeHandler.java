package mariculture.plugins.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.api.util.Text;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.ItemHelper;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIVatRecipeHandler extends NEIBase {
    public class CachedVatRecipe extends CachedRecipe {
        RecipeVat recipe;
        PositionedStack input;
        PositionedStack output;

        public CachedVatRecipe(RecipeVat recipe, ItemStack ingredient) {
            if (recipe.inputItem != null) {
                if (ingredient != null) input = new PositionedStack(ingredient.copy(), 28, 17);
                else input = new PositionedStack(recipe.inputItem.copy(), 28, 17);
            }

            if (recipe.outputItem != null) {
                output = new PositionedStack(recipe.outputItem.copy(), 121, 17);
            }
            this.recipe = recipe;
        }

        public CachedVatRecipe(RecipeVat vat) {
            this(vat, null);
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

        @Override
        public PositionedStack getIngredient() {
            return input;
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        boolean isSecondSearch = isSecondSearch(outputId, results);
        if ((outputId.equals("vat") || isSecondSearch) && getClass() == NEIVatRecipeHandler.class) {
            for (RecipeVat vat : MaricultureHandlers.vat.getRecipes()) {
                FluidStack fluid = vat.outputFluid;
                if (!isSecondSearch || isSecondSearch && fluid != null && fluid.getFluid() != null && fluid.getFluid().getName().equals(results[1])) {
                    arecipes.add(new CachedVatRecipe(vat));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public boolean isEqual(FluidStack fluid1, FluidStack fluid2, String fluid) {
        if (fluid1 != null && fluid1.getFluid() != null && fluid1.getFluid().getName().equals(fluid)) return true;
        return fluid2 != null && fluid2.getFluid() != null && fluid2.getFluid().getName().equals(fluid);
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        boolean isSecondSearch = isSecondSearch(inputId, ingredients);
        if ((inputId.equals("vat") || isSecondSearch) && getClass() == NEIVatRecipeHandler.class) {
            for (RecipeVat vat : MaricultureHandlers.vat.getRecipes())
                if (!isSecondSearch || isSecondSearch && isEqual(vat.inputFluid1, vat.inputFluid2, (String) ingredients[1])) {
                    arecipes.add(new CachedVatRecipe(vat));
                }
        } else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeVat vat : MaricultureHandlers.vat.getRecipes()) {
            if (vat.outputItem != null) {
                ItemStack item = vat.outputItem;
                if (OreDicHelper.convert(result).equals(OreDicHelper.convert(item))) {
                    arecipes.add(new CachedVatRecipe(vat));
                }
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
            if (fluid == null || fluid.getFluid() == null || vat.outputFluid == null) {
                continue;
            }

            if (fluid.getFluid().getName().equals(vat.outputFluid.getFluid().getName())) {
                arecipes.add(new CachedVatRecipe(vat));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (RecipeVat vat : MaricultureHandlers.vat.getRecipes()) {
            if (vat.inputItem != null && ItemHelper.areEqual(ingredient, vat.inputItem)) {
                arecipes.add(new CachedVatRecipe(vat, ingredient));
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(ingredient);
            if (fluid == null) {
                continue;
            }

            if (vat.inputFluid1 != null) if (fluid.getFluid().getName().equals(vat.inputFluid1.getFluid().getName())) {
                arecipes.add(new CachedVatRecipe(vat));
            }

            if (vat.inputFluid2 != null) if (fluid.getFluid().getName().equals(vat.inputFluid2.getFluid().getName())) {
                arecipes.add(new CachedVatRecipe(vat));
            }
        }
    }

    @Override
    public void drawExtras(int id) {
        CachedVatRecipe cache = (CachedVatRecipe) arecipes.get(id);
        RecipeVat recipe = cache.recipe;
        if (recipe.inputFluid1 != null) {
            drawFluidRect(13, 30, recipe.inputFluid1, TankSize.VAT);
            if (recipe.inputFluid2 == null) {
                drawFluidRect(36, 30, recipe.inputFluid1, TankSize.VAT);
            } else {
                drawFluidRect(36, 30, recipe.inputFluid2, TankSize.VAT);
            }
        }

        if (recipe.outputFluid != null) {
            drawFluidRect(106, 30, recipe.outputFluid, TankSize.VAT);
            drawFluidRect(129, 30, recipe.outputFluid, TankSize.VAT);
        }

        int time = recipe.processTime;
        int x = time < 10 ? 76 : 72;
        Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + "" + time + "s", x, 16, 0);
    }

    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
        currenttip = super.handleTooltip(gui, currenttip, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return currenttip;

        RecipeVat recipe = ((CachedVatRecipe) arecipes.get(id)).recipe;
        String fluid = null;
        String fluid2 = null;
        String fluid3 = null;

        if (recipe.inputFluid1 != null) {
            fluid = FluidHelper.getFluidName(recipe.inputFluid1.getFluid());
        }
        if (recipe.inputFluid2 != null) {
            fluid2 = FluidHelper.getFluidName(recipe.inputFluid2.getFluid());
        }
        if (recipe.outputFluid != null) {
            fluid3 = FluidHelper.getFluidName(recipe.outputFluid.getFluid());
        }
        int yLow = id % 2 == 0 ? 19 : 85;
        int yHigh = id % 2 == 0 ? 63 : 128;

        int xEnd = recipe.inputFluid2 == null ? 64 : 40;
        if (fluid != null) {
            if (mouse.x >= 16 && mouse.x <= xEnd && mouse.y >= yLow && mouse.y <= yHigh) {
                currenttip.add(fluid);
                FluidHelper.getFluidQty(currenttip, recipe.inputFluid1, -1);
            }

            if (fluid2 != null) if (mouse.x > xEnd && mouse.x <= 64 && mouse.y >= yLow && mouse.y <= yHigh) {
                currenttip.add(fluid2);
                FluidHelper.getFluidQty(currenttip, recipe.inputFluid2, -1);
            }
        }

        if (fluid3 != null) if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
            currenttip.add(fluid3);
            FluidHelper.getFluidQty(currenttip, recipe.outputFluid, -1);
        }

        return currenttip;
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int id) {
        super.mouseClicked(gui, button, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        RecipeVat recipe = ((CachedVatRecipe) arecipes.get(id)).recipe;

        int yLow = id % 2 == 0 ? 19 : 85;
        int yHigh = id % 2 == 0 ? 63 : 128;

        int xEnd = recipe.inputFluid2 == null ? 64 : 40;
        if (recipe.inputFluid1 != null) {
            if (mouse.x >= 16 && mouse.x <= xEnd && mouse.y >= yLow && mouse.y <= yHigh) {
                loadFluidsButton(button, recipe.inputFluid1);
            }

            FluidStack fluid = recipe.inputFluid2 == null ? recipe.inputFluid1 : recipe.inputFluid2;
            if (mouse.x > xEnd && mouse.x <= 64 && mouse.y >= yLow && mouse.y <= yHigh) {
                loadFluidsButton(button, fluid);
            }
        }

        if (recipe.outputFluid != null) if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsButton(button, recipe.outputFluid);
        }

        return false;
    }

    @Override
    public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int id) {
        super.keyTyped(gui, keyChar, keyCode, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        RecipeVat recipe = ((CachedVatRecipe) arecipes.get(id)).recipe;

        int yLow = id % 2 == 0 ? 19 : 85;
        int yHigh = id % 2 == 0 ? 63 : 128;

        int xEnd = recipe.inputFluid2 == null ? 64 : 40;
        if (recipe.inputFluid1 != null) {
            if (mouse.x >= 16 && mouse.x <= xEnd && mouse.y >= yLow && mouse.y <= yHigh) {
                loadFluidsKey(keyCode, recipe.inputFluid1);
            }

            FluidStack fluid = recipe.inputFluid2 == null ? recipe.inputFluid1 : recipe.inputFluid2;
            if (mouse.x > xEnd && mouse.x <= 64 && mouse.y >= yLow && mouse.y <= yHigh) {
                loadFluidsKey(keyCode, fluid);
            }
        }

        if (recipe.outputFluid != null) if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsKey(keyCode, recipe.outputFluid);
        }
        return false;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(72, 26, 22, 16), "vat"));
    }

    @Override
    public String getRecipeName() {
        return "Vat";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Mariculture.modid, "textures/gui/nei/vat.png").toString();
    }

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
    public String getOverlayIdentifier() {
        return "vat";
    }

    @Override
    public boolean isOverItem(GuiRecipe gui, int id) {
        CachedVatRecipe cache = (CachedVatRecipe) arecipes.get(id);
        return cache.input != null && gui.isMouseOver(cache.input, id) || cache.output != null && gui.isMouseOver(cache.output, id);
    }
}
