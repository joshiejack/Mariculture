package mariculture.plugins.nei;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.util.Text;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.util.Rand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class NEICrucibleRecipeHandler extends NEIBase {
    public class CachedLiquifierRecipe extends CachedRecipe {
        RecipeSmelter recipe;
        PositionedStack input1;
        PositionedStack output;

        public CachedLiquifierRecipe(RecipeSmelter recipe) {
            input1 = new PositionedStack(recipe.input.copy(), 24, 6);
            if (recipe.output != null) {
                output = new PositionedStack(recipe.output.copy(), 140, 25);
            }
            this.recipe = recipe;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(input1));
        }

        @Override
        public PositionedStack getOtherStack() {
            if (checkAndFixFuels()) return afuels.get(cycleticks / 64 % afuels.size()).stack;
            else return new PositionedStack(new ItemStack(Items.coal, 1, 0), 33, 44, false);
        }
    }

    public static class LiquifierFuel {
        public PositionedStack stack;
        public FuelInfo info;

        public LiquifierFuel(ItemStack ingred, FuelInfo info) {
            stack = new PositionedStack(ingred, 33, 44, false);
            this.info = info;
        }
    }

    public static ArrayList<LiquifierFuel> afuels;

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return mariculture.core.gui.GuiLiquifier.class;
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        checkAndFixFuels();
        return super.newInstance();
    }

    private boolean checkAndFixFuels() {
        if (afuels == null || afuels.size() < 1) return findFuels();
        else return true;
    }

    private static boolean findFuels() {
        afuels = new ArrayList<LiquifierFuel>();
        for (ItemStack item : ItemList.items) {
            FuelInfo info = MaricultureHandlers.crucible.getFuelInfo(item);
            if (info != null) {
                afuels.add(new LiquifierFuel(item.copy(), info));
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
            if (fluid == null || fluid.getFluid() == null) {
                continue;
            }

            info = MaricultureHandlers.crucible.getFuelInfo(fluid);
            if (info != null) {
                afuels.add(new LiquifierFuel(item.copy(), info));
            }
        }

        return afuels != null && afuels.size() > 0;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        boolean isSecondSearch = isSecondSearch(outputId, results);
        if ((outputId.equals("liquifier") || isSecondSearch) && getClass() == NEICrucibleRecipeHandler.class) {
            for (RecipeSmelter recipe : MaricultureHandlers.crucible.getRecipes())
                if (!isSecondSearch || isSecondSearch && recipe.fluid.getFluid().getName().equals(results[1])) {
                    arecipes.add(new CachedLiquifierRecipe(recipe));
                }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeSmelter recipe : MaricultureHandlers.crucible.getRecipes()) {
            ItemStack item = recipe.output;
            if (NEIServerUtils.areStacksSameTypeCrafting(result, item)) {
                arecipes.add(new CachedLiquifierRecipe(recipe));
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
            if (fluid == null || fluid.getFluid() == null || recipe.fluid == null) {
                continue;
            }

            if (fluid.getFluid().getName().equals(recipe.fluid.getFluid().getName())) {
                arecipes.add(new CachedLiquifierRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (RecipeSmelter recipe : MaricultureHandlers.crucible.getRecipes()) {
            ItemStack item = recipe.input;
            if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, item)) {
                arecipes.add(new CachedLiquifierRecipe(recipe));
            }
        }
    }

    public int aFluid = -1;

    @Override
    public void drawExtras(int id) {
        CachedLiquifierRecipe recipe = (CachedLiquifierRecipe) arecipes.get(id);
        int temp = recipe.recipe.temp * 60 / 2000;
        GuiDraw.drawTexturedModalRect(7, 0 + 3 + 60 - temp, 251, 60 - temp, 5, temp);

        if (recipe.recipe.fluid != null) if (recipe.recipe.random != null) {
            if (cycleticks % 64 == 0 || aFluid == -1) {
                aFluid = Rand.rand.nextInt(recipe.recipe.random.length);
            }

            if (aFluid >= recipe.recipe.random.length) {
                aFluid = 0;
            }
            FluidStack fluid = recipe.recipe.random[aFluid];
            drawFluidRect(93, 46, fluid, TankSize.DOUBLE);
        } else {
            drawFluidRect(93, 46, recipe.recipe.fluid, TankSize.DOUBLE);
        }

        if (recipe.recipe.output != null) {
            int chance = (int) ((float) 1 / recipe.recipe.chance * 100);
            int x = chance < 10 ? 143 : 139;
            Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + "" + chance + "%", x, 44, 0);
        }
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int id) {
        if (stack != null) {
            CachedLiquifierRecipe cache = (CachedLiquifierRecipe) arecipes.get(id);
            RecipeSmelter recipe = cache.recipe;
            if (cache.output != null) {
                if (gui.isMouseOver(cache.output, id)) {
                    if (recipe.output != null && ItemHelper.areItemStacksEqualNoNBT(stack, recipe.output)) {
                        int chance = (int) ((float) 1 / recipe.chance * 100);
                        currenttip.add(Text.GREY + chance + StatCollector.translateToLocal("mariculture.string.percent") + stack.getDisplayName());
                    }
                }
            }

            if (cache.recipe.input != null) {
                if (gui.isMouseOver(cache.input1, id)) {
                    currenttip.add(Text.ORANGE + StatCollector.translateToLocal("mariculture.string.melting") + ": " + cache.recipe.temp + "\u00B0" + "C");
                }
            }

            if (id % 2 == 0) {
                FuelInfo info = MaricultureHandlers.crucible.getFuelInfo(stack);
                if (FluidContainerRegistry.isFilledContainer(stack)) {
                    FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
                    info = MaricultureHandlers.crucible.getFuelInfo(fluid);
                }

                if (info != null) {
                    if (FluidContainerRegistry.isFilledContainer(stack)) {
                        currenttip.add(Text.DARK_AQUA + StatCollector.translateToLocal("mariculture.string.asFluid"));
                        currenttip.add(Text.WHITE + StatCollector.translateToLocal("mariculture.string.perTempFluid") + ": " + info.maxTempPer + "\u00B0" + "C");
                    } else {
                        currenttip.add(Text.DARK_GREEN + StatCollector.translateToLocal("mariculture.string.asSolid"));
                        currenttip.add(Text.WHITE + StatCollector.translateToLocal("mariculture.string.perTempSolid") + ": " + info.maxTempPer + "\u00B0" + "C");
                    }

                    currenttip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.maxTemp") + ": " + info.maxTemp + "\u00B0" + "C");
                }
            }
        }

        return currenttip;
    }

    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
        currenttip = super.handleTooltip(gui, currenttip, id);

        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return currenttip;

        RecipeSmelter recipe = ((CachedLiquifierRecipe) arecipes.get(id)).recipe;
        int yLow = id % 2 == 0 ? 19 : 84;
        int yHigh = id % 2 == 0 ? 79 : 144;

        String fluid = null;
        if (recipe.fluid != null) if (recipe.fluid.amount > 0) if (recipe.rands != null && aFluid < recipe.random.length) {
            FluidStack someFluid = recipe.random[aFluid];
            fluid = FluidHelper.getFluidName(someFluid);
        } else {
            fluid = FluidHelper.getFluidName(recipe.fluid.getFluid());
        }

        if (mouse.x >= 97 && mouse.x <= 132 && mouse.y >= yLow && mouse.y <= yHigh) {
            currenttip.add(fluid);
            if (fluid != null) if (recipe.rands != null && aFluid < recipe.random.length) {
                FluidHelper.getFluidQty(currenttip, recipe.random[aFluid], -1);
            } else {
                FluidHelper.getFluidQty(currenttip, recipe.fluid, -1);
            }
        }

        if (mouse.x >= 12 && mouse.x <= 16 && mouse.y >= yLow && mouse.y <= yHigh) {
            currenttip.add(recipe.temp + "\u00B0" + "C");
        }

        return currenttip;
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int id) {
        super.mouseClicked(gui, button, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        RecipeSmelter recipe = ((CachedLiquifierRecipe) arecipes.get(id)).recipe;
        int yLow = id % 2 == 0 ? 19 : 84;
        int yHigh = id % 2 == 0 ? 79 : 144;

        if (recipe.fluid != null) if (mouse.x >= 97 && mouse.x <= 132 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsButton(button, recipe.fluid);
        }

        return false;
    }

    @Override
    public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int id) {
        super.keyTyped(gui, keyChar, keyCode, id);
        Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
        if (isOverItem(gui, id)) return false;

        RecipeSmelter recipe = ((CachedLiquifierRecipe) arecipes.get(id)).recipe;
        int yLow = id % 2 == 0 ? 19 : 84;
        int yHigh = id % 2 == 0 ? 79 : 144;

        if (recipe.fluid != null) if (mouse.x >= 97 && mouse.x <= 132 && mouse.y >= yLow && mouse.y <= yHigh) {
            loadFluidsKey(keyCode, recipe.fluid);
        }

        return false;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(60, 27, 22, 16), "liquifier"));
    }

    @Override
    public String getRecipeName() {
        return "Crucible Furnace";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Mariculture.modid, "textures/gui/nei/liquifier.png").toString();
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 15, 166, 73);
    }

    @Override
    public String getOverlayIdentifier() {
        return "liquifier";
    }

    @Override
    public boolean isOverItem(GuiRecipe gui, int id) {
        return false;
    }
}
