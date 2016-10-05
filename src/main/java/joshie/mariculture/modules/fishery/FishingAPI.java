package joshie.mariculture.modules.fishery;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import joshie.mariculture.api.fishing.Fishing;
import joshie.mariculture.core.helpers.GroupHelper;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import joshie.mariculture.core.util.holder.StackHolder;
import joshie.mariculture.core.util.holder.StackNBTHolder;
import joshie.mariculture.modules.fishery.rod.*;
import joshie.mariculture.modules.fishery.utils.FishingRodHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static joshie.mariculture.api.fishing.Fishing.Salinity.BRACKISH;
import static joshie.mariculture.api.fishing.Fishing.Salinity.SALINE;
import static joshie.mariculture.modules.fishery.Fishery.FISHING_ROD;
import static joshie.mariculture.modules.fishery.rod.FishingRod.NULL_FISHING_ROD;
import static net.minecraftforge.common.BiomeDictionary.Type.*;

@MCApiImpl("fishery")
public class FishingAPI implements Fishing {
    @SuppressWarnings("unused")
    public static final FishingAPI INSTANCE = new FishingAPI();

    private final EnumMap<Type, Salinity> salinityBestGuess = new EnumMap<>(Type.class);
    private final Cache<Biome, Salinity> salinityCache = CacheBuilder.newBuilder().build();
    private final HashMap<Biome, Salinity> salinityRegistry = new HashMap<>();
    private final HashMap<StackHolder, ResourceLocation> baitRegistry = new HashMap<>();
    private final Cache<StackHolder, FishingRod> rodCache = CacheBuilder.newBuilder().maximumSize(64).build();
    private final HashMap<StackHolder, FishingRod> rodRegistry = new HashMap<>();

    public FishingAPI() {
        salinityBestGuess.put(OCEAN, SALINE);
        salinityBestGuess.put(MUSHROOM, BRACKISH);
        salinityBestGuess.put(BEACH, BRACKISH);
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
    public RodComponent createPole(String name, int durability, int strength) {
        ResourceLocation resource = new ResourceLocation(Loader.instance().activeModContainer().getModId(), name);
        return new RodPole(resource, durability, strength);
    }

    @Override
    public RodComponent createReel(String name, float heightModifier, float distanceBonus, double retractSpeed) {
        ResourceLocation resource = new ResourceLocation(Loader.instance().activeModContainer().getModId(), name);
        return new RodReel(resource, heightModifier, distanceBonus, retractSpeed);
    }

    @Override
    public RodComponent createString(String name, float strengthModifier) {
        ResourceLocation resource = new ResourceLocation(Loader.instance().activeModContainer().getModId(), name);
        return new RodString(resource, strengthModifier);
    }

    @Override
    public RodComponent createHook(String name, int catchSpeed, Size... bestSizes) {
        ResourceLocation resource = new ResourceLocation(Loader.instance().activeModContainer().getModId(), name);
        return new RodHook(resource, catchSpeed, bestSizes);
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
