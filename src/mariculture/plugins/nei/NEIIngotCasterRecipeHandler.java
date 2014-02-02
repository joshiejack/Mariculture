package mariculture.plugins.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.cofh.StringHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIIngotCasterRecipeHandler extends NEIBase {
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
		if (outputId.equals("caster") && getClass() == NEIIngotCasterRecipeHandler.class) {
			HashMap<String, RecipeIngotCasting> recipes = MaricultureHandlers.casting.getRecipes();
			for (Entry<String, RecipeIngotCasting> recipe : recipes.entrySet()) {
				arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<String, RecipeIngotCasting> recipes = MaricultureHandlers.casting.getRecipes();
		for (Entry<String, RecipeIngotCasting> recipe : recipes.entrySet()) {
			if(OreDicHelper.convert(result).equals(OreDicHelper.convert(recipe.getValue().output))) {
				arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<String, RecipeIngotCasting> recipes = MaricultureHandlers.casting.getRecipes();
		for (Entry<String, RecipeIngotCasting> recipe : recipes.entrySet()) {
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(ingredient);
			if(fluid == null)
				continue;
			if (fluid.getFluid().getName().equals(recipe.getValue().fluid.getFluid().getName())) {
				arecipes.add(new CachedCasterRecipe(recipe.getValue().fluid, recipe.getValue().output));
			}
		}
	}

	@Override
	public void drawExtras(int id) {
		CachedCasterRecipe cache = (CachedCasterRecipe)arecipes.get(id);
		drawFluidRect(22, 15, cache.fluid, TankSize.CASTER);
	}
	
	@Override
	public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int id) {
		currenttip = super.handleTooltip(gui, currenttip, id);
		Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
		if (isOverItem(gui, id))
			return currenttip;

		CachedCasterRecipe cache = (CachedCasterRecipe)arecipes.get(id);
		String fluid = StringHelper.getFluidName(cache.fluid);

		int yLow = id % 2 == 0 ? 26 : 89;
		int yHigh = id % 2 == 0 ? 73 : 138;

		if (mouse.x >= 20 && mouse.x <= 68 && mouse.y >= yLow && mouse.y <= yHigh) {
			currenttip.add(fluid);
			StringHelper.getFluidQty(currenttip, cache.fluid, -1);
		}
		
		return currenttip;
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int id) {
		super.mouseClicked(gui, button, id);
		Point mouse = getMouse(getGuiWidth(gui), getGuiHeight(gui));
		if (isOverItem(gui, id))
			return false;

		CachedCasterRecipe cache = (CachedCasterRecipe)arecipes.get(id);
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
		if (isOverItem(gui, id))
			return false;

		CachedCasterRecipe cache = (CachedCasterRecipe)arecipes.get(id);
		int yLow = id % 2 == 0 ? 26 : 89;
		int yHigh = id % 2 == 0 ? 73 : 138;

		if (mouse.x >= 20 && mouse.x <= 68 && mouse.y >= yLow && mouse.y <= yHigh) {
			loadFluidsKey(keyCode, cache.fluid);
		}
		
		return false;
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(77, 26, 22, 16), "caster"));
	}

	@Override
	public String getRecipeName() {
		return "Ingot Caster";
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(Mariculture.modid, "textures/gui/nei/caster.png").toString();
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
		return "caster";
	}
	
	@Override
	public boolean isOverItem(GuiRecipe gui, int id) {
		CachedCasterRecipe cache = (CachedCasterRecipe) arecipes.get(id);
		return (cache.output != null && gui.isMouseOver(cache.output, id));
	}
}
