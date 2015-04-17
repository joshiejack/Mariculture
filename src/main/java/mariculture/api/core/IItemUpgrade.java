package mariculture.api.core;

import net.minecraft.item.ItemStack;

public interface IItemUpgrade {
    /** Return how much heat this upgrade gives can be negative if it cools instead of heats **/
    public int getTemperature(ItemStack stack);

    /** Return how much 'storage count' this counts as **/
    public int getStorageCount(ItemStack stack);

    /** Return how many 'purity points' this upgrade gives **/
    public int getPurity(ItemStack stack);

    /** return how much speed this upgrade gives **/
    public int getSpeed(ItemStack stack);

    /** return 'rf count' boost **/
    public int getRFBoost(ItemStack stack);

    /** return salinity boost **/
    public int getSalinity(ItemStack stack);

    /** The type of upgrade this is, used when checking if a machine has this type in it,
     * Currently used values are:
     * storage, heating, cooling, purity, impurity, male, female, ethereal, debugKill, debugLive **/
    public String getType(ItemStack stack);

}
