package mariculture.api.fishery;

import java.util.Random;

import net.minecraft.item.ItemStack;

public interface IBaitHandler {
	/**
	 * @param Bait
	 *            : Item
	 * @param Effectiveness
	 *            : How good the bait is, (Minnow = 5, Worm = 3, Maggot/Hopper =
	 *            2, Bread = 1, Ant = 0) Higher = Better Do not add any higher
	 *            than 5 or there will be errors
	 * @param Object
	 *            : List of blocks that give this bait with the rarity (Rarity
	 *            max = 25  = Higher = More common then the minimum and maximum
	 *            of that bait that block gives, See below for usage example, 25 = Guaranteed
	 **/

	/*
	 * Example: addBait(new ItemStack(Item.fishRaw), 1, new Object[] { new
	 * ItemStack(Block.sand), 25, 2, 3, new ItemStack(Block.dirt), 5, 1, 2, new
	 * ItemStack(Block.wood), 2, 1, 1 });
	 */

	public void addBait(ItemStack bait, int effectiveness, Object... params);

	/**
	 * @param random
	 * @param Input
	 *            stack
	 * @return returns a random bait object that is a valid output for the input
	 *         stack, returns null if none valid
	 */
	public ItemStack getBaitForStack(Random random, ItemStack item);

	/** Returns the effectiveness for this bait item **/
	public int getEffectiveness(ItemStack stack);
}
