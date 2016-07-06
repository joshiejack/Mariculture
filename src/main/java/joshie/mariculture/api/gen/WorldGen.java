package joshie.mariculture.api.gen;

import net.minecraft.world.chunk.IChunkGenerator;

public interface WorldGen {
    /**This is to make it easier to support different chunk generators than the vanilla standard one,
     * Simply register the chunk generator class to a worldgen and it will be called
     *
     * @param chunkGenerator    the chunk generator to associate with this world generator
     * @param worldGen          the worldgen to register */
    void registerWorldGenHandler(Class<? extends IChunkGenerator> chunkGenerator, IWorldGen worldGen);
}
