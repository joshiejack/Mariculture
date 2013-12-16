package mariculture.api.core;

import net.minecraft.util.Icon;

public interface IItemUpgrade {
	/** Return how much heat this upgrade gives can be negative if it cools instead of heats **/
	public int getTemperature(int meta);

	/** Return how much 'storage count' this counts as **/
	public int getStorageCount(int meta);

	/** Return how many 'purity points' this upgrade gives **/
	public int getPurity(int meta);

	/** The type of upgrade this is, used when checking if a machine has this type in it,
	 * Currently used values are:
	 * storage, heating, cooling, purity, impurity, male, female, ethereal, debugKill, debugLive **/
	public String getType(int meta);
}
