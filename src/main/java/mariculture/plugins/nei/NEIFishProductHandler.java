package mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishProduct;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Text;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIFishProductHandler extends NEIBase {
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
	
	public class CachedProductRecipe extends CachedRecipe {
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
		
		@Override
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
			for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
				Integer fishID = species.getKey();
				FishSpecies fish = species.getValue();
				arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
			FishSpecies fish = species.getValue();
			for(FishProduct product: fish.getProductList()) {
				if(NEIServerUtils.areStacksSameTypeCrafting(product.product, result)) {
					arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
				}
			}
		}
	}
	
	@Override
    public void loadUsageRecipes(ItemStack ingredient)  {
		if(!(ingredient.getItem() instanceof ItemFishy)) {
			return;
		}
		
		for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
			FishSpecies fish = species.getValue();
			if(NEIFishBreedingMutationHandler.isSpecies(ingredient, fish, false)) {
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
