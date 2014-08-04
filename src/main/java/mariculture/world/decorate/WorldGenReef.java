package mariculture.world.decorate;

import hacker.TimeMeasurement;

import java.util.ArrayList;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.config.GeneralStuff;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenReef extends WorldGenerator {
    private void setBlock(ArrayList<String> cache, World world, int x, int y, int z) {
        String coords = x + ":" + z;
        if (!cache.contains(coords)) {
            cache.add(coords);
        }
        world.setBlock(x, y, z, Core.rocks);
    }

    public boolean coralCanReplace(Block block) {
        return block == Blocks.sand || block == Blocks.gravel || block == Blocks.dirt || block == Core.limestone;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        if (GeneralStuff.DEBUG_ON) TimeMeasurement.start("CoralReef");
        ArrayList<String> cache = new ArrayList();
        if (!BlockHelper.chunkExists(world, x, z)) return false;
        y = world.getTopSolidOrLiquidBlock(x, z);
        if (WorldGen.CORAL_EXTRA) {
            for (int i = -rand.nextInt(16) - rand.nextInt(4); i < rand.nextInt(16) + rand.nextInt(4); i++) {
                int xPos = x + i;
                for (int j = -rand.nextInt(12) - rand.nextInt(i < 3 || i > 3 ? 1 : 5); j < rand.nextInt(14) + rand.nextInt(i < 5 || i > 3 ? 1 : 3); j++) {
                    int zPos = z + j;
                    if (coralCanReplace(world.getBlock(xPos, y - 1, zPos)) && BlockHelper.isWater(world, xPos, y + 1, zPos) && BlockHelper.isWater(world, xPos, y + 2, zPos)) {
                        setBlock(cache, world, xPos, y - 1, zPos);
                    }
                }
            }
        }

        int l = rand.nextInt(5 - 2) + 2;
        for (int i1 = x - l; i1 <= x + l; ++i1) {
            for (int j1 = z - l; j1 <= z + l; ++j1) {
                int k1 = i1 - x;
                int l1 = j1 - z;

                if (k1 * k1 + l1 * l1 <= l * l) {
                    for (int i2 = y; i2 <= y; ++i2) {
                        if (coralCanReplace(world.getBlock(i1, i2 - 1, j1)) && BlockHelper.isWater(world, i1, i2 + 1, j1) && BlockHelper.isWater(world, i1, i2 + 2, j1)) {
                            setBlock(cache, world, i1, i2 - 1, j1);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < cache.size(); i++) {
            String[] coords = cache.get(i).split(":");
            int x2 = Integer.parseInt(coords[0]);
            int z2 = Integer.parseInt(coords[1]);
            int y2 = world.getTopSolidOrLiquidBlock(x2, z2);
            if (BlockHelper.isWater(world, x2, y2 + 2, z2) && BlockHelper.isWater(world, x2, y2 + 1, z2)) if (rand.nextInt(25) > 0) {
                world.setBlock(x2, y2, z2, WorldPlus.plantStatic, rand.nextInt(CoralMeta.COUNT - 5) + 2, 2);
            } else {
                world.setBlock(x2, y2, z2, Blocks.sponge);
            }
        }
        
        if (GeneralStuff.DEBUG_ON) TimeMeasurement.finish("CoralReef");

        return true;
    }
}
