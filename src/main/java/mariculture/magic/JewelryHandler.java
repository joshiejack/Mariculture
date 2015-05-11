package mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.config.Enchantments;
import mariculture.core.helpers.NBTHelper;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class JewelryHandler {
    public static void addJewelry(JewelryType type, ItemStack result, ItemStack binding, ItemStack material, int hits) {
        if (type == JewelryType.RING) {
            addRing(result, binding, material, hits);
        } else if (type == JewelryType.BRACELET) {
            addBracelet(result, binding, material, hits);
        } else if (type == JewelryType.NECKLACE) {
            addNecklace(result, binding, material, hits);
        }
    }

    public static void addRing(ItemStack result, ItemStack binding, ItemStack material, int hits) {
        //Anvil Recipes
        CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { "ABA", "A A", "AAA", 'A', binding, 'B', material }));

        //Swap Jewelry Recipe - Allow the swapping of jewelry pieces
        CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits / 3), new Object[] { material, result }));
    }

    public static void addBracelet(ItemStack result, ItemStack binding, ItemStack material, int hits) {
        //Anvil Recipes
        CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { "A A", "B B", " B ", 'A', binding, 'B', material }));

        //Swap Jewelry Recipe - Allow the swapping of jewelry pieces
        CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits / 3), new Object[] { material, result }));
    }

    public static void addNecklace(ItemStack result, ItemStack binding, ItemStack material, int hits) {
        //Anvil Recipes
        CraftingManager.getInstance().getRecipeList().add(new ShapedJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits), new Object[] { "BAB", "B B", "BBB", 'A', binding, 'B', material }));

        //Swap Jewelry Recipe - Allow the swapping of jewelry pieces
        CraftingManager.getInstance().getRecipeList().add(new ShapelessJewelryRecipe(MaricultureHandlers.anvil.createWorkedItem(result, hits / 3), new Object[] { material, result }));
    }

    //Returns the jewelry binding this item has
    public static JewelryBinding getBinding(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(JewelryBinding.nbt)) return JewelryBinding.list.get(stack.stackTagCompound.getString(JewelryBinding.nbt));
        else return Magic.dummyBinding;
    }

    //Returns the material this item has
    public static JewelryMaterial getMaterial(ItemStack stack) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey(JewelryMaterial.nbt)) return JewelryMaterial.list.get(stack.stackTagCompound.getString(JewelryMaterial.nbt));
        else return Magic.dummyMaterial;
    }

    //Returns the item type based
    public static JewelryType getType(ItemStack stack) {
        return stack.getItem() instanceof ItemJewelry ? ((ItemJewelry) stack.getItem()).getType() : JewelryType.NONE;
    }

    //Creates a 'special' piece of jewelry
    public static ItemStack createSpecial(ItemJewelry item, JewelryType accepted, String special) {
        ItemStack stack = new ItemStack(item);
        if (getType(stack) == accepted) {
            stack.setTagCompound(new NBTTagCompound());
            stack.stackTagCompound.setString("Specials", special);
            return stack;
        } else return null;
    }

    //Creates a piece of jewelry with these specific settings
    public static ItemStack createJewelry(ItemJewelry item, JewelryBinding binding, JewelryMaterial material) {
        ItemStack stack = new ItemStack(item);
        stack.setTagCompound(new NBTTagCompound());
        stack.stackTagCompound.setString(JewelryBinding.nbt, binding.getIdentifier());
        stack.stackTagCompound.setString(JewelryMaterial.nbt, material.getIdentifier());
        return stack;
    }

    public static int getLevel(JewelryType type, JewelryMaterial mat, JewelryBinding bind, int start) {
        return Math.min(start, Math.min(bind.getMaxEnchantmentLevel(type), mat.getMaximumEnchantmentLevel(type)));
    }

    public static boolean canApply(ItemStack stack) {
        if (Enchantments.DISABLE_BOOKS_ON_PEARLS) {
            if (stack.getItem() == Core.pearls) {
                return false;
            }
        }

        if (stack.getItem() instanceof ItemJewelry && stack.hasTagCompound()) {
            if (Enchantments.ALLOW_MC_ANVIL) {
                JewelryType type = getType(stack);
                JewelryMaterial material = getMaterial(stack);
                int current = EnchantmentHelper.getEnchantments(stack).size();
                int max = type.getMaximumEnchantments() + material.getExtraEnchantments(type);
                return current < max;
            } else return false;
        } else return true;
    }

    public static ItemStack finishJewelry(ItemStack stack, ItemStack result, Random rand) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("EnchantmentList") && stack.stackTagCompound.hasKey("EnchantmentLevels")) {
            JewelryType type = getType(result);
            JewelryMaterial material = getMaterial(result);
            JewelryBinding binding = getBinding(result);
            int chance = binding.getKeepEnchantmentChance(type);
            int[] enchants = stack.stackTagCompound.getIntArray("EnchantmentList");
            int[] levels = stack.stackTagCompound.getIntArray("EnchantmentLevels");
            int max = type.getMaximumEnchantments() + material.getExtraEnchantments(type);
            LinkedHashMap<Integer, Integer> existingMap = (LinkedHashMap<Integer, Integer>) EnchantmentHelper.getEnchantments(result);
            HashMap<Integer, Integer> newMap = new HashMap();
            List<Integer> existing = new ArrayList();
            int totalAdded = 0;
            
            //Reset all the enchantment data on the result
            result.stackTagCompound.setTag("ench", new NBTTagList());
            
            //Add all the existing enchantments to the new map
            for (Entry<Integer, Integer> i : existingMap.entrySet()) {
                newMap.put(i.getKey(), i.getValue());
                existing.add(i.getKey());
                totalAdded++;
            }
            
            for (int i = 0; i < enchants.length; i++) {
                //If we are going to add this enchantment, then let's do it
                if (rand.nextInt(100) < chance) {
                    //If we do not have room this enchantment, let's remove one
                    if (totalAdded >= max) {
                        int id = existing.get(rand.nextInt(existing.size()));
                        newMap.remove(id); //Remove the enchantment at this id
                    }
                    
                    Enchantment enchant = Enchantment.enchantmentsList[enchants[i]];
                    //Adjust the level for this enchantment
                    int levelToAdd = getLevel(type, material, binding, levels[i]);
                    int levelExisting = newMap.get(enchant.effectId) == null? 0: newMap.get(enchant.effectId); //The current level
                    if (levelExisting != 0 && levelExisting < enchant.getMaxLevel() && rand.nextInt(1 + 100 - chance) == 0) { //Attempt to increase the level
                        levelExisting = getLevel(type, material, binding, levelExisting + levelToAdd);
                    } else if (levelExisting == 0) levelExisting = levelToAdd;
                    
                    newMap.put(enchant.effectId, levelExisting);
                    totalAdded++;
                }
            }
            
            //Now that we have built the map, let's add it to the jewelry
            for (Entry<Integer, Integer> i : newMap.entrySet()) {
                result.addEnchantment(Enchantment.enchantmentsList[i.getKey()], i.getValue());
            }
        }

        return result;
    }

    public static boolean match(ItemStack result, ItemStack stack) {
        if (result.stackTagCompound == null || stack.stackTagCompound == null) return false;
        ItemStack check1 = NBTHelper.getItemStackFromNBT(result.stackTagCompound.getCompoundTag("WorkedItem"));
        ItemStack check2 = NBTHelper.getItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
        if (check1 == null || check1.getItem() == null || check2 == null || check2.getItem() == null) return false;
        return getBinding(check1) == getBinding(check2) && getMaterial(check1) == getMaterial(check2) && getType(check1) == getType(check2);
    }
}
