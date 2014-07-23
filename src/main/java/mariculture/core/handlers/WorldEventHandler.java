package mariculture.core.handlers;

import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.world.terrain.BiomeGenSandyOcean;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {
    //Core, Replacing Gravel with Sand and Limestone in Oceans
    @SubscribeEvent
    public void onReplaceBiomeBlocks(ReplaceBiomeBlocks event) {
        if (event.metaArray == null || event.world == null) return;
        IChunkProvider chunkProvider = event.world.provider.terrainType.getChunkGenerator(event.world, event.world.provider.field_82913_c);
        if (chunkProvider instanceof ChunkProviderGenerate) {
            ChunkProviderGenerate chunk = (ChunkProviderGenerate) chunkProvider;
            double d0 = 0.03125D;
            chunk.stoneNoise = chunk.field_147430_m.func_151599_a(chunk.stoneNoise, (double) (event.chunkX * 16), (double) (event.chunkZ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

            boolean affected = false;
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    BiomeGenBase biome = event.biomeArray[l + k * 16];
                    if (BiomeDictionary.isBiomeOfType(biome, Type.WATER) || BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) {
                        if (BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) {
                            genBiomeTerrain(event.world, event.world.rand, event.blockArray, event.metaArray, event.chunkX * 16 + k, event.chunkZ * 16 + l, chunk.stoneNoise[l + k * 16], biome, WorldGen.BEACH_LIMESTONE);
                        } else if (biome == BiomeGenBase.deepOcean) {
                            genBiomeTerrain(event.world, event.world.rand, event.blockArray, event.metaArray, event.chunkX * 16 + k, event.chunkZ * 16 + l, chunk.stoneNoise[l + k * 16], biome, WorldGen.OCEAN_DEEP_LIMESTONE);
                        } else if (biome instanceof BiomeGenRiver) {
                            genBiomeTerrain(event.world, event.world.rand, event.blockArray, event.metaArray, event.chunkX * 16 + k, event.chunkZ * 16 + l, chunk.stoneNoise[l + k * 16], biome, WorldGen.RIVER_LIMESTONE);
                        } else {
                            genBiomeTerrain(event.world, event.world.rand, event.blockArray, event.metaArray, event.chunkX * 16 + k, event.chunkZ * 16 + l, chunk.stoneNoise[l + k * 16], biome, WorldGen.OCEAN_LIMESTONE);
                        }
                    } else {
                        biome.genTerrainBlocks(event.world, event.world.rand, event.blockArray, event.metaArray, event.chunkX * 16 + k, event.chunkZ * 16 + l, chunk.stoneNoise[l + k * 16]);
                    }
                }
            }

            if (affected) {
                event.setResult(Result.DENY);
            }
        }
    }

    private Block topBlock = Blocks.grass;
    private Block fillerBlock = Blocks.dirt;

    public final void genBiomeTerrain(World p_150560_1_, Random p_150560_2_, Block[] p_150560_3_, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_, BiomeGenBase biome, double depth) {
        boolean flag = true;
        Block block = this.topBlock;
        byte b0 = 0;
        Block block1 = this.fillerBlock;
        int k = -1;
        int l = (int) (p_150560_7_ / depth + depth + p_150560_2_.nextDouble() * 0.25D);
        int i1 = p_150560_5_ & 15;
        int j1 = p_150560_6_ & 15;
        int k1 = p_150560_3_.length / 256;

        for (int l1 = 255; l1 >= 0; --l1) {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + p_150560_2_.nextInt(5)) {
                p_150560_3_[i2] = Blocks.bedrock;
            } else {
                Block block2 = p_150560_3_[i2];

                if (block2 != null && block2.getMaterial() != Material.air) {
                    if (block2 == Blocks.stone) {
                        if (k == -1) {
                            if (l <= 0) {
                                block = null;
                                b0 = 0;
                                block1 = Blocks.stone;
                            } else if (l1 >= 59 && l1 <= 64) {
                                block = this.topBlock;
                                b0 = (byte) 0;
                                block1 = this.fillerBlock;
                            }

                            if (l1 < 63 && (block == null || block.getMaterial() == Material.air)) {
                                if (biome.getFloatTemperature(p_150560_5_, l1, p_150560_6_) < 0.15F) {
                                    block = Blocks.ice;
                                    b0 = 0;
                                } else {
                                    block = Blocks.water;
                                    b0 = 0;
                                }
                            }

                            k = l;

                            if (l1 >= 62) {
                                p_150560_3_[i2] = block;
                                p_150560_4_[i2] = b0;
                            } else if (l1 < 61 - l) {
                                block = null;
                                block1 = Core.limestone;
                                p_150560_3_[i2] = Blocks.sand;
                            } else {
                                p_150560_3_[i2] = block1;
                            }
                        } else if (k > 0) {
                            --k;
                            p_150560_3_[i2] = block1;

                            if (k == 0 && block1 == Blocks.sand) {
                                k = p_150560_2_.nextInt(4) + Math.max(0, l1 - 63);
                                block1 = Blocks.sandstone;
                            }
                        }
                    }
                } else {
                    k = -1;
                }
            }
        }
    }

    /* World Plus, Replacing Grass Gen with Kelp Gen */
    @SubscribeEvent
    public void onDecorate(Decorate event) {
        if (event.type == EventType.GRASS) {
            int k = event.chunkX + event.world.rand.nextInt(16) + 8;
            int l = event.chunkZ + event.world.rand.nextInt(16) + 8;
            if (MaricultureHandlers.environment.getSalinity(event.world, k, l) == Salinity.SALINE) {
                int i1 = event.world.rand.nextInt(event.world.getHeightValue(k, l) * 2);
                BiomeGenSandyOcean.kelpGenerator.generate(event.world, event.world.rand, k, i1, l);
                event.setResult(Result.DENY);
            }
        }
    }
}
