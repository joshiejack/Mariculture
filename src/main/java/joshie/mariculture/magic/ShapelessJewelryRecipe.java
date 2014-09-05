package joshie.mariculture.magic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import joshie.mariculture.magic.ShapedJewelryRecipe.LinkedInteger;
import joshie.mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapelessJewelryRecipe implements IRecipe {
    private ItemStack output = null;
    private ArrayList<Object> input = new ArrayList<Object>();

    public ShapelessJewelryRecipe(Block result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapelessJewelryRecipe(Item result, Object... recipe) {
        this(new ItemStack(result), recipe);
    }

    public ShapelessJewelryRecipe(ItemStack result, Object... recipe) {
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

    @SuppressWarnings("unchecked")
    ShapelessJewelryRecipe(ShapelessRecipes recipe, Map<ItemStack, String> replacements) {
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

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craft) {
        ItemStack ret = output.copy();
        if (!ret.hasTagCompound()) {
            ret.setTagCompound(new NBTTagCompound());
        }

        ArrayList<LinkedInteger> cache = new ArrayList();
        for (int j = 0; j < craft.getSizeInventory(); j++) {
            ItemStack stack = craft.getStackInSlot(j);
            if (stack != null) {
                LinkedHashMap<Integer, Integer> maps = (LinkedHashMap<Integer, Integer>) EnchantmentHelper.getEnchantments(stack);
                for (Entry<Integer, Integer> i : maps.entrySet()) {
                    Enchantment enchant = Enchantment.enchantmentsList[i.getKey()];
                    cache.add(new LinkedInteger(enchant.effectId, EnchantmentHelper.getEnchantmentLevel(enchant.effectId, stack)));
                }
            }
        }

        if (cache.size() > 0) {
            Collections.shuffle(cache);
            int[] level = new int[cache.size()];
            int[] enchant = new int[cache.size()];
            for (int i = 0; i < cache.size(); i++) {
                enchant[i] = cache.get(i).enchant;
                level[i] = cache.get(i).level;
            }

            ret.stackTagCompound.setIntArray("EnchantmentList", enchant);
            ret.stackTagCompound.setIntArray("EnchantmentLevels", level);
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting var1, World world) {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++) {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null) {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

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
        if (target.getItem() instanceof ItemJewelry) return JewelryHandler.getBinding(target) == JewelryHandler.getBinding(input) && JewelryHandler.getMaterial(target) == JewelryHandler.getMaterial(input) && JewelryHandler.getType(target) == JewelryHandler.getType(input);
        else return target.getItem() == input.getItem() && (target.getItemDamage() == OreDictionary.WILDCARD_VALUE || target.getItemDamage() == input.getItemDamage());
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should
     * never manipulate the values in this array as it will effect the recipe
     * itself.
     * 
     * @return The recipes input vales.
     */
    public ArrayList<Object> getInput() {
        return input;
    }
}