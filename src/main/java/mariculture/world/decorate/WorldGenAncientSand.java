package mariculture.world.decorate;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.GroundMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAncientSand extends WorldGenerator {
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        if (BlockHelper.getBlock(world, x, y, z).getMaterial() != Material.water) return false;
        else {
            int l = rand.nextInt(WorldGen.ANCIENT_SAND_SIZE - 2) + 2;
            byte b0 = 2;

            for (int i1 = x - l; i1 <= x + l; ++i1) {
                for (int j1 = z - l; j1 <= z + l; ++j1) {
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    if (k1 * k1 + l1 * l1 <= l * l) {
                        for (int i2 = y - b0; i2 <= y + b0; ++i2) {
                            Block block = BlockHelper.getBlock(world, i1, i2, j1);
                            if (block == Blocks.dirt || block == Blocks.grass || block == Blocks.sand || block == Blocks.gravel) {
                                world.setBlock(i1, i2, j1, Core.sands, GroundMeta.ANCIENT, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
