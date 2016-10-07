package joshie.mariculture.modules.sealife.world;

import joshie.mariculture.modules.sealife.world.layer.GenLayerBeach;
import joshie.mariculture.modules.sealife.world.layer.GenLayerDeepestOcean;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import static net.minecraft.world.gen.layer.GenLayer.getModdedBiomeSize;

public class OceanicBiomeProvider extends BiomeProvider {
    public OceanicBiomeProvider(WorldInfo info) {
        super(info);
        GenLayer[] agenlayer = initializeAllBiomeGenerators(info.getSeed(), info.getTerrainType(), info.getGeneratorOptions());
        agenlayer = getModdedBiomeGenerators(info.getTerrainType(), info.getSeed(), agenlayer);
        ReflectionHelper.setPrivateValue(BiomeProvider.class, this, agenlayer[0], "genBiomes", "field_76944_d");
        ReflectionHelper.setPrivateValue(BiomeProvider.class, this, agenlayer[1], "biomeIndexLayer", "field_76945_e");
    }

    //Adding this here, to replace the vanilla GenLayerShore
    public GenLayer[] initializeAllBiomeGenerators(long seed, WorldType type, String options) {
        GenLayer genlayer = new GenLayerIsland(1L);
        genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        GenLayerAddIsland genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
        genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
        GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
        GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
        GenLayerAddIsland genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
        GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        GenLayerZoom genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
        genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
        GenLayerAddIsland genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
        GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
        GenLayerDeepestOcean genlayerdeepocean = new GenLayerDeepestOcean(4L, genlayeraddmushroomisland);
        GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
        int i = 4;
        int j = i;

        if (type == WorldType.CUSTOMIZED && !options.isEmpty()) {
            ChunkProviderSettings chunkprovidersettings = ChunkProviderSettings.Factory.jsonToFactory(options).build();
            i = chunkprovidersettings.biomeSize;
            j = chunkprovidersettings.riverSize;
        }

        if (type == WorldType.LARGE_BIOMES) {
            i = 6;
        }

        i = getModdedBiomeSize(type, i);

        GenLayer lvt_8_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, lvt_8_1_);
        GenLayer lvt_10_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayer genlayerbiomeedge = type.getBiomeLayer(seed, genlayer4, options);
        GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_10_1_);
        GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
        GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer5);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);

        for (int k = 0; k < i; ++k) {
            genlayerhills = new GenLayerZoom((long) (1000 + k), genlayerhills);

            if (k == 0) {
                genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
            }

            if (k == 1 || i == 1) {
                genlayerhills = new GenLayerBeach(1000L, genlayerhills);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(seed);
        genlayer3.initWorldGenSeed(seed);
        return new GenLayer[]{genlayerrivermix, genlayer3, genlayerrivermix};
    }
}