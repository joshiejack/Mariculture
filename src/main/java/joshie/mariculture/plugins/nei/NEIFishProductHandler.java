package joshie.mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishProduct;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.util.MCTranslate;
import joshie.mariculture.fishery.items.ItemFishy;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIFishProductHandler extends NEIBase {
    public class ProductResult {
        public int x, y, bump, bump2;
        double chance;
        public PositionedStack stack;

        public ProductResult(ItemStack stack, int x, int y, double chance) {
            bump = x;
            bump2 = y;
            this.chance = chance;
            this.x = 72 + x * 18;
            this.y = 2 + y * 18;
            this.stack = new PositionedStack(stack, this.x, this.y);
        }
    }

    public class CachedProductRecipe extends CachedRecipe {
        PositionedStack input;
        List<ProductResult> outputs;

        public CachedProductRecipe(ItemStack input, ArrayList<FishProduct> outputs) {
            this.outputs = new ArrayList();
            this.input = new PositionedStack(input, 11, 21);
            int x = 0;
            int y = 0;
            for (FishProduct fish : outputs) {
                this.outputs.add(new ProductResult(fish.product.copy(), x, y, fish.chance));
                if (x == 4) {
                    x = 0;
                    y++;
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
            for (ProductResult result : outputs) {
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
                if (fish.getProductList() != null) {
                    arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
            FishSpecies fish = species.getValue();
            if (fish.getProductList() != null) {
                for (FishProduct product : fish.getProductList()) {
                    if (NEIServerUtils.areStacksSameTypeCrafting(product.product, result)) {
                        arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
                    }
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (!(ingredient.getItem() instanceof ItemFishy)) return;

        for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
            FishSpecies fish = species.getValue();
            if (NEIFishBreedingMutationHandler.isSpecies(ingredient, fish, false) && fish.getProductList() != null) {
                arecipes.add(new CachedProductRecipe(Fishing.fishHelper.makePureFish(fish), fish.getProductList()));
            }
        }
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int id) {
        if (stack != null) {
            CachedProductRecipe cache = (CachedProductRecipe) arecipes.get(id);
            if (cache != null) {
                for (ProductResult r : cache.outputs) {
                    if (gui.isMouseOver(r.stack, id)) {
                        currenttip.add(joshie.lib.util.Text.ORANGE + r.chance + "% " + MCTranslate.translate("chance"));
                    }
                }
            }
        }

        return currenttip;
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(41, 19, 22, 16), "fishproducts"));
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
