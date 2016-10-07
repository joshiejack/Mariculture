package joshie.mariculture.modules.sealife.world.biome;

import joshie.mariculture.modules.sealife.world.decorate.PlantDecorator;
import joshie.mariculture.modules.sealife.world.gen.WorldGenPlant;
import net.minecraft.world.biome.BiomeDecorator;

import static joshie.mariculture.modules.sealife.blocks.BlockPlant.Plant.*;

public class BiomeSeagrassMeadow extends BiomeSandy {
    public BiomeSeagrassMeadow(BiomeProperties properties) {
        super(properties);
        //this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityTurtle.class, 30, 2, 3)); //TODO: Add Sea Turtles
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new PlantDecorator(new WorldGenPlant(GRASS_SHORT), new WorldGenPlant(GRASS_TOP, GRASS_DOUBLE));
    }
}
