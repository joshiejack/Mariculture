package joshie.mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class StateHelper {
    public static IBlockState getStateFromString(String name) {
        String[] split = name.split(" ");
        String block = split[0];
        int meta = split.length > 1 ? getIntegerFromString(split[1]): 0;
        Block theBlock = Block.REGISTRY.getObject(new ResourceLocation(block));
        return theBlock.getStateFromMeta(meta);
    }

    public static String getStringFromState(IBlockState state) {
        String name = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
        int meta = state.getBlock().getMetaFromState(state);
        return name + " " + meta;
    }

    private static int getIntegerFromString(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
