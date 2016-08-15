package joshie.mariculture.modules.aquaculture;

import joshie.mariculture.core.util.StackHolder;
import joshie.mariculture.modules.EventAPIContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

@EventAPIContainer(modules = "aquaculture")
public class AquacultureAPI implements joshie.mariculture.api.aquaculture.Aquaculture {
    private static final Set<StackHolder> SANDS = new HashSet<>();

    public AquacultureAPI() {
        registerSand(new ItemStack(Blocks.SAND, 1, 0));
        registerSand(new ItemStack(Blocks.SAND, 1, 1));
    }

    public static boolean isSand(ItemStack stack) {
        return SANDS.contains(StackHolder.of(stack));
    }

    @Override
    public void registerSand(ItemStack stack) {
        AquacultureAPI.SANDS.add(StackHolder.of(stack));
    }
}
