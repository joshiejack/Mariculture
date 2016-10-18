package joshie.mariculture.core.helpers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import joshie.mariculture.core.util.holder.StackHolder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class InventoryHelper {
    private static final Cache<StackHolder, List<String>> ORE_NAME_CACHE = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.MINUTES).build();
    private static final List<String> EMPTY_LIST = new ArrayList<>();

    public static boolean oreStartsWith(ItemStack stack, String starts) {
        for (String ore: getOreNames(stack)) {
            if (ore.startsWith(starts)) return true;
        }

        return false;
    }

    public static List<String> getOreNames(ItemStack stack) {
        try {
            return ORE_NAME_CACHE.get(StackHolder.of(stack), () -> {
                List<String> list = new ArrayList<>();
                for (int i: OreDictionary.getOreIDs(stack)) {
                    list.add(OreDictionary.getOreName(i));
                }

                return list;
            });
        } catch (ExecutionException e) { return EMPTY_LIST; }
    }
}
