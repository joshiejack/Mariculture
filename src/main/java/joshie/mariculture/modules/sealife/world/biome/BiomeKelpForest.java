package joshie.mariculture.modules.sealife.world.biome;

import joshie.mariculture.modules.sealife.world.decorate.PlantDecorator;
import joshie.mariculture.modules.sealife.world.gen.WorldGenPlant;
import net.minecraft.world.biome.BiomeDecorator;

import static joshie.mariculture.modules.sealife.blocks.BlockPlant.Plant.*;

public class BiomeKelpForest extends BiomeSandy {
    public BiomeKelpForest(BiomeProperties properties) {
        super(properties);
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new PlantDecorator(new WorldGenPlant(KELP_TOP).setFrequency(32), new WorldGenPlant(KELP_TOP, KELP_MIDDLE, KELP_BOTTOM).setFrequency(16));
    }
}
