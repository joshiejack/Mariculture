package mariculture.world.terrain;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenSandyOcean extends BiomeGenBase {
    public BiomeGenSandyOcean(int i) {
        super(i);
        this.spawnableCreatureList.clear();
        this.topBlock = (byte)Block.sand.blockID;
        this.theBiomeDecorator.dirtGen = null;
        this.theBiomeDecorator.sandPerChunk = 16;
        this.theBiomeDecorator.sandPerChunk2 = 16;
    }
}
