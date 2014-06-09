package mariculture.core.helpers;

import java.util.ArrayList;

import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class OreDicHelper {
    public static boolean isWhitelisted(ItemStack stack) {
        if (isInDictionary(stack)) {
            String ore = getDictionaryName(stack);
            if (mariculture.core.config.AutoDictionary.ENABLE_WHITELIST) {
                for (String element : mariculture.core.config.AutoDictionary.WHITELIST)
                    if (ore.startsWith(element)) return true;

                return false;
            } else {
                for (String element : mariculture.core.config.AutoDictionary.BLACKLIST)
                    if (ore.equals(element)) return false;

                return true;
            }
        }

        return false;
    }

    public static boolean isInDictionary(ItemStack stack) {
        if (stack.getItem() instanceof ItemFishFood) return false;

        if (OreDictionary.getOreID(stack) >= 0) return true;

        return false;
    }

    public static String getDictionaryName(ItemStack stack) {
        return OreDictionary.getOreName(OreDictionary.getOreID(stack));
    }

    public static String convert(Object object) {
        String name = "";

        ItemStack stack = null;
        Fluid fluid = null;

        if (object instanceof Block) {
            stack = new ItemStack((Block) object);
        }
        if (object instanceof Item) {
            stack = new ItemStack((Item) object);
        }
        if (object instanceof ItemStack) {
            stack = (ItemStack) object;
        }
        if (stack != null) if (isInDictionary(stack)) {
            name = getDictionaryName(stack);
        } else if (stack.isItemStackDamageable()) {
            name = Item.itemRegistry.getNameForObject(stack.getItem()) + "|ignore";
        } else {
            name = Item.itemRegistry.getNameForObject(stack.getItem()) + "|" + stack.getItemDamage();
        }

        if (object instanceof String) {
            name = (String) object;
        }

        return name;
    }

    public static void add(String name, ItemStack stack) {
        if (OreDictionary.getOres(name).size() < 1) {
            OreDictionary.registerOre(name, stack);
        }
    }

    public static ItemStack getNextValidEntry(ItemStack stack) {
        return getNextOreDicEntry(stack, true);
    }

    public static ItemStack getNextEntry(ItemStack stack) {
        return getNextOreDicEntry(stack, false);
    }

    public static ItemStack getNextOreDicEntry(ItemStack stack, boolean checkWhitelist) {
        if (OreDicHelper.isInDictionary(stack)) if (OreDicHelper.isWhitelisted(stack) && checkWhitelist || !checkWhitelist) {
            String name = OreDicHelper.getDictionaryName(stack);
            int id = getOrePos(stack);
            if (OreDictionary.getOres(name).size() > 0) {
                id++;

                if (id >= OreDictionary.getOres(name).size()) {
                    id = 0;
                }
            }

            stack = OreDictionary.getOres(OreDicHelper.getDictionaryName(stack)).get(id).copy();

            if (!checkWhitelist) if (stack.getItem() == Item.getItemFromBlock(Blocks.planks) || stack.getItem() == Item.getItemFromBlock(Blocks.wooden_slab) || stack.getItem() == Item.getItemFromBlock(Blocks.log) || stack.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                stack.setItemDamage(Rand.rand.nextInt(4));
            }

            return stack;
        }

        return stack;
    }

    private static int getOrePos(ItemStack input) {
        int id = 0;
        String name = OreDicHelper.getDictionaryName(input);
        ArrayList<ItemStack> ores = OreDictionary.getOres(name);
        for (ItemStack item : ores) {
            if (OreDictionary.itemMatches(item, input, true)) return id;

            id++;
        }

        return id;
    }
}
