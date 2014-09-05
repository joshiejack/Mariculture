package joshie.mariculture.api.core;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class CoralRegistry {
    private static ArrayList<ItemStack> corals = new ArrayList();

    public static void register(ItemStack stack) {
        corals.add(stack);
    }
}
