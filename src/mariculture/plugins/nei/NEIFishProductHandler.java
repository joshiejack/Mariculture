package mariculture.plugins.nei;

import static codechicken.core.gui.GuiDraw.changeTexture;
import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishProduct;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Text;
import mariculture.plugins.nei.NEIFishProductHandler.CachedProductRecipe.ProductResult;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIFishProductHandler extends NEIBase {
	public class CachedProductRecipe extends CachedRecipe {
		public class ProductResult {
			public int x, y, bump, bump2;
			double chance;
			public PositionedStack stack;
	        public ProductResult(ItemStack stack, int x, int y, double chance) {
	        	this.bump = x;
	        	this.bump2 = y;
	        	this.chance = chance;
	        	this.x = 93 + x * 18;
	        	this.y = 1 + y * 32;
	        	this.stack = new PositionedStack(stack, this.x, this.y);
	        }
	    }
		
		PositionedStack input;
		List<ProductResult> outputs;

		public CachedProductRecipe(ItemStack input, ArrayList<FishProduct> outputs) {
			this.outputs = new ArrayList();
			this.input = new PositionedStack(input, 20, 18);
			int x = 0;
			int y = 0;
			for(FishProduct fish: outputs) {
				this.outputs.add(new ProductResult(fish.product.copy(), x, y, fish.chance));
				if(x == 2) {
					x = 0;
					y = 1;
				} else {
					x++;
				}
			}
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
		
		public List<PositionedStack> getOtherStacks() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            for(ProductResult result: this.outputs) {
            	stacks.add(result.stack);
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
		if (outputId.equals("fishproducts") && getClass() == NEIFishProductHandler.class) {
			for(int i = 0; i < FishSpecies.speciesList.size(); i++) {
				FishSpecies fish = FishSpecies.speciesList.get(i);
				arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for(int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);
			for(FishProduct product: fish.getProductList()) {
				if(NEIServerUtils.areStacksSameTypeCrafting(product.product, result)) {
					arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
				}
			}
		}
	}
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient)  {
		for(int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);
			if(NEIFishBreedingMutationHandler.isAFish(ingredient, i)) {
				arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
			}
		}
    }
	
	@Override
	public void drawExtras(int id) {
		CachedProductRecipe cache = (CachedProductRecipe) arecipes.get(id);
		for(ProductResult recipe: cache.outputs) {
			GL11.glPushMatrix();
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + recipe.chance + "%", recipe.x + 59 + (recipe.bump * 15), recipe.y + 34 + (recipe.bump2 * 20), 0);
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
		transferRects.add(new RecipeTransferRect(new Rectangle(54, 16, 22, 16), "fishproducts"));
	}
	
	@Override
	public String getRecipeName() {
		return "Fish Products";
	}

	@Override
	public boolean isOverItem(GuiRecipe gui, int id) {
		return false;
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(Mariculture.modid, "textures/gui/nei/fishproducts.png").toString();
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "fishproducts";
	}
}
