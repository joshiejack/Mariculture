package joshie.mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class OreDicHelper {
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

    public static boolean matches(String entry, ItemStack input) {
        int[] ids = OreDictionary.getOreIDs(input);
        for(int i: ids) {
            if(OreDictionary.getOreName(i).equals(entry)) {
                return true;
            }
        }
        
        return false;
    }
}
