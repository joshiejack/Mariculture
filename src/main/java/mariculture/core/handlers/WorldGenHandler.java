package mariculture.core.handlers;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.blocks.TileOyster;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.Rand;
import mariculture.core.world.WorldGenGas;
import mariculture.core.world.WorldGenLimestone;
import mariculture.core.world.WorldGenOre;
import mariculture.plugins.PluginBiomesOPlenty;
import mariculture.plugins.PluginBiomesOPlenty.Biome;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.IWorldGenerator;

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
		
		generateGas(world, random, x, z);
		generateBauxite(world, random, x, z);
		generateCopper(world, random, x, z);
		generateOyster(world, random, x, z);
	}
	
	public static void generateBauxite(World world, Random random, int x, int z) {
		int posX, posY, posZ;
		// Layers 60-256 for Bauxite
		if (OreGeneration.BAUXITE_ON) {
			for (int i = 0; i < OreGeneration.BAUXITE_TOTAL; i++) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.BAUXITE_MIN + random.nextInt(OreGeneration.BAUXITE_MAX - OreGeneration.BAUXITE_MIN);
		        posZ = z + random.nextInt(16);
			    new WorldGenOre(Core.oreBlocks, OresMeta.BAUXITE, 
			        OreGeneration.BAUXITE_VEIN, Blocks.stone).generate(world, random, posX, posY, posZ);
		    }
		}
	}
	
	public static void generateGas(World world, Random random, int x, int z) {
		int posX, posY, posZ;
		//Layers 16-25 for Gas
		if(OreGeneration.NATURAL_GAS_ON && random.nextInt(OreGeneration.NATURAL_GAS_CHANCE) == 0) {
			if(isOceanBiome(world, x, z)) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.NATURAL_GAS_MIN + random.nextInt(OreGeneration.NATURAL_GAS_MAX - OreGeneration.NATURAL_GAS_MIN);
				posZ = z + random.nextInt(16);
				new WorldGenGas(Core.airBlocks, AirMeta.NATURAL_GAS, OreGeneration.NATURAL_GAS_VEIN, Blocks.stone)
					.generate(world, random, posX, posY, posZ);
			}
		}
	}
	
	public static void generateCopper(World world, Random random, int x, int z) {
		int posX, posY, posZ;
		// Layers 1-64 for Copper
		if (OreGeneration.COPPER_ON) {
			for (int i = 0; i < OreGeneration.COPPER_TOTAL; i++) {
				posX = x + random.nextInt(16);
				posY = OreGeneration.COPPER_MIN + random.nextInt(OreGeneration.COPPER_MAX - OreGeneration.COPPER_MIN);
		     	posZ = z + random.nextInt(16);
		     	new WorldGenOre(Core.oreBlocks, OresMeta.COPPER,  OreGeneration.COPPER_VEIN, Blocks.stone)
		     		.generate(world, random, posX, posY, posZ);
			}
		}		
	}
	
	public static boolean isRiverBiome(World world, int posX, int posZ) {
		if(!Extra.RIVER_FORCE) {
			if(BiomeDictionary.isBiomeOfType(world.getWorldChunkManager().getBiomeGenAt(posX, posZ), Type.WATER))
				return true;
		}
		
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
	
	public static boolean isOceanBiome(World world, int posX, int posZ) {
		if(!Extra.OCEAN_FORCE) {
			if(MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(posX, posZ)) == EnumBiomeType.OCEAN) {
				return true;
			}
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
	public static void generateOyster(World world, Random random, int blockX, int blockZ) {
		if(WorldGeneration.OYSTER_ENABLED) {
			if (MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(blockX, blockZ)) == EnumBiomeType.OCEAN) {
				for(int j = 0; j < WorldGeneration.OYSTER_PER_CHUNK; j++) {
					int chance = (PluginBiomesOPlenty.isBiome(world, blockX, blockZ, Biome.CORAL))? WorldGeneration.OYSTER_CHANCE: WorldGeneration.OYSTER_CHANCE * 2;
					if(random.nextInt(chance) == 0) {
						int randMeta = random.nextInt(4);
						int randX = blockX - 8 + random.nextInt(4);
						int randZ = blockZ - 8 + random.nextInt(4);
						int blockY = world.getTopSolidOrLiquidBlock(randX, randZ);
	
						if (Core.waterBlocks.canBlockStay(world, randX, blockY, randZ)) {
							if(BlockHelper.isWater(world, randX, blockY + 1, randZ)) {
								world.setBlock(randX, blockY, randZ, Core.waterBlocks);
								TileOyster oyster = (TileOyster) world.getTileEntity(randX, blockY, randZ);
								if (oyster != null) {
									oyster.orientation = ForgeDirection.values()[2 + random.nextInt(4)];
									if (random.nextInt(WorldGeneration.OYSTER_PEARL_CHANCE) == 0) {
										oyster.setInventorySlotContents(0, PearlGenHandler.getRandomPearl(random));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
