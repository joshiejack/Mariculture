package joshie.mariculture.core.handlers;

import java.util.HashMap;

import joshie.mariculture.api.core.ICastingHandler;
import joshie.mariculture.api.core.RecipeCasting;
import joshie.mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import joshie.mariculture.api.core.RecipeCasting.RecipeIngotCasting;
import joshie.mariculture.api.core.RecipeCasting.RecipeNuggetCasting;
import joshie.mariculture.core.helpers.OreDicHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CastingHandler implements ICastingHandler {
    private final HashMap<String, RecipeCasting> nuggets = new HashMap();
    private final HashMap<String, RecipeCasting> ingots = new HashMap();
    private final HashMap<String, RecipeCasting> blocks = new HashMap();

    @Override
    public void addRecipe(RecipeCasting recipe) {
        if (recipe instanceof RecipeNuggetCasting) {
            nuggets.put(recipe.fluid.getFluid().getName(), recipe);
        }
        if (recipe instanceof RecipeIngotCasting) {
            ingots.put(recipe.fluid.getFluid().getName(), recipe);
        }
        if (recipe instanceof RecipeBlockCasting) {
            blocks.put(recipe.fluid.getFluid().getName(), recipe);
        }
    }

    @Override
    public RecipeCasting getNuggetResult(FluidStack fluid) {
        if (fluid == null) return null;
        RecipeCasting result = nuggets.get(fluid.getFluid().getName());
        if (result == null) return null;
        return fluid.amount < result.fluid.amount ? null : result;
    }

    @Override
    public RecipeCasting getIngotResult(FluidStack fluid) {
        if (fluid == null) return null;
        RecipeCasting result = ingots.get(fluid.getFluid().getName());
        if (result == null) return null;
        return fluid.amount < result.fluid.amount ? null : result;
    }

    @Override
    public RecipeCasting getBlockResult(FluidStack fluid) {
        if (fluid == null) return null;
        RecipeCasting result = blocks.get(fluid.getFluid().getName());
        if (result == null) return null;
        return fluid.amount < result.fluid.amount ? null : result;
    }

    //Attempt to fetch the texture, using the ore dictionary
    public static IIcon getTexture(ItemStack stack) {
        String name = OreDicHelper.getDictionaryName(stack);
        if (name.startsWith("nugget")) {
            name = name.substring(6);
        } else {
            name = name.substring(5);
        }

        name = "block" + name;

        Block block = null;
        int damage = stack.getItemDamage();
        if (OreDictionary.getOres(name).size() > 0) {
            ItemStack item = OreDictionary.getOres(name).get(0);
            block = Block.getBlockFromItem(item.getItem());
            damage = item.getItemDamage();
        } else if (stack.getItem() == Items.snowball) {
            block = Blocks.snow;
        } else if (OreDicHelper.getDictionaryName(stack).equals("foodSalt")) {
            block = Blocks.wool;
            damage = 0;
        } else {
            block = Block.getBlockFromItem(stack.getItem());
        }

        return block != null ? block.getIcon(0, damage) : Blocks.iron_block.getIcon(0, 0);
    }

    @Override
    public HashMap<String, RecipeCasting> getNuggetRecipes() {
        return nuggets;
    }

    @Override
    public HashMap<String, RecipeCasting> getIngotRecipes() {
        return ingots;
    }

    @Override
    public HashMap<String, RecipeCasting> getBlockRecipes() {
        return blocks;
    }
}
