package mariculture.core.helpers;

import net.minecraftforge.common.config.Configuration;

public class ConfigHelper {
    private static Configuration config;
    private static String category;

    public static void setConfig(Configuration cf) {
        config = cf;
    }

    public static void setCategory(String cat) {
        category = cat;
    }

    public static void setCategory(String cat, String comment) {
        category = cat;
        config.addCustomCategoryComment(cat, comment);
    }

    public static boolean getBoolean(String name, boolean dft, String comment) {
        return config.get(category, name, dft, comment).getBoolean(dft);
    }

    public static boolean getBoolean(String name, boolean dft) {
        return config.get(category, name, dft).getBoolean(dft);
    }

    public static double getDouble(String name, double dft, String comment) {
        return config.get(category, name, dft, comment).getDouble(dft);
    }

    public static double getDouble(String name, double dft) {
        return config.get(category, name, dft).getDouble(dft);
    }

    public static int getInt(String name, int dft, String comment) {
        return config.get(category, name, dft, comment).getInt(dft);
    }

    public static int getInt(String name, int dft) {
        return config.get(category, name, dft).getInt(dft);
    }

    public static String[] getStringList(String name, String[] dft, String comment) {
        return config.get(category, name, dft, comment).getStringList();
    }

    public static String[] getStringList(String name, String[] dft) {
        return config.get(category, name, dft).getStringList();
    }
}
