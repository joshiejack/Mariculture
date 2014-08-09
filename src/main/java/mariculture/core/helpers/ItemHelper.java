package mariculture.core.helpers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHelper {
    public static boolean areEqual(ItemStack stack, ItemStack stack2) {
        if (stack == null || stack2 == null) return false;
        else if (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) return stack.getItem() == stack2.getItem();
        else return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
    }

    public static String getName(ItemStack stack) {
        try {
            return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
        } catch (Exception e) {
            try {
                return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + OreDictionary.WILDCARD_VALUE;
            } catch (Exception e2) {
                return "";
            }
        }
    }

    public static boolean areEqual(ItemStack ingredient, Object input) {
        if (input instanceof ItemStack) return areEqual(ingredient, (ItemStack) input);
        else if (input instanceof String) return OreDicHelper.matches((String) input, ingredient);
        else return false;
    }
}
