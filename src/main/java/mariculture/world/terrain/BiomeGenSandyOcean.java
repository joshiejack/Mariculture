package mariculture.world.terrain;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.config.WorldGeneration.OreGen;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.RockMeta;
import mariculture.core.util.Rand;
import mariculture.world.WorldPlus;
import mariculture.world.decorate.WorldGenAncientSand;
import mariculture.world.decorate.WorldGenKelp;
import mariculture.world.decorate.WorldGenReef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSandyOcean extends BiomeGenOcean {
    public BiomeGenSandyOcean(int id) {
        super(id);

        theBiomeDecorator.lapisGen = new WorldGenMinable(Blocks.lapis_ore, 8);
    }

    public static WorldGenAncientSand sandGen = new WorldGenAncientSand(Core.sands, GroundMeta.ANCIENT, WorldGen.ANCIENT_SAND_SIZE);
    public static WorldGenReef coralGenerator = new WorldGenReef();
    public static WorldGenKelp kelpGenerator = new WorldGenKelp(WorldPlus.plantStatic, CoralMeta.KELP_MIDDLE);

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
        return kelpGenerator;
    }

    private static boolean isCoralReef = false;

    @Override
    public void decorate(World world, Random rand, int x, int z) {
        super.decorate(world, rand, x, z);

        generateSand(world, rand, x, z);
        generateCoral(world, rand, x, z);
    }

    public static void generateSand(World world, Random rand, int x, int z) {
        if (WorldGen.ANCIENT_SAND_ENABLED) {
            if (rand.nextInt(Math.max(1, WorldGen.ANCIENT_SAND_CHANCE)) == 0) {
                int j = x + rand.nextInt(16) + 8;
                int k = z + rand.nextInt(16) + 8;
                sandGen.generate(world, rand, j, world.getTopSolidOrLiquidBlock(j, k), k);
            }
        }
    }

    public static void generateCoral(World world, Random rand, int x, int z) {
        if (WorldGen.CORAL_REEF_ENABLED) {
            if (!isCoralReef && Rand.nextInt(WorldGen.CORAL_REEF_START_CHANCE)) {
                isCoralReef = true;
            }
            if (isCoralReef && Rand.nextInt(WorldGen.CORAL_REEF_END_CHANCE)) {
                isCoralReef = false;
            }
            if (isCoralReef) {
                for (int i = 0; i < 5; i++) {
                    coralGenerator.generate(world, rand, x + rand.nextInt(16) + 8, 64, z + rand.nextInt(16) + 8);
                }
            }
        }
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] metas, int x, int z, double noise) {
        boolean flag = true;
        Block block = topBlock;
        byte b0 = (byte) (field_150604_aj & 255);
        Block block1 = fillerBlock;
        int k = -1;
        int l = (int) (noise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int i1 = x & 15;
        int j1 = z & 15;
        int k1 = blocks.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + rand.nextInt(5)) {
                blocks[i2] = Blocks.bedrock;
            } else {
                Block block2 = blocks[i2];

                if (block2 != null && block2.getMaterial() != Material.air) {
                    if (block2 == Blocks.stone) if (k == -1) {
                        if (l <= 0) {
                            block = null;
                            b0 = 0;
                            block1 = Core.limestone;
                        } else if (l1 >= 59 && l1 <= 64) {
                            block = topBlock;
                            b0 = (byte) (field_150604_aj & 255);
                            block1 = fillerBlock;
                        }

                        if (l1 < 63 && (block == null || block.getMaterial() == Material.air)) if (getFloatTemperature(x, l1, z) < 0.15F) {
                            block = Blocks.ice;
                            b0 = 0;
                        } else {
                            block = Blocks.water;
                            b0 = 0;
                        }

                        k = l;

                        if (l1 >= 62) {
                            blocks[i2] = block;
                            metas[i2] = b0;
                        } else if (l1 < 56 - l) {
                            block = null;
                            block1 = Core.limestone;
                            blocks[i2] = Blocks.sand;
                        } else {
                            blocks[i2] = block1;
                        }
                    } else if (k > 0) {
                        --k;

                        blocks[i2] = block1;
                        if (Rand.nextInt(OreGen.RUTILE_SPAWN_CHANCE)) {
                            blocks[i2] = Core.rocks;
                            metas[i2] = RockMeta.RUTILE;
                        }

                        if (k == 0 && block1 == Blocks.sand) {
                            k = rand.nextInt(4) + Math.max(0, l1 - 63);
                            block1 = Blocks.sandstone;
                        }
                    }
                } else {
                    k = -1;
                }
            }
        }
    }
}
