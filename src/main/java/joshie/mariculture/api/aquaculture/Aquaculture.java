package joshie.mariculture.api.aquaculture;

import net.minecraft.item.ItemStack;

public interface Aquaculture {
    /** Register a new item as sand
     *  @param stack the item that counts as sand
     *  @param texture the block texture to use in the format "minecraft:blocks/sand" */
    void registerSand(ItemStack stack, String texture);
}
