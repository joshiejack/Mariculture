package mariculture.magic;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
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

    public static ItemStack finishJewelry(ItemStack stack, ItemStack result, Random rand) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("EnchantmentList") && stack.stackTagCompound.hasKey("EnchantmentLevels")) {
            JewelryType type = getType(result);
            JewelryMaterial material = getMaterial(result);
            JewelryBinding binding = getBinding(result);
            int chance = binding.getKeepEnchantmentChance(type);
            int[] enchants = stack.stackTagCompound.getIntArray("EnchantmentList");
            int[] levels = stack.stackTagCompound.getIntArray("EnchantmentLevels");
            int total = 0;
            for (int j = 0; j < enchants.length; j++)
                if (rand.nextInt(100) < chance) if (total < type.getMaximumEnchantments() + material.getExtraEnchantments(type)) {
                    total++;
                    //Add the enchantment after increasing the enchants added
                    Enchantment enchant = Enchantment.enchantmentsList[enchants[j]];
                    int level = getLevel(type, material, binding, levels[j]);
                    int current = EnchantmentHelper.getEnchantmentLevel(enchant.effectId, result);
                    if (current < enchant.getMaxLevel()) {
                        int newLevel = getLevel(type, material, binding, current + level);
                        if (current != 0) {
                            //Not guaranteed to increase the level, but there's a chance to
                            if (rand.nextInt(1 + 100 - chance) == 0) {
                                NBTTagList tagList = result.stackTagCompound.getTagList("ench", 10);
                                for (int i = 0; i < tagList.tagCount(); i++) {
                                    NBTTagCompound tag = tagList.getCompoundTagAt(i);
                                    int id = tag.getShort("id");
                                    if (id == enchant.effectId) {
                                        tag.setShort("lvl", (short) newLevel);
                                        tagList.removeTag(i);
                                        tagList.appendTag(tag);
                                    }
                                }
                            }
                        } else {
                            result.addEnchantment(enchant, level);
                        }
                    }
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
