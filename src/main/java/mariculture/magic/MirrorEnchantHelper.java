package mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mariculture.core.util.Rand;
import mariculture.magic.enchantments.EnchantmentJewelry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class MirrorEnchantHelper {
    public static List buildEnchantmentList(Random rand, ItemStack stack, int level) {
        int chance = 75 - level;
        if (chance <= 1) {
            chance = 1;
        }
        Item item = stack.getItem();
        int j = item.getItemEnchantability();

        if (j <= 0) return null;
        else {
            j /= 2;
            j = 1 + rand.nextInt((j >> 1) + 1) + rand.nextInt((j >> 1) + 1);
            int k = j + level;
            float f = (rand.nextFloat() + rand.nextFloat() - 1.0F) * 0.15F;
            int l = (int) (k * (1.0F + f) + 0.5F);

            if (l < 1) {
                l = 1;
            }

            ArrayList arraylist = null;
            Map map = mapEnchantmentData(l, level, stack);
            if (map != null && !map.isEmpty()) {
                EnchantmentData enchantmentdata = (EnchantmentData) WeightedRandom.getRandomItem(rand, map.values());
                if (enchantmentdata != null) {
                    arraylist = new ArrayList();
                    arraylist.add(enchantmentdata);

                    for (int i1 = l; rand.nextInt(chance) <= i1; i1 >>= 1) {
                        Iterator iterator = map.keySet().iterator();

                        while (iterator.hasNext()) {
                            Integer integer = (Integer) iterator.next();
                            boolean flag = true;
                            Iterator iterator1 = arraylist.iterator();

                            while (true) {
                                if (iterator1.hasNext()) {
                                    EnchantmentData enchantmentdata1 = (EnchantmentData) iterator1.next();
                                    if (enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[integer.intValue()])) {
                                        continue;
                                    }

                                    flag = false;
                                }

                                if (!flag) {
                                    iterator.remove();
                                }

                                break;
                            }
                        }

                        if (!map.isEmpty()) {
                            EnchantmentData enchantmentdata2 = (EnchantmentData) WeightedRandom.getRandomItem(rand, map.values());
                            arraylist.add(enchantmentdata2);
                        }
                    }
                }
            }

            return arraylist;
        }
    }

    public static Map mapEnchantmentData(int enchantability, int orig, ItemStack stack) {
        Item item = stack.getItem();
        HashMap hashmap = null;
        boolean flag = stack.getItem() == Items.book;
        Enchantment[] aenchantment = Enchantment.enchantmentsList;
        int j = aenchantment.length;

        for (int k = 0; k < j; ++k) {
            Enchantment enchantment = aenchantment[k];
            if (enchantment == null) {
                continue;
            }
            if (enchantment.canApplyAtEnchantingTable(stack) || item == Items.book && enchantment.isAllowedOnBooks()) {
                for (int l = enchantment.getMinLevel(); l <= enchantment.getMaxLevel(); ++l)
                    if (enchantment instanceof EnchantmentJewelry && enchantability <= enchantment.getMaxEnchantability(l) || !(enchantment instanceof EnchantmentJewelry)) if (enchantability >= enchantment.getMinEnchantability(l)) {
                        if (hashmap == null) {
                            hashmap = new HashMap();
                        }

                        if (orig > 30) if (enchantment != Enchantment.protection && enchantment.getMaxLevel() != 1 && !(enchantment instanceof EnchantmentJewelry)) {
                            int chanceDoubled = orig > 55 ? 2 : 1;
                            for (int i = 0; i < chanceDoubled; i++)
                                if (Rand.rand.nextInt() < orig) {
                                    l += 1;
                                }
                        }

                        hashmap.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, l));
                    }
            }
        }

        return hashmap;
    }
}
