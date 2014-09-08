package joshie.mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.recipes.RecipeSifter;
import joshie.mariculture.core.helpers.OreDicHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEISifterRecipeHandler extends NEIBase {
    public class SifterResult {
        int id;
        RecipeSifter recipe;
        public ArrayList<PositionedStack> stacks;

        public SifterResult(RecipeSifter recipe, int x) {
            id = x;
            stacks = new ArrayList<PositionedStack>();
            for (int i = recipe.minCount; i <= recipe.maxCount; i++) {
                ItemStack bait = recipe.bait.copy();
                bait.stackSize = i;
                stacks.add(new PositionedStack(bait.copy(), 70 + x * 18, 15, false));
            }

            this.recipe = recipe;
        }

        public PositionedStack getStack() {
            return stacks.get(cycleticks / 48 % stacks.size());
        }
    }

    public class CachedSifterRecipe extends CachedRecipe {
        List<PositionedStack> inputs;
        //PositionedStack input;
        List<SifterResult> outputs;

        public CachedSifterRecipe(ItemStack input, ArrayList<RecipeSifter> output) {
            inputs = new ArrayList();
            if (input == null) {
                inputs = generateList(output);
            } else {
                inputs.add(new PositionedStack(input, 12, 16));
            }
            outputs = new ArrayList<SifterResult>();
            int x = 0;
            for (RecipeSifter recipe : output) {
                outputs.add(new SifterResult(recipe, x));
                x++;
            }
        }

        private List<PositionedStack> generateList(ArrayList<RecipeSifter> output) {
            List<PositionedStack> list = new ArrayList();
            ItemStack stack = output.get(0).block;
            if (stack.getItemDamage() == Short.MAX_VALUE) {
                List<ItemStack> permutations = ItemList.itemMap.get(stack.getItem());
                if (!permutations.isEmpty()) {
                    for (ItemStack stack2 : permutations) {
                        list.add(new PositionedStack(stack2.copy(), 12, 16));
                    }
                } else {
                    ItemStack base = new ItemStack(stack.getItem(), stack.stackSize);
                    base.stackTagCompound = stack.stackTagCompound;
                    list.add(new PositionedStack(base, 12, 16));
                }
            } else {
                list.add(new PositionedStack(stack, 12, 16));
            }

            return list;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            for (SifterResult result : outputs) {
                stacks.add(result.getStack());
            }

            return stacks;
        }

        @Override
        public PositionedStack getIngredient() {
            if (inputs == null || inputs.size() < 1) return null;
            return inputs.get(cycleticks / 48 % inputs.size());
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("sifter") && getClass() == NEISifterRecipeHandler.class) {
            HashMap<List<? extends Object>, ArrayList<RecipeSifter>> recipes = Fishing.sifter.getRecipes();
            for (Entry<List<? extends Object>, ArrayList<RecipeSifter>> recipe : recipes.entrySet()) {
                arecipes.add(new CachedSifterRecipe(null, recipe.getValue()));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        HashMap<List<? extends Object>, ArrayList<RecipeSifter>> recipes = Fishing.sifter.getRecipes();
        for (Entry<List<? extends Object>, ArrayList<RecipeSifter>> recipe : recipes.entrySet()) {
            for (RecipeSifter sifter : recipe.getValue()) {
                if (OreDicHelper.convert(sifter.bait).equals(OreDicHelper.convert(result))) {
                    arecipes.add(new CachedSifterRecipe(null, recipe.getValue()));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        ArrayList<RecipeSifter> recipes = Fishing.sifter.getResult(ingredient);
        if (recipes != null) {
            arecipes.add(new CachedSifterRecipe(ingredient, recipes));
        }
    }

    @Override
    public void drawExtras(int id) {
        CachedSifterRecipe cache = (CachedSifterRecipe) arecipes.get(id);
        for (SifterResult recipe : cache.outputs) {
            GL11.glPushMatrix();
            GL11.glScalef(0.9F, 0.9F, 0.9F);
            Minecraft.getMinecraft().fontRenderer.drawString(joshie.lib.util.Text.GREY + recipe.recipe.chance + "%", 78 + recipe.id * 21, 38, 0);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 65);
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
