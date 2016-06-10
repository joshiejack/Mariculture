package joshie.mariculture.modules.fishery;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import joshie.mariculture.api.fishing.Fishing;
import joshie.mariculture.helpers.GroupHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static joshie.mariculture.api.fishing.Fishing.Salinity.BRACKISH;
import static joshie.mariculture.api.fishing.Fishing.Salinity.SALINE;
import static net.minecraftforge.common.BiomeDictionary.Type.BEACH;
import static net.minecraftforge.common.BiomeDictionary.Type.MUSHROOM;
import static net.minecraftforge.common.BiomeDictionary.Type.OCEAN;

public class FishingAPI implements Fishing {
    private final EnumMap<Type, Salinity> salinityBestGuess = new EnumMap(Type.class);
    private final Cache<Biome, Salinity> salinityCache = CacheBuilder.newBuilder().build();
    private final HashMap<Biome, Salinity> salinityRegistry = new HashMap<>();
    private final TObjectIntMap<Item> strengthRegistry = new TObjectIntHashMap<>();

    public FishingAPI() {
        salinityBestGuess.put(OCEAN, SALINE);
        salinityBestGuess.put(MUSHROOM, BRACKISH);
        salinityBestGuess.put(BEACH, BRACKISH);
    }

    @Override
    public int getFishingRodStrength(ItemStack stack) {
        return strengthRegistry.containsKey(stack.getItem()) ? strengthRegistry.get(stack.getItem()) : 0;
    }

    @Override
    public void registerBiomeAsSalinity(Biome biome, Salinity salinity) {
        salinityRegistry.put(biome, salinity);
    }

    @Override
    public Salinity getSalinityForBiome(Biome biome) {
        try {
            return salinityCache.get(biome, new Callable<Salinity>() {
                @Override
                public Salinity call() throws Exception {
                    Salinity salinity = salinityRegistry.get(biome);
                    if (salinity != null) return salinity;

                    Type[] types = BiomeDictionary.getTypesForBiome(biome);
                    int[] values = new int[types.length];
                    for (int i = 0; i < types.length; i++) {
                        values[i] = salinityBestGuess.get(types[i]).ordinal();
                    }

                    return Salinity.values()[GroupHelper.getMostPopular(values)];
                }
            });
        } catch (Exception e) { return Salinity.FRESH; }
    }
}
