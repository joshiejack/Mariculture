package joshie.mariculture.helpers;

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

    public static int getInteger(String name, int default_) {
        return config.get(category, name, default_).getInt();
    }
}
