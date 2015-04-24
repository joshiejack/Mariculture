package mariculture.fishery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessResetFishRecipe implements IRecipe {
    private ItemStack output = null;
    private ArrayList input = new ArrayList();

    public ShapelessResetFishRecipe(Block result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapelessResetFishRecipe(Item result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapelessResetFishRecipe(ItemStack result, Object... recipe) {
        output = result.copy();
        for (Object in : recipe)
            if (in instanceof ItemStack) {
                input.add(((ItemStack) in).copy());
            } else if (in instanceof Item) {
                input.add(new ItemStack((Item) in));
            } else if (in instanceof Block) {
                input.add(new ItemStack((Block) in));
            } else if (in instanceof String) {
                input.add(OreDictionary.getOres((String) in));
            } else {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp : recipe) {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
    }

    ShapelessResetFishRecipe(ShapelessRecipes recipe, Map<ItemStack, String> replacements) {
        output = recipe.getRecipeOutput();
        for (ItemStack ingred : (List<ItemStack>) recipe.recipeItems) {
            Object finalObj = ingred;
            for (Entry<ItemStack, String> replace : replacements.entrySet())
                if (OreDictionary.itemMatches(replace.getKey(), ingred, false)) {
                    finalObj = OreDictionary.getOres(replace.getValue());
                    break;
                }
            input.add(finalObj);
        }
    }

    @Override
    public int getRecipeSize() {
        return input.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    public ItemStack getRecipeOutput(FishSpecies fish, int gender) {
        ItemStack stack = Fishing.fishHelper.makePureFish(fish).copy();
        Fish.gender.addDNA(stack, gender);
        Fish.gender.addLowerDNA(stack, gender);
        return stack;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting) {
        for (int i = 0; i < crafting.getSizeInventory(); i++) {
            ItemStack stack = crafting.getStackInSlot(i);
            if (stack != null) {
                FishSpecies species = Fishing.fishHelper.getSpecies(stack);
                if (species != null) {
                    if (Fishing.fishHelper.isPure(stack)) {
                        return getRecipeOutput(species, Fish.gender.getDNA(stack));
                    }
                }
            }
        }

        return null;
    }

    @Override
    public boolean matches(InventoryCrafting var1, World world) {
        ArrayList required = new ArrayList(input);

        for (int x = 0; x < var1.getSizeInventory(); x++) {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null) {
                boolean inRecipe = false;
                Iterator req = required.iterator();

                while (req.hasNext()) {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack) {
                        match = checkItemEquals((ItemStack) next, slot);
                    } else if (next instanceof ArrayList) {
                        for (ItemStack item : (ArrayList<ItemStack>) next) {
                            match = match || checkItemEquals(item, slot);
                        }
                    }

                    if (match) {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe) return false;
            }
        }

        return required.isEmpty();
    }

    private boolean checkItemEquals(ItemStack target, ItemStack input) {
        return target.getItem() == input.getItem() && (target.getItemDamage() == OreDictionary.WILDCARD_VALUE || target.getItemDamage() == input.getItemDamage());
    }

    public ArrayList getInput() {
        return input;
    }
}
