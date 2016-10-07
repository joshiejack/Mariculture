package joshie.mariculture.modules.sealife.world.biome;

import joshie.mariculture.modules.sealife.world.gen.WorldGenCoral;
import joshie.mariculture.modules.sealife.world.gen.WorldGenRocks;
import joshie.mariculture.modules.sealife.world.gen.WorldGenStarfish;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeCoralReef extends BiomeSandy {
    private static final WorldGenCoral CORAL_GENERATOR = new WorldGenCoral();
    private static final WorldGenStarfish STARFISH_GENERATOR = new WorldGenStarfish();
    private static final WorldGenRocks ROCK_GENERATOR = new WorldGenRocks();

    public BiomeCoralReef(BiomeProperties properties) {
        super(properties);
        //this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityUrchin.class, 30, 5, 11)); //TODO: Add Urchins
    }

    @Override
    public void decorate(World worldIn, Random random, BlockPos pos) {
        //Generate Reef Boulders
        if (random.nextInt(7) == 0) {
            int i = random.nextInt(5);
            for (int j = 0; j < i; ++j) {
                int k = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
                ROCK_GENERATOR.generate(worldIn, random, blockpos);
            }
        }

        //Generate Coral
        for (int l2 = 0; l2 < 20; ++l2) {
            int i7 = random.nextInt(16) + 8;
            int l10 = random.nextInt(16) + 8;
            BlockPos blockpos1 = pos.add(i7, worldIn.getTopSolidOrLiquidBlock(new BlockPos(i7, 0, l10)).getY(), l10);
            CORAL_GENERATOR.generate(worldIn, random, blockpos1);
        }

        //Generate Starfish
        for (int j5 = 0; j5 < 10; ++j5) {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(pos.add(l9, 0, k13)).getY() * 2;

            if (l16 > 0) {
                int j19 = random.nextInt(l16);
                STARFISH_GENERATOR.generate(worldIn, random, pos.add(l9, j19, k13));
            }
        }

        super.decorate(worldIn, random, pos);
    }
}
