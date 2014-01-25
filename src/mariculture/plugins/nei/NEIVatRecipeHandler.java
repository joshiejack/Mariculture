package mariculture.plugins.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.core.Mariculture;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.Text;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.core.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.FurnaceRecipeHandler.SmeltingPair;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class NEIVatRecipeHandler extends NEIBase {
	public class CachedVatRecipe extends CachedRecipe {
		RecipeVat recipe;
		PositionedStack input;
		PositionedStack output;

		public CachedVatRecipe(RecipeVat recipe) {
			if (recipe.inputItem != null)
				this.input = new PositionedStack(recipe.inputItem.copy(), 28, 17);
			if (recipe.outputItem != null)
				this.output = new PositionedStack(recipe.outputItem.copy(), 121, 17);
			this.recipe = recipe;
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
		if (outputId.equals("vat") && getClass() == NEIVatRecipeHandler.class) {
			HashMap<List<? extends Object>, RecipeVat> recipes = MaricultureHandlers.vat.getRecipes();
			for (Entry<List<? extends Object>, RecipeVat> recipe : recipes.entrySet()) {
				arecipes.add(new CachedVatRecipe(recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<List<? extends Object>, RecipeVat> recipes = MaricultureHandlers.vat.getRecipes();
		for (Entry<List<? extends Object>, RecipeVat> recipe : recipes.entrySet()) {
			if (recipe.getValue().outputItem != null) {
				ItemStack item = recipe.getValue().outputItem;
				if (OreDicHelper.convert(result).equals(OreDicHelper.convert(item))) {
					arecipes.add(new CachedVatRecipe(recipe.getValue()));
				}
			}

			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
			if (fluid == null || recipe.getValue().outputFluid == null) {
				continue;
			}

			if (fluid.getFluid().getName().equals(recipe.getValue().outputFluid.getFluid().getName())) {
				arecipes.add(new CachedVatRecipe(recipe.getValue()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<List<? extends Object>, RecipeVat> recipes = MaricultureHandlers.vat.getRecipes();
		for (Entry<List<? extends Object>, RecipeVat> recipe : recipes.entrySet()) {
			RecipeVat vat = recipe.getValue();
			if (vat.inputItem != null) {
				if (OreDicHelper.convert(ingredient).equals(OreDicHelper.convert(vat.inputItem))) {
					arecipes.add(new CachedVatRecipe(recipe.getValue()));
				}
			}

			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(ingredient);
			if (fluid == null)
				continue;

			if (vat.inputFluid1 != null) {
				if (fluid.getFluid().getName().equals(recipe.getValue().inputFluid1.getFluid().getName())) {
					arecipes.add(new CachedVatRecipe(recipe.getValue()));
				}
			}

			if (vat.inputFluid2 != null) {
				if (fluid.getFluid().getName().equals(recipe.getValue().inputFluid2.getFluid().getName())) {
					arecipes.add(new CachedVatRecipe(recipe.getValue()));
				}
			}
		}
	}

	@Override
	public void drawExtras(int id) {
		CachedVatRecipe cache = (CachedVatRecipe) arecipes.get(id);
		RecipeVat recipe = cache.recipe;
		if (recipe.inputFluid1 != null) {
			drawFluidRect(12, 31, recipe.inputFluid1, TankSize.VAT);
			if (recipe.inputFluid2 == null)
				drawFluidRect(36, 31, recipe.inputFluid1, TankSize.VAT);
			else
				drawFluidRect(36, 31, recipe.inputFluid2, TankSize.VAT);
		}

		if (recipe.outputFluid != null) {
			drawFluidRect(105, 31, recipe.outputFluid, TankSize.VAT);
			drawFluidRect(129, 31, recipe.outputFluid, TankSize.VAT);
		}

		int time = recipe.processTime;
		int x = time < 10 ? 76 : 72;
		Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + "" + time + "s", x, 16, 0);
	}
	
	@Override
	public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
		currenttip = super.handleTooltip(gui, currenttip, id);
		Point mouse = getMouse(gui.width, gui.height);
		if (isOverItem(gui, id))
			return currenttip;

		RecipeVat recipe = ((CachedVatRecipe) arecipes.get(id)).recipe;
		String fluid = null;
		String fluid2 = null;
		String fluid3 = null;

		if (recipe.inputFluid1 != null)
			fluid = StringHelper.getFluidName(recipe.inputFluid1.getFluid());
		if (recipe.inputFluid2 != null)
			fluid2 = StringHelper.getFluidName(recipe.inputFluid2.getFluid());
		if (recipe.outputFluid != null)
			fluid3 = StringHelper.getFluidName(recipe.outputFluid.getFluid());
		int yLow = id % 2 == 0 ? 19 : 85;
		int yHigh = id % 2 == 0 ? 63 : 128;

		int xEnd = recipe.inputFluid2 == null ? 64 : 40;
		if (fluid != null) {
			if (mouse.x >= 16 && mouse.x <= xEnd && mouse.y >= yLow && mouse.y <= yHigh) {
				currenttip.add(fluid);
				StringHelper.getFluidQty(currenttip, recipe.inputFluid1, -1);
			}

			if (fluid2 != null) {
				if (mouse.x > xEnd && mouse.x <= 64 && mouse.y >= yLow && mouse.y <= yHigh) {
					currenttip.add(fluid2);
					StringHelper.getFluidQty(currenttip, recipe.inputFluid2, -1);
				}
			}
		}

		if (fluid3 != null) {
			if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
				currenttip.add(fluid3);
				StringHelper.getFluidQty(currenttip, recipe.outputFluid, -1);
			}
		}

		return currenttip;
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int id) {
		super.mouseClicked(gui, button, id);
		Point mouse = getMouse(gui.width, gui.height);
		if (isOverItem(gui, id))
			return false;

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

		if (recipe.outputFluid != null) {
			if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
				loadFluidsButton(button, recipe.outputFluid);
			}
		}

		return false;
	}
	
	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int id) {
		super.keyTyped(gui, keyChar, keyCode, id);
		Point mouse = getMouse(gui.width, gui.height);
		if (isOverItem(gui, id))
			return false;

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

		if (recipe.outputFluid != null) {
			if (mouse.x > 109 && mouse.x <= 157 && mouse.y >= yLow && mouse.y <= yHigh) {
				loadFluidsKey(keyCode, recipe.outputFluid);
			}
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
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 15, 166, 73);
		drawExtras(recipe);
	}

	@Override
	public void drawForeground(int recipe) {
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		changeTexture(getGuiTexture());
	}

	@Override
	public String getOverlayIdentifier() {
		return "vat";
	}
	
	@Override
	public boolean isOverItem(GuiRecipe gui, int id) {
		CachedVatRecipe cache = (CachedVatRecipe) arecipes.get(id);
		return (cache.input != null && gui.isMouseOver(cache.input, id)) || (cache.output != null && gui.isMouseOver(cache.output, id));
	}
}
