package mariculture.core.handlers;

import java.util.Random;

import biomesoplenty.api.Biomes;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.OresMeta;
import mariculture.core.world.WorldGenGas;
import mariculture.core.world.WorldGenLimestone;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;

public class WorldGenHandler implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		case 1:
			break;
		default:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateSurface(World world, Random random, int x, int z) {
		int posX, posY, posZ;
		
		//Layers 16-25 for Gas
		if(OreGeneration.NATURAL_GAS_ON && random.nextInt(OreGeneration.NATURAL_GAS_CHANCE) == 0) {
			if(isValidForNaturalGas(world, x, z)) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.NATURAL_GAS_MIN + random.nextInt(OreGeneration.NATURAL_GAS_MAX - OreGeneration.NATURAL_GAS_MIN);
				posZ = z + random.nextInt(16);
				(new WorldGenGas(Core.airBlocks.blockID, AirMeta.NATURAL_GAS, OreGeneration.NATURAL_GAS_VEIN, Block.stone.blockID)).generate(world, random, posX, posY, posZ);
			}
		}
		
		// Layers 1-64 for Copper
		if (OreGeneration.COPPER_ON) {
			for (int i = 0; i < OreGeneration.COPPER_TOTAL; i++) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.COPPER_MIN + random.nextInt(OreGeneration.COPPER_MAX - OreGeneration.COPPER_MIN);
                posZ = z + random.nextInt(16);
                new WorldGenMinable(Core.oreBlocks.blockID, OresMeta.COPPER, 
                		OreGeneration.COPPER_VEIN, Block.stone.blockID).generate(world, random, posX, posY, posZ);
            }
		}

		// Layers 60-256 for Bauxite
		if (OreGeneration.BAUXITE_ON) {
			for (int i = 0; i < OreGeneration.BAUXITE_TOTAL; i++) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.BAUXITE_MIN + random.nextInt(OreGeneration.BAUXITE_MAX - OreGeneration.BAUXITE_MIN);
                posZ = z + random.nextInt(16);
                new WorldGenMinable(Core.oreBlocks.blockID, OresMeta.BAUXITE, 
                		OreGeneration.BAUXITE_VEIN, Block.stone.blockID).generate(world, random, posX, posY, posZ);
            }
		}

		// Under rivers for Limestone
		if (OreGeneration.LIMESTONE && random.nextInt(OreGeneration.LIMESTONE_CHANCE) == 0) {
			posX = x + random.nextInt(16);
			posZ = z + random.nextInt(16);
			if(isValidForLimestone(world, posX, posZ)) {
				new WorldGenLimestone(OreGeneration.LIMESTONE_VEIN).generate(world, random, posX, posZ);
			}
		}
		
		if(!OreGeneration.LIMESTONE && OreGeneration.RUTILE) {
			posX = x + random.nextInt(16);
			posZ = z + random.nextInt(16);
			if(isValidForLimestone(world, posX, posZ)) {
				for (int i = 0; i < (OreGeneration.RUTILE_CHANCE/10); i++) {
					posX = x + random.nextInt(16);
					posY = 54 + random.nextInt(10);
	                posZ = z + random.nextInt(16);
	                new WorldGenMinable(Core.oreBlocks.blockID, OresMeta.RUTILE, 
	                		2, Block.stone.blockID).generate(world, random, posX, posY, posZ);
	            }
			}
		}

		generateOysters(world, random, x, z);
	}
	
	private boolean isValidForLimestone(World world, int posX, int posZ) {
		int id = world.getWorldChunkManager().getBiomeGenAt(posX, posZ).biomeID;
		for(int i = 0; i < Extra.RIVER_BIOMES.length; i++) {
			if(Extra.RIVER_BIOMES[i] > -1) {
				if(id == Extra.RIVER_BIOMES[i]) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isValidForNaturalGas(World world, int posX, int posZ) {
		if(MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(posX, posZ)) == EnumBiomeType.OCEAN) {
			return true;
		}
		
		int id = world.getWorldChunkManager().getBiomeGenAt(posX, posZ).biomeID;
		for(int i = 0; i < Extra.OCEAN_BIOMES.length; i++) {
			if(Extra.OCEAN_BIOMES[i] > -1) {
				if(id == Extra.OCEAN_BIOMES[i]) {
					return true;
				}
			}
		}
		
		return false;
	}

	// Generates Oysters in the Ocean
	private void generateOysters(World world, Random random, int blockX, int blockZ) {
		if (MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(blockX, blockZ)) == EnumBiomeType.OCEAN) {
			if (random.nextInt(16) == 1) {
				int randMeta = random.nextInt(4);
				int randX = blockX - 8 + random.nextInt(4);
				int randZ = blockZ - 8 + random.nextInt(4);
				int blockY = world.getTopSolidOrLiquidBlock(randX, randZ);

				if (Core.oysterBlock.canBlockStay(world, randX, blockY, randZ)) {
					world.setBlock(randX, blockY, randZ, Core.oysterBlock.blockID, randMeta, 2);
					TileOyster oyster = (TileOyster) world.getBlockTileEntity(randX, blockY, randZ);
					if (oyster != null) {
						if (random.nextInt(2) == 1) {
							oyster.setInventorySlotContents(0, PearlGenHandler.getRandomPearl(random));
						}
					}
				}
			}
			
			int chance = 40;
			if(Loader.isModLoaded("BiomesOPlenty")) {
				if(Biomes.oceanCoral.isPresent()) {
					if(world.getWorldChunkManager().getBiomeGenAt(blockX, blockZ) == Biomes.oceanCoral.get()) {
						chance = 20;
					} else {
						chance = 80;
					}
				}
			}

			if (random.nextInt(chance) == 1) {
				int randMeta = random.nextInt(4);
				int randX = blockX + 8 - random.nextInt(4);
				int randZ = blockZ + 8 - random.nextInt(4);
				int blockY = world.getTopSolidOrLiquidBlock(randX, randZ);

				if (Core.oysterBlock.canBlockStay(world, randX, blockY, randZ)) {
					world.setBlock(randX, blockY, randZ, Core.oysterBlock.blockID, randMeta, 2);
					final TileOyster oyster = (TileOyster) world.getBlockTileEntity(randX, blockY, randZ);
					if (oyster != null) {
						if (random.nextInt(5) == 1) {
							oyster.setInventorySlotContents(0, PearlGenHandler.getRandomPearl(random));
						}
					}
				}
			}
		}
	}
}
