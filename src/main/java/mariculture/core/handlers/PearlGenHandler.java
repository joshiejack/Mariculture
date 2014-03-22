package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;

public class PearlGenHandler {
	private static ArrayList<GeneratedPearls> pearls = new ArrayList<GeneratedPearls>();

	public static void addPearl(ItemStack item, int rarity) {
		addPearl(item, rarity, 1, 1);
	}

	private static float addPearl(ItemStack item, int rarity, int minCount, int maxCount) {
		for (GeneratedPearls loot : pearls) {
			if (loot.equals(item, minCount, maxCount)) {
				return loot.itemWeight += rarity;
			}
		}

		pearls.add(new GeneratedPearls(rarity, item, minCount, maxCount));
		return rarity;
	}

	public static ItemStack getRandomPearl(Random rand) {
		GeneratedPearls ret = (GeneratedPearls) WeightedRandom.getRandomItem(rand, pearls);
		if (ret != null) {
			return ret.generateStack(rand);
		}
		return null;
	}

	private static class GeneratedPearls extends WeightedRandomChestContent {
		private ItemStack itemStack;
		private int minCount = 1;
		private int maxCount = 1;

		private GeneratedPearls(int weight, ItemStack item, int min, int max) {
			super(item, weight, max, max);
			this.itemStack = item;
			minCount = min;
			maxCount = max;
		}

		private ItemStack generateStack(Random rand) {
			ItemStack ret = this.itemStack.copy();
			ret.stackSize = minCount + (rand.nextInt(maxCount - minCount + 1));

			return ret;
		}

		private boolean equals(ItemStack item, int min, int max) {
			return (min == minCount && max == maxCount && item.isItemEqual(this.itemStack));
		}

		public boolean equals(ItemStack item) {
			return item.isItemEqual(this.itemStack);
		}
	}
}
