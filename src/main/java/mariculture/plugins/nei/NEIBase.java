package mariculture.plugins.nei;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;

import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;

public abstract class NEIBase extends TemplateRecipeHandler {
    protected void drawFluidRect(int j, int k, FluidStack fluid, TankSize size) {
        if (fluid == null || fluid.getFluid() == null) return;
        int scale = fluid.amount * 58 / 5000;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        if (size.equals(TankSize.VAT)) {
            drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 16, k, fluid.getFluid().getIcon(), 7, 16);
            drawScaledTexturedModelRectFromIcon(j, k - 16, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 16, k - 16, fluid.getFluid().getIcon(), 7, 16);
        }

        if (size.equals(TankSize.CASTER)) {
            drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 21, k, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 21, k + 21, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j, k + 21, fluid.getFluid().getIcon(), 16, 16);
        }

        if (size.equals(TankSize.BLOCK_CASTER)) {
            drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 12, 12);
            drawScaledTexturedModelRectFromIcon(j + 12, k, fluid.getFluid().getIcon(), 13, 12);
            drawScaledTexturedModelRectFromIcon(j + 25, k, fluid.getFluid().getIcon(), 12, 12);

            drawScaledTexturedModelRectFromIcon(j + 25, k + 12, fluid.getFluid().getIcon(), 12, 13);
            drawScaledTexturedModelRectFromIcon(j + 12, k + 12, fluid.getFluid().getIcon(), 13, 13);
            drawScaledTexturedModelRectFromIcon(j, k + 12, fluid.getFluid().getIcon(), 12, 13);

            drawScaledTexturedModelRectFromIcon(j + 25, k + 25, fluid.getFluid().getIcon(), 12, 12);
            drawScaledTexturedModelRectFromIcon(j + 12, k + 25, fluid.getFluid().getIcon(), 13, 12);
            drawScaledTexturedModelRectFromIcon(j, k + 25, fluid.getFluid().getIcon(), 12, 12);
        }

        if (size.equals(TankSize.NUGGET_CASTER)) {
            drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j, k + 10, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j, k + 21, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j, k + 31, fluid.getFluid().getIcon(), 6, 6);

            drawScaledTexturedModelRectFromIcon(j + 10, k, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 10, k + 10, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 10, k + 21, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 10, k + 31, fluid.getFluid().getIcon(), 6, 6);

            drawScaledTexturedModelRectFromIcon(j + 21, k, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 21, k + 10, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 21, k + 21, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 21, k + 31, fluid.getFluid().getIcon(), 6, 6);

            drawScaledTexturedModelRectFromIcon(j + 31, k, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 31, k + 10, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 31, k + 21, fluid.getFluid().getIcon(), 6, 6);
            drawScaledTexturedModelRectFromIcon(j + 31, k + 31, fluid.getFluid().getIcon(), 6, 6);
        }

        if (size.equals(TankSize.DOUBLE)) {
            drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 16, k, fluid.getFluid().getIcon(), 16, 16);
            drawScaledTexturedModelRectFromIcon(j + 32, k, fluid.getFluid().getIcon(), 2, 16);
            Minecraft.getMinecraft().renderEngine.bindTexture(Feature.texture);
            GuiDraw.drawTexturedModalRect(j, k - 42, 16, 0, 34, 60);
        }

        GuiDraw.changeTexture(getGuiTexture());
    }

    public int getGuiWidth(GuiRecipe gui) {
        try {
            Field f = gui.getClass().getField("width");
            return (Integer) f.get(gui);
        } catch (NoSuchFieldException e) {
            try {
                Field f = gui.getClass().getField("field_146294_l");
                return (Integer) f.get(gui);
            } catch (Exception e2) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getGuiHeight(GuiRecipe gui) {
        try {
            Field f = gui.getClass().getField("height");
            return (Integer) f.get(gui);
        } catch (NoSuchFieldException e) {
            try {
                Field f = gui.getClass().getField("field_146295_m");
                return (Integer) f.get(gui);
            } catch (Exception e2) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void drawScaledTexturedModelRectFromIcon(int i, int j, IIcon icon, int x, int y) {
        if (icon == null) return;

        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(i + 0, j + y, GuiDraw.gui.getZLevel(), minU, minV + (maxV - minV) * y / 16.0D);
        tessellator.addVertexWithUV(i + x, j + y, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * x / 16.0D, minV + (maxV - minV) * y / 16.0D);
        tessellator.addVertexWithUV(i + x, j + 0, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * x / 16.0D, minV);
        tessellator.addVertexWithUV(i + 0, j + 0, GuiDraw.gui.getZLevel(), minU, minV);
        tessellator.draw();
    }

    public boolean isSecondSearch(String outputId, Object... results) {
        return outputId.equals("fluid") && results.length == 2 && results[0] instanceof String && results[0].equals("fluid");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("fluid") && results.length == 1 && results[0] instanceof FluidStack) {
            FluidStack fluidStack = (FluidStack) results[0];
            if (fluidStack == null || fluidStack.getFluid() == null) return;
            String fluid = ((FluidStack) results[0]).getFluid().getName();
            ArrayList<ItemStack> stacks = NEIConfig.containers.get(fluid);
            if (stacks != null) {
                for (ItemStack stack : stacks) {
                    GuiCraftingRecipe.openRecipeGui("item", stack);
                }
            }

            GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { "fluid", fluid });
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals("fluid") && ingredients.length == 1) {
            Fluid fluidz = ((FluidStack) ingredients[0]).getFluid();
            if (fluidz == null) return;
            String fluid = fluidz.getName();
            ArrayList<ItemStack> stacks = NEIConfig.containers.get(fluid);
            if (stacks != null) {
                for (ItemStack stack : stacks) {
                    GuiUsageRecipe.openRecipeGui("item", stack);
                }
            }

            GuiUsageRecipe.openRecipeGui("fluid", new Object[] { "fluid", fluid });
        } else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    public Point getMouse(int width, int height) {
        Point mousepos = GuiDraw.getMousePosition();
        int guiLeft = (width - 176) / 2;
        int guiTop = (height - 166) / 2;
        Point relMouse = new Point(mousepos.x - guiLeft, mousepos.y - guiTop);
        return relMouse;
    }

    public void loadFluidsButton(int button, FluidStack fluid) {
        if (button == 0) {
            loadFluidRecipes(fluid);
        } else if (button == 1) {
            loadFluidUsages(fluid);
        }
    }

    public void loadFluidsKey(int key, FluidStack fluid) {
        if (key == NEIClientConfig.getKeyBinding("gui.recipe")) {
            loadFluidRecipes(fluid);
        } else if (key == NEIClientConfig.getKeyBinding("gui.usage")) {
            loadFluidUsages(fluid);
        }
    }

    public void loadFluidRecipes(FluidStack fluid) {
        GuiCraftingRecipe.openRecipeGui("fluid", new Object[] { fluid });
    }

    public void loadFluidUsages(FluidStack fluid) {
        GuiUsageRecipe.openRecipeGui("fluid", new Object[] { fluid });
    }

    public abstract boolean isOverItem(GuiRecipe gui, int id);
}
