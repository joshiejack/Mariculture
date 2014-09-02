package joshie.mariculture.api.fishery;

import net.minecraft.item.ItemStack;

public interface IFishFoodHandler {
    /**
     * Add something as a fish food
     * 
     * @param The ItemStack you want to add
     * @param How much each piece provides (Example Values: Ant: 1, Worm: 2,
     *            Fish Meal: 8)
     */
    public void addFishFood(ItemStack food, int value);
}
