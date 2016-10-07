package joshie.mariculture.modules.sealife.world.layer;

import joshie.mariculture.modules.sealife.Sealife;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GenLayerCoast extends GenLayerBiome {
    private List<BiomeEntry> oceans = new ArrayList<BiomeEntry>();
    private static final Field field = ReflectionHelper.findField(GenLayerBiome.class, "settings", "field_175973_g");

    public GenLayerCoast(long seed, GenLayer parent, WorldType type, String options) {
        super(seed, parent, type, options);
        oceans.clear();
        oceans.add(new BiomeEntry(Sealife.REEF, 13));
        oceans.add(new BiomeEntry(Sealife.FOREST, 9));
        oceans.add(new BiomeEntry(Sealife.MEADOW, 11));
        oceans.add(new BiomeEntry(Biomes.OCEAN, 66));
    }

    public ChunkProviderSettings getSettings() {
        try {
            return (ChunkProviderSettings) field.get(this);
        } catch (Exception e) {
            return null;
        }
    }

    //Code mostly from genlayer biome
    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaHeight; ++i) {
            for (int j = 0; j < areaWidth; ++j) {
                this.initChunkSeed((long) (j + areaX), (long) (i + areaY));
                int k = aint[j + i * areaWidth];
                int l = (k & 3840) >> 8;
                k = k & -3841;

                if (this.getSettings() != null && this.getSettings().fixedBiome >= 0) {
                    aint1[j + i * areaWidth] = this.getSettings().fixedBiome;
                } else if (isBiomeOceanic(k)) {
                    if (k != Biome.getIdForBiome(Biomes.DEEP_OCEAN))
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry().biome);
                    else aint1[j + i * areaWidth] = k;
                } else if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
                    aint1[j + i * areaWidth] = k;
                } else if (k == 1) {
                    if (l > 0) {
                        if (this.nextInt(3) == 0) {
                            aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_CLEAR_ROCK);
                        } else {
                            aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_ROCK);
                        }
                    } else {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT).biome);
                    }
                } else if (k == 2) {
                    if (l > 0) {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.JUNGLE);
                    } else {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM).biome);
                    }
                } else if (k == 3) {
                    if (l > 0) {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.REDWOOD_TAIGA);
                    } else {
                        aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL).biome);
                    }
                } else if (k == 4) {
                    aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY).biome);
                } else {
                    aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
                }
            }
        }

        return aint1;
    }

    private BiomeEntry getWeightedBiomeEntry() {
        int totalWeight = WeightedRandom.getTotalWeight(oceans);
        int weight = nextInt(totalWeight / 10) * 10;
        return WeightedRandom.getRandomItem(oceans, weight);
    }
}
