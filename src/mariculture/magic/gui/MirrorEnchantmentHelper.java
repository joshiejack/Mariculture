package mariculture.magic.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.parts.JewelryPart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class MirrorEnchantmentHelper {
	public static List buildEnchantmentList(Random par0Random, ItemStack stack, int par2, ItemStack mirror) {
		Item item = stack.getItem();
		int j = item.getItemEnchantability();

		if (stack.hasTagCompound()) {
			int id1 = stack.stackTagCompound.getInteger("Part1");
			int id2 = stack.stackTagCompound.getInteger("Part2");
			j = j + JewelryPart.materialList.get(id1).getEnchantability()
					+ (JewelryPart.materialList.get(id2).getEnchantability() / 2);
		}
		
		j += EnchantHelper.getLevel(Magic.luck, mirror) * 4;
		
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
			Map map = EnchantmentHelper.mapEnchantmentData(l, stack);

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
}
