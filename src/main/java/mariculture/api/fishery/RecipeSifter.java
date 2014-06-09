package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

public class RecipeSifter {
    public String name;
    public ItemStack bait;
    public ItemStack block;
    public int minCount, maxCount, chance;

    public RecipeSifter(ItemStack bait, ItemStack block, int min, int max, int chance) {
        this.bait = bait;
        this.block = block;
        minCount = min;
        maxCount = max;
        this.chance = chance;
    }

    public RecipeSifter(ItemStack bait, String name, int min, int max, int chance) {
        this.bait = bait;
        this.name = name;
        minCount = min;
        maxCount = max;
        this.chance = chance;
    }
}
