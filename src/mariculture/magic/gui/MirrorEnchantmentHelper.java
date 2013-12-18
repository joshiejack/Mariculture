package mariculture.magic.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mariculture.magic.jewelry.parts.JewelryPart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class MirrorEnchantmentHelper {
	/**
	 * Create a list of random EnchantmentData (enchantments) that can be added
	 * together to the ItemStack, the 3rd parameter is the total enchantability
	 * level.
	 */
	public static List buildEnchantmentList(Random par0Random, ItemStack stack, int par2) {
		Item item = stack.getItem();
		int j = item.getItemEnchantability();

		if (stack.hasTagCompound()) {
			int id1 = stack.stackTagCompound.getInteger("Part1");
			int id2 = stack.stackTagCompound.getInteger("Part2");
			j = j + JewelryPart.materialList.get(id1).getEnchantability()
					+ (JewelryPart.materialList.get(id2).getEnchantability() / 2);
		}
		
		if (j <= 0) {
			return null;
		} else {
			j /= 2;
			j = 1 + par0Random.nextInt((j >> 1) + 1) + par0Random.nextInt((j >> 1) + 1);
			int k = j + par2;
			float f = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
			int l = (int) ((float) k * (1.0F + f) + 0.5F);

			if (l < 1) {
				l = 1;
			}
	
			ArrayList arraylist = null;
			Map map = mapEnchantmentData(l, stack);

			if (map != null && !map.isEmpty()) {
				EnchantmentData enchantmentdata = (EnchantmentData) WeightedRandom.getRandomItem(par0Random, map.values());

				if (enchantmentdata != null) {
					arraylist = new ArrayList();
					arraylist.add(enchantmentdata);

					for (int i1 = l; par0Random.nextInt(50) <= i1; i1 >>= 1) {
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
							EnchantmentData enchantmentdata2 = (EnchantmentData) WeightedRandom.getRandomItem(par0Random,
									map.values());
							arraylist.add(enchantmentdata2);
						}
					}
				}
			}

			return arraylist;
		}
	}

	/**
	 * Creates a 'Map' of EnchantmentData (enchantments) possible to add on the
	 * ItemStack and the enchantability level passed.
	 */
	public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack) {
		Item item = par1ItemStack.getItem();
		HashMap hashmap = null;
		boolean flag = par1ItemStack.itemID == Item.book.itemID;
		Enchantment[] aenchantment = Enchantment.enchantmentsList;
		int j = aenchantment.length;

		for (int k = 0; k < j; ++k) {
			Enchantment enchantment = aenchantment[k];

			if (enchantment == null)
				continue;

			flag = (par1ItemStack.itemID == Item.book.itemID) && enchantment.isAllowedOnBooks();
			if (enchantment.canApplyAtEnchantingTable(par1ItemStack) || flag) {
				for (int l = enchantment.getMinLevel(); l <= enchantment.getMaxLevel(); ++l) {
					if (par0 >= enchantment.getMinEnchantability(l) && par0 <= enchantment.getMaxEnchantability(l)) {
						if (hashmap == null) {
							hashmap = new HashMap();
						}

						hashmap.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, l));
					}
				}
			}
		}

		return hashmap;
	}
}
