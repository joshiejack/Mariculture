package mariculture.core.helpers;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import mariculture.api.core.MaricultureHandlers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantHelper {
    public static boolean exists(Enchantment enchant) {
        if (enchant == null) return false;

        return enchant.effectId > 0;
    }

    //Player Based Methods
    public static int getEnchantStrength(Enchantment enchant, EntityPlayer player) {
        if (!exists(enchant)) return 0;

        return MaricultureHandlers.mirror.getEnchantmentStrength(player, enchant.effectId);
    }

    public static boolean hasEnchantment(Enchantment enchant, EntityPlayer player) {
        return getEnchantStrength(enchant, player) > 0;
    }

    //Damage Mirror Items
    public static void damageItems(Enchantment enchant, EntityPlayer player, int dmg) {
        if (!exists(enchant) || player.capabilities.isCreativeMode) return;

        MaricultureHandlers.mirror.damageItemsWithEnchantment(player, enchant.effectId, dmg);
    }

    //Item Based Methods
    public static int getLevel(Enchantment enchant, ItemStack stack) {
        if (!exists(enchant)) return 0;

        if (isBroken(stack)) return 0;

        return EnchantmentHelper.getEnchantmentLevel(enchant.effectId, stack);
    }

    public static int getLevel(int id, ItemStack stack) {
        return getLevel(getEnchant(id), stack);
    }

    //Has Based Helper
    public static boolean hasEnchantment(Enchantment enchant, ItemStack stack) {
        return getLevel(enchant, stack) > 0;
    }

    public static boolean hasEnchantment(int id, ItemStack stack) {
        return hasEnchantment(getEnchant(id), stack);
    }

    public static Enchantment getEnchant(int id) {
        return Enchantment.enchantmentsList[id];
    }

    //Whether the item is broken
    public static boolean isBroken(ItemStack stack) {
        return stack != null && stack.getItemDamage() >= stack.getMaxDamage();
    }
    
    public static int getEnchantmentValue(ItemStack stack) {
        //Setup the Maximum Weight
        if (MAX_WEIGHT == 0) setMax();
        int volume = 0;
        LinkedHashMap<Integer, Integer> maps = (LinkedHashMap<Integer, Integer>) EnchantmentHelper.getEnchantments(stack);
        for (Entry<Integer, Integer> i : maps.entrySet()) {
            Enchantment enchant = Enchantment.enchantmentsList[i.getKey()];
            volume += ((i.getValue() * ((MAX_WEIGHT + 1) - (enchant.getWeight()))));
        }

        return (int) Math.max(1, ((((double) volume / (double) MAX_WEIGHT)) * 5) * 5);
    }

    public static int MAX_WEIGHT = 0;

    public static void setMax() {
        for (Enchantment enchant : Enchantment.enchantmentsList) {
            if (enchant != null) {
                if (enchant.getWeight() > MAX_WEIGHT) {
                    MAX_WEIGHT = enchant.getWeight();
                }
            }
        }
    }
}
