package joshie.mariculture.helpers;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.config.Configuration;

public class ConfigHelper {
    private static Configuration config;
    private static String category;

    public static Configuration setConfig(Configuration config) {
        ConfigHelper.config = config;
        category = "Settings"; //Default
        return config;
    }

    public static void setCategory(String name) {
        category = name;
    }

    public static IBlockState getBlockState(String name, IBlockState default_) {
        String defaultString = StateHelper.getStringFromState(default_);
        String ret = config.get(category, name, defaultString).getString();
        return StateHelper.getStateFromString(ret);
    }

    public static boolean getBoolean(String name, boolean default_) {
        return config.get(category, name, default_).getBoolean();
    }

    public static int getInteger(String name, int default_) {
        return config.get(category, name, default_).getInt();
    }

    public static double getDouble(String name, double default_) {
        return config.get(category, name, default_).getDouble();
    }
}
