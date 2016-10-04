package joshie.mariculture.modules.fishery;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import joshie.mariculture.api.fishing.Fishing;
import joshie.mariculture.api.fishing.rod.FishingRod;
import joshie.mariculture.core.helpers.GroupHelper;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import joshie.mariculture.core.util.holder.StackHolder;
import joshie.mariculture.core.util.holder.StackNBTHolder;
import joshie.mariculture.modules.fishery.utils.FishingRodHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static joshie.mariculture.api.fishing.Fishing.Salinity.BRACKISH;
import static joshie.mariculture.api.fishing.Fishing.Salinity.SALINE;
import static joshie.mariculture.api.fishing.rod.FishingRod.NULL_FISHING_ROD;
import static joshie.mariculture.modules.fishery.Fishery.FISHING_ROD;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

@MCApiImpl("fishery")
public class FishingAPI implements Fishing {
    @SuppressWarnings("unused")
    public static final FishingAPI INSTANCE = new FishingAPI();

    private final EnumMap<Type, Salinity> salinityBestGuess = new EnumMap<>(Type.class);
    private final Cache<Biome, Salinity> salinityCache = CacheBuilder.newBuilder().build();
    private final HashMap<Biome, Salinity> salinityRegistry = new HashMap<>();
    private final TObjectIntMap<Item> strengthRegistry = new TObjectIntHashMap<>();

    private final HashMap<StackHolder, ResourceLocation> baitRegistry = new HashMap<>();
    private final Cache<StackHolder, FishingRod> rodCache = CacheBuilder.newBuilder().maximumSize(64).build();
    private final HashMap<StackHolder, FishingRod> rodRegistry = new HashMap<>();

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
    public void registerBait(ResourceLocation resource, ItemStack stack) {
        if (!LootTableList.getAll().contains(resource)) {
            LootTableList.register(resource);
        }

        baitRegistry.put(StackHolder.of(stack), resource);
    }

    @Override
    public ResourceLocation getLootTableFromBait(ItemStack stack) {
        if (stack == null) return LootTableList.GAMEPLAY_FISHING;
        ResourceLocation result = baitRegistry.get(StackHolder.of(stack));
        return result == null ? LootTableList.GAMEPLAY_FISHING : result;
    }

    @Override
    public Salinity getSalinityForBiome(Biome biome) {
        try {
            return salinityCache.get(biome, () -> {
                Salinity salinity = salinityRegistry.get(biome);
                if (salinity != null) return salinity;

                Type[] types = BiomeDictionary.getTypesForBiome(biome);
                int[] values = new int[types.length];
                for (int i = 0; i < types.length; i++) {
                    values[i] = salinityBestGuess.get(types[i]).ordinal();
                }

                return Salinity.values()[GroupHelper.getMostPopular(values)];
            });
        } catch (Exception e) { return Salinity.FRESH; }
    }

    @Nullable
    @Override
    public FishingRod getFishingRodFromStack(ItemStack stack) {
        if (stack.getItem() == FISHING_ROD) {
            try {
                FishingRod rod = stack.hasTagCompound() ? rodCache.get(StackNBTHolder.of(stack), () -> FishingRodHelper.getFishingRod(stack)) : null;
                return rod == null || rod == NULL_FISHING_ROD ? null : rod;
            } catch (ExecutionException e) { return null; }
        }

        return rodRegistry.get(StackHolder.of(stack));
    }
}
