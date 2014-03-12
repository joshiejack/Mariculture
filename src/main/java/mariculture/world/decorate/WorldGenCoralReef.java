package mariculture.world.decorate;

import java.util.ArrayList;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.Rand;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCoralReef extends WorldGenerator {
	private static class Coords {
		int x, y, z;
		public Coords(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	private static ArrayList<Coords> cache;
	
	private void setBlock(World world, int x, int y, int z) {
		cache.add(new Coords(x, y, z));
		world.setBlock(x, y, z, Core.oreBlocks, OresMeta.CORAL_ROCK, 2);
	}
	
	public boolean coralCanReplace(Block block) {
		return block == Blocks.sand || block == Blocks.gravel || block == Blocks.dirt || block == Core.limestone;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		y = world.getTopSolidOrLiquidBlock(x, z);
		cache = new ArrayList();
		
		for (int i = -rand.nextInt(16) - rand.nextInt(4); i < rand.nextInt(16) + rand.nextInt(4); i++) {
			int xPos = x + i;
			for (int j = -rand.nextInt(12) - rand.nextInt(i < 3 || i > 3 ? 1 : 5); j < rand.nextInt(14) + rand.nextInt(i < 5 || i > 3 ? 1 : 3); j++) {
				int zPos = z + j;
				if(coralCanReplace(world.getBlock(xPos, y - 1, zPos)) && BlockHelper.isWater(world, xPos, y + 1, zPos) && BlockHelper.isWater(world, xPos, y + 2, zPos)) {
					setBlock(world, xPos, y - 1, zPos);
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
						if(coralCanReplace(world.getBlock(i1, i2 - 1, j1)) && BlockHelper.isWater(world, i1, i2 + 1, j1) && BlockHelper.isWater(world, i1, i2 + 2, j1)) {
							setBlock(world, i1, i2 - 1, j1);
						}
					}
				}
			}
		}
		
		for(Coords cord: cache) {
			if(BlockHelper.isWater(world, cord.x, cord.y + 1, cord.z) && BlockHelper.isWater(world, cord.x, cord.y + 2, cord.z)) {
				if(rand.nextInt(20) != 0) world.setBlock(cord.x, cord.y + 1, cord.z, WorldPlus.plantStatic, rand.nextInt(CoralMeta.COUNT - 2) + 2, 2);
				else world.setBlock(cord.x, cord.y + 1, cord.z, Blocks.sponge);
			}
		}

		return true;
	}
}
