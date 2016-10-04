package joshie.mariculture.modules.abyssal.gen;

import joshie.mariculture.api.gen.IWorldGen;
import joshie.mariculture.api.gen.WorldGen;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.modules.abyssal.Abyssal;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@MCEvents(modules = "abyssal")
@MCApiImpl("abyssal")
public class AbyssalOcean implements WorldGen {
    private static final HashMap<Class<? extends IChunkGenerator>, IWorldGen> GENERATORS = new HashMap<>();

    @Override
    public void registerWorldGenHandler(Class<? extends IChunkGenerator> chunkGenerator, IWorldGen worldGen) {
        GENERATORS.put(chunkGenerator, worldGen);
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public void onChunkReplaceBlocks(ChunkGeneratorEvent.ReplaceBiomeBlocks event) {
        if (Abyssal.OCEAN_REPLACE) {
            IWorldGen generator = GENERATORS.get(event.getGen().getClass());
            if (generator != null) {
                generator.generate(event.getGenerator(), event.getPrimer(), event.getX(), event.getZ());
                event.setResult(generator.getResult());
            }
        }
    }
}
