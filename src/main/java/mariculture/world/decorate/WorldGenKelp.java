package mariculture.world.decorate;

import java.util.Random;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.world.WorldPlus;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;

public class WorldGenKelp extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return generate(world, random, x, z, false, 3, WorldGeneration.KELP_PATCH_DENSITY);
	}
	
	public boolean generate(World world, Random random, int x, int z, boolean chests, int max, int times) {
		for (int l = 0; l < times; ++l) {
			int x2 = x + random.nextInt(8) - random.nextInt(8);
			int z2 = z + random.nextInt(8) - random.nextInt(8);
			 if (world.getChunkProvider().chunkExists(x2 >> 4, z2 >> 4)) {
		        try {
		        	generateKelp(world, random, x, z, chests, max);
		        } catch(Exception e){ }
		     }
		}
		
		return true;
	}
	
	public boolean generateKelp(World world, Random rand, int xCoord, int zCoord, boolean chests, int maxHeight) {
		//If the kelp is at the maximum depth
		int yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		if(world.isAirBlock(xCoord, yCoord + WorldGeneration.KELP_DEPTH, zCoord)
				&& canBlockStay(world, xCoord, yCoord, zCoord)) {
			//If chests, generate chest instead of kelp
			if(chests) {
				if(rand.nextInt(WorldGeneration.KELP_CHEST_CHANCE) == 0) {
					return generateChest(world, rand, xCoord, zCoord);
				}
			}
			
			//Get the true maximum, and if it's less than 1 return false
			int max = getMaxHeight(world, xCoord, zCoord, maxHeight);
			
			//TheHeight
			int theHeight = rand.nextInt(max) + 1;
			int theY = 0;
			for(int y = 0; y < theHeight; y++) {
				if(BlockHelper.isWater(world, xCoord, yCoord + y + 2, zCoord)) {
					theY++;
					world.setBlock(xCoord, yCoord + y, zCoord, WorldPlus.coral, CoralMeta.KELP_MIDDLE, 2);
				}
			}
			
			//Update the top block
			WorldPlus.coral.onPostBlockPlaced(world, xCoord, yCoord + (theY - 1), zCoord, 0);
			
			return true;
		}
			
		return false;
	}
	
	private int getMaxHeight(World world, int xCoord, int zCoord, int maxHeight) {
		int yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		int height;
		for(height = 1; height <= maxHeight; height++) {
			if(!BlockHelper.isWater(world, xCoord, yCoord + (height - 1), zCoord)) {
				return height;
			}
		}
		
		return maxHeight;
	}

	private boolean generateChest(World world, Random random, int x, int z) {
		int facing = random.nextInt(4);
		int y = world.getTopSolidOrLiquidBlock(x, z);
		if (BlockHelper.isWater(world, x, y + 10, z)) {
			if (BlockHelper.isWater(world, x, y, z)) {
				if (world.getBlock(x + 1, y, z) != Blocks.chest
						&& world.getBlock(x - 1, y, z) != Blocks.chest
						&& world.getBlock(x, y, z + 1) != Blocks.chest
						&& world.getBlock(x, y, z - 1) != Blocks.chest) {

					world.setBlock(x, y, z, Blocks.chest, 1, facing);
					TileEntityChest chest = (TileEntityChest) world.getTileEntity(x, y, z);
					if (chest != null) {
						WeightedRandomChestContent.generateChestContents(random,
								ChestGenHooks.getItems(WorldPlus.OCEAN_CHEST, random), chest, random.nextInt(5) + random.nextInt(5) + 2);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean canBlockStay(World world, int x, int y, int z) {
		if(!BlockHelper.isWater(world, x, y + 2, z)) {
			return false;
		}
		
		if (world.getBlock(x, y - 1, z) == WorldPlus.coral && world.getBlockMetadata(x, y - 1, z) <= CoralMeta.KELP_MIDDLE) {
			return true;
		}
		
		if (world.getBlock(x, y - 1, z) == Blocks.gravel) {
			return true;
		}
		
		if (world.getBlock(x, y - 1, z) == Blocks.cobblestone) {
			return true;
		}
		
		if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone) {
			return true;
		}
		
		if (world.getBlock(x, y - 1, z) == Blocks.sand) {
			return true;
		}
		
		return false;
	}
}
