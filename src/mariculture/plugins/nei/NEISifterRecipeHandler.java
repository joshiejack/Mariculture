package mariculture.plugins.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.Mariculture;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Text;
import mariculture.plugins.nei.NEIIngotCasterRecipeHandler.CachedCasterRecipe;
import mariculture.plugins.nei.NEISifterRecipeHandler.CachedSifterRecipe.SifterResult;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class NEISifterRecipeHandler extends NEIBase {
	public class CachedSifterRecipe extends CachedRecipe {
		public class SifterResult {
			int id;
			RecipeSifter recipe;
			public ArrayList<PositionedStack> stacks;
	        public SifterResult(RecipeSifter recipe, int x) {
	        	this.id = x;
	        	stacks = new ArrayList<PositionedStack>();
	        	for(int i = recipe.minCount; i <= recipe.maxCount; i++) {
	        		ItemStack bait = recipe.bait.copy();
	        		bait.stackSize = i;
	        		stacks.add(new PositionedStack(bait.copy(), 70 + (x * 18), 15, false));
	        	}
	        	
	            this.recipe = recipe;
	        }
	        
	        public PositionedStack getStack() {
	        	return stacks.get((cycleticks/48) % stacks.size());
	        }
	    }
		
		PositionedStack input;
		List<SifterResult> outputs;

		public CachedSifterRecipe(ItemStack input, ArrayList<RecipeSifter> output) {
			this.input = new PositionedStack(input, 12, 16);
			outputs = new ArrayList<SifterResult>();
			int x = 0;
			for(RecipeSifter recipe: output) {
				outputs.add(new SifterResult(recipe, x));
				x++;
			}
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
		
		public List<PositionedStack> getOtherStacks() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            for(SifterResult result: outputs) {
            	stacks.add(result.getStack());
            }

            return stacks;
        }

		@Override
		public PositionedStack getIngredient() {
			return input;
		}
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("sifter") && getClass() == NEISifterRecipeHandler.class) {
			HashMap<String, ArrayList<RecipeSifter>> recipes = Fishing.sifter.getRecipes();
			for (Entry<String, ArrayList<RecipeSifter>> recipe : recipes.entrySet()) {
				arecipes.add(new CachedSifterRecipe(recipe.getValue().get(0).block, recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<String, ArrayList<RecipeSifter>> recipes = Fishing.sifter.getRecipes();
		for (Entry<String, ArrayList<RecipeSifter>> recipe : recipes.entrySet()) {
			for(RecipeSifter sifter: recipe.getValue()) {
				if(OreDicHelper.convert(sifter.bait).equals(OreDicHelper.convert(result))) {
					arecipes.add(new CachedSifterRecipe(sifter.block, recipe.getValue()));
				}
			}
		}
	}
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient)  {
		HashMap<String, ArrayList<RecipeSifter>> recipes = Fishing.sifter.getRecipes();
		for (Entry<String, ArrayList<RecipeSifter>> recipe : recipes.entrySet()) {
			if(recipe.getKey().equals(OreDicHelper.convert(ingredient))) {
				arecipes.add(new CachedSifterRecipe(recipe.getValue().get(0).block, recipe.getValue()));
			}
		}
    }
	
	@Override
	public void drawExtras(int id) {
		CachedSifterRecipe cache = (CachedSifterRecipe) arecipes.get(id);
		for(SifterResult recipe: cache.outputs) {
			GL11.glPushMatrix();
			GL11.glScalef(0.9F, 0.9F, 0.9F);
			Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + recipe.recipe.chance + "%", 78 + recipe.id * 21, 38, 0);
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 65);
    }
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(39, 16, 22, 16), "sifter"));
	}
	
	@Override
	public String getRecipeName() {
		return "Sifter";
	}

	@Override
	public boolean isOverItem(GuiRecipe gui, int id) {
		return false;
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(Mariculture.modid, "textures/gui/nei/sifter.png").toString();
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "sifter";
	}
}
