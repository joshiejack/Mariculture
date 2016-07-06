package joshie.mariculture.api.gen;

import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.DENY;

public interface IWorldGen<C extends IChunkGenerator> {

    /** Called when chunks generate, This is to add additional features to the world,
     *  Or replace existing ones
     * @param generator
     * @param primer
     * @param chunkX
     * @param chunkZ
     */
    void generate(C generator, ChunkPrimer primer, int chunkX, int chunkZ);

    /** What this does to the event, By default it will return DENY,
     *  Which means the calling of vanilla will not happen,
     *  Returning true means this code gets run as well as the one from vanilla
     *
     * @return the result
     */
    default Result getResult() {
        return DENY;
    }
}
