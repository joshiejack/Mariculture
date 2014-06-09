package mariculture.api.core;

import java.util.Map;
import java.util.TreeMap;

import net.minecraft.item.ItemStack;

public class MaricultureRegistry {
    private static final Map<String, ItemStack> itemRegistry = new TreeMap<String, ItemStack>();

    public static ItemStack get(String name) {

        ItemStack result = itemRegistry.get(name);
        if (result != null) {
            result = result.copy();
        }

        return result;
    }

    public static void register(String name, ItemStack stack) {
        itemRegistry.put(name, stack);
    }

    public static int size() {
        return itemRegistry.size();
    }

    public static Map<String, ItemStack> getRegistry() {
        return itemRegistry;
    }
}
