package mariculture.fishery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mariculture.api.fishery.IBaitHandler;
import mariculture.core.helpers.DictionaryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomItem;

public class BaitHandler implements IBaitHandler {
	private static ArrayList<BaitLoot> baitLoot = new ArrayList<BaitLoot>();
	private final Map effectivenessList = new HashMap();
	private final Map blockList = new HashMap();

	@Override
	public void addBait(ItemStack bait, int effectiveness, Object... params) {
		for (int i = 0; i < (params.length - 3); i++) {
			if (params[i] instanceof ItemStack && params[i + 1] instanceof Integer && params[i + 2] instanceof Integer
					&& params[i + 3] instanceof Integer) {
				ItemStack input = (ItemStack) params[i];
				int rarity = Integer.parseInt(params[i + 1].toString());
				rarity = (rarity > 25) ? 25 : rarity;
				int min = Integer.parseInt(params[i + 2].toString());
				int max = Integer.parseInt(params[i + 3].toString());

				if (!DictionaryHelper.isInDictionary(input)) {
					addOtherBlock(bait, rarity, min, max, input);
					blockList.put(Arrays.asList(input.itemID, input.getItemDamage()), true);
				} else {
					addBlock(bait, rarity, min, max, DictionaryHelper.getDictionaryName(input));
					blockList.put(DictionaryHelper.getDictionaryName(input), true);
				}
			}
		}

		effectivenessList.put(Arrays.asList(bait.itemID, bait.getItemDamage()), effectiveness);
	}

	@Override
	public int getEffectiveness(ItemStack stack) {
		if ((Integer) effectivenessList.get(Arrays.asList(stack.itemID, stack.getItemDamage())) != null) {
			return (Integer) effectivenessList.get(Arrays.asList(stack.itemID, stack.getItemDamage()));
		}

		return -1;
	}

	public void addOtherBlock(ItemStack output, int rarity, int minCount, int maxCount, ItemStack input) {
		baitLoot.add(new BaitLoot(rarity, output, minCount, maxCount, input));
	}

	public void addBlock(ItemStack output, int rarity, int minCount, int maxCount, String input) {
		baitLoot.add(new BaitLoot(rarity, output, minCount, maxCount, input));
	}

	@Override
	public ItemStack getBaitForStack(Random rand, ItemStack stack) {
		BaitLoot ret = (BaitLoot) WeightedRandom.getRandomItem(rand, baitLoot);

		boolean hasBlock = false;

		if (DictionaryHelper.isInDictionary(stack)) {
			if (blockList.get(DictionaryHelper.getDictionaryName(stack)) != null) {
				hasBlock = true;
			}
		}

		if (hasBlock == false && blockList.get(Arrays.asList(stack.itemID, stack.getItemDamage())) != null) {
			hasBlock = true;
		}

		boolean foundSameBlock = false;

		int chance = 1;
		
		if (hasBlock) {
			while (!foundSameBlock) {
				ret = (BaitLoot) WeightedRandom.getRandomItem(rand, baitLoot);

				if (DictionaryHelper.isInDictionary(stack)) {
					if (ret.stackDictionary == DictionaryHelper.getDictionaryName(stack)) {
						foundSameBlock = true;
					}
				} else if (ret.stackInput != null && ret.stackInput.itemID == stack.itemID) {
					foundSameBlock = true;
				}

			}
		}

		if (foundSameBlock) {
			return ret.generateStack(rand);
		}

		return null;
	}

	public static class BaitLoot extends WeightedRandomItem {
		private ItemStack stackOutput;
		private int minCount;
		private int maxCount;
		private ItemStack stackInput;
		private String stackDictionary;

		public BaitLoot(int weight, ItemStack output, int min, int max, ItemStack input) {
			super(weight);
			this.stackOutput = output;
			minCount = min;
			maxCount = max;
			this.stackInput = input;
		}

		public BaitLoot(int weight, ItemStack output, int min, int max, String dictionary) {
			super(weight);
			this.stackOutput = output;
			minCount = min;
			maxCount = max;
			this.stackDictionary = dictionary;
		}

		public ItemStack generateStack(Random rand) {
			ItemStack ret = this.stackOutput.copy();
			ret.setTagCompound(new NBTTagCompound());
			ret.stackTagCompound.setInteger("Chance", this.itemWeight);
			ret.stackTagCompound.setInteger("Min", this.minCount);
			ret.stackTagCompound.setInteger("Max", this.maxCount);
			return ret;
		}

		public boolean equals(ItemStack output, int min, int max, ItemStack input) {
			return (min == minCount && max == maxCount && output.isItemEqual(this.stackOutput) && input
					.isItemEqual(this.stackInput));
		}

		public boolean isSame(ItemStack output, int min, int max, String input) {
			return (min == minCount && max == maxCount && output.isItemEqual(this.stackOutput) && input == this.stackDictionary);
		}
	}

	public void addDungeonLoot(BaitLoot loot) {
		baitLoot.add(loot);
	}

	public boolean removeDungeonLoot(BaitLoot loot) {
		return baitLoot.remove(loot);
	}
}