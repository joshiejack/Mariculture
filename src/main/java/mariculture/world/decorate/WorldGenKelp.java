package mariculture.world.decorate;

import java.util.Random;

import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.helpers.BlockHelper;
import mariculture.world.BlockCoral;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenKelp extends WorldGenerator {
    private Block block;
    private int meta;

    public WorldGenKelp(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    private static boolean genForest = false;

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        if (!WorldGen.KELP_FOREST_ENABLED) return true;
        //If forests enabled, turn them on/off
        if (rand.nextInt(Math.max(1, WorldGen.KELP_FOREST_START_CHANCE)) == 0) {
            genForest = true;
        }
        if (genForest && rand.nextInt(Math.max(1, WorldGen.KELP_FOREST_END_CHANCE)) == 0) {
            genForest = false;
        }

        for (int l = 0; l < (genForest ? 128 : rand.nextInt(129)); l++) {
            int i1 = x + rand.nextInt(8) - rand.nextInt(8);
            int k1 = z + rand.nextInt(8) - rand.nextInt(8);
            int j1 = 62;

            do {
                j1--;
            } while (BlockHelper.isWater(world, i1, j1, k1));

            if (!BlockCoral.canSustainKelp(world.getBlock(i1, j1, k1), world.getBlockMetadata(i1, j1, k1))) {
                continue;
            }
            if (genForest && rand.nextInt(WorldGen.KELP_FOREST_CHEST_CHANCE) == 0) {
                generateChest(world, rand, i1, j1 + 1, k1);
            } else {
                for (int i = 0; j1 + i < j1 + rand.nextInt(genForest ? 5 : 2); j1++) {
                    if (i == 0 && world.getBlock(i1, j1, k1) == block) {
                        world.setBlockMetadataWithNotify(i1, j1, k1, meta, 2);
                    }
                    if (BlockHelper.isWater(world, i1, j1 + 2, k1)) {
                        world.setBlock(i1, j1 + 1, k1, block, meta, 2);
                    } else {
                        break;
                    }
                }

                if (world.getBlock(i1, j1, k1) == block) {
                    world.setBlock(i1, j1, k1, block, meta - 1, 1);
                }
            }
        }

        return true;
    }

    private void generateChest(World world, Random rand, int x, int y, int z) {
        if (world.getBlock(x, y - 1, z) == Blocks.sand) {
            world.setBlock(x, y, z, Blocks.chest, rand.nextInt(4), 2);
            TileEntityChest chest = (TileEntityChest) world.getTileEntity(x, y, z);
            if (chest != null) {
                WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(WorldPlus.OCEAN_CHEST, rand), chest, rand.nextInt(WorldGen.KELP_FOREST_CHEST_MAX_ITEMS - WorldGen.KELP_FOREST_CHEST_MIN_ITEMS) + WorldGen.KELP_FOREST_CHEST_MIN_ITEMS);
            }
        }
    }
}
