package joshie.mariculture.modules.sealife.world;

import joshie.mariculture.modules.sealife.world.layer.GenLayerCoast;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeOcean extends WorldType {
    public WorldTypeOcean() {
        super("oceanic");
    }

    @Override
    public BiomeProvider getBiomeProvider(World world) {
        return new OceanicBiomeProvider(world.getWorldInfo());
    }

    @Override
    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, String chunkProviderSettingsJson)  {
        GenLayer ret = new GenLayerCoast(200L, parentLayer, this, chunkProviderSettingsJson);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }
}
