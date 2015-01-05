package mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Modules;
import mariculture.magic.JewelryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIAnvilRecipeHandler extends NEIBase {
    public static final HashMap<ItemStack, RecipeJewelry> jewelry = new HashMap();

    public static class RecipeJewelry {
        int hits;
        ItemStack input;

        public RecipeJewelry(ItemStack input, int hits) {
            this.hits = hits;
            this.input = input;
        }
    }

    public class CachedAnvilRecipe extends CachedRecipe {
        int hits;
        PositionedStack input;
        PositionedStack output;

        public CachedAnvilRecipe(ItemStack input, ItemStack output, int hits) {
            this.hits = hits;
            this.input = new PositionedStack(input.copy(), 62, 12);
            this.output = new PositionedStack(output.copy(), 128, 11);
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
        if (outputId.equals("blacksmithanvil") && getClass() == NEIAnvilRecipeHandler.class) {
            HashMap<String, RecipeAnvil> recipes = MaricultureHandlers.anvil.getRecipes();
            for (Entry<String, RecipeAnvil> recipe : recipes.entrySet()) {
                arecipes.add(new CachedAnvilRecipe(recipe.getValue().input, recipe.getValue().output, recipe.getValue().hits));
            }

            if (Modules.isActive(Modules.magic)) {
                for (Entry<ItemStack, RecipeJewelry> recipe : jewelry.entrySet()) {
                    arecipes.add(new CachedAnvilRecipe(recipe.getValue().input, recipe.getKey(), recipe.getValue().hits));
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        HashMap<String, RecipeAnvil> recipes = MaricultureHandlers.anvil.getRecipes();
        for (Entry<String, RecipeAnvil> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameTypeCrafting(result, recipe.getValue().output)) {
                arecipes.add(new CachedAnvilRecipe(recipe.getValue().input, recipe.getValue().output, recipe.getValue().hits));
            }

        if (Modules.isActive(Modules.magic)) {
            for (Entry<ItemStack, RecipeJewelry> recipe : jewelry.entrySet())
                if (result.hasTagCompound() && recipe.getKey().hasTagCompound()) {
                    ItemStack other = recipe.getKey();
                    if (JewelryHandler.getMaterial(other) == JewelryHandler.getMaterial(result) && JewelryHandler.getBinding(other) == JewelryHandler.getBinding(result) && JewelryHandler.getType(other) == JewelryHandler.getType(result)) {
                        arecipes.add(new CachedAnvilRecipe(recipe.getValue().input, recipe.getKey(), recipe.getValue().hits));
                    }
                }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        HashMap<String, RecipeAnvil> recipes = MaricultureHandlers.anvil.getRecipes();
        for (Entry<String, RecipeAnvil> recipe : recipes.entrySet())
            if (OreDicHelper.convert(ingredient).equals(OreDicHelper.convert(recipe.getValue().input))) {
                arecipes.add(new CachedAnvilRecipe(recipe.getValue().input, recipe.getValue().output, recipe.getValue().hits));
            }
    }

    @Override
    public void drawExtras(int id) {
        CachedAnvilRecipe cache = (CachedAnvilRecipe) arecipes.get(id);
        Minecraft.getMinecraft().fontRenderer.drawString(mariculture.lib.util.Text.GREY + cache.hits + " Hits", 78, 38, 0);
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(88, 13, 22, 16), "blacksmithanvil"));
    }

    @Override
    public String getRecipeName() {
        return "Blacksmith's Anvil";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Mariculture.modid, "textures/gui/nei/anvil.png").toString();
    }

    @Override
    public String getOverlayIdentifier() {
        return "blacksmithanvil";
    }

    @Override
    public boolean isOverItem(GuiRecipe gui, int id) {
        return false;
    }
}
