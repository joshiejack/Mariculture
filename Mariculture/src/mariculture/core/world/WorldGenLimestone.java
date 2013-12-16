package mariculture.core.world;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLimestone extends WorldGenerator {
	private int numberOfBlocks;

	public WorldGenLimestone(int par2) {
		numberOfBlocks = par2;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		return generate(world, random, i, world.getTopSolidOrLiquidBlock(i, k), k);
	}

	public boolean generate(World world, Random random, int xCoord, int zCoord) {
		float f = random.nextFloat() * (float) Math.PI;
		double d = xCoord + 8 + (MathHelper.sin(f) * numberOfBlocks) / 8F;
		double d1 = xCoord + 8 - (MathHelper.sin(f) * numberOfBlocks) / 8F;
		double d2 = zCoord + 8 + (MathHelper.cos(f) * numberOfBlocks) / 8F;
		double d3 = zCoord + 8 - (MathHelper.cos(f) * numberOfBlocks) / 8F;
		double d4 = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		double d5 = world.getTopSolidOrLiquidBlock(xCoord, zCoord);

		for (int i = 0; i <= numberOfBlocks; i++) {
			double d6 = d + ((d1 - d) * i) / numberOfBlocks;
			double d7 = d4 + ((d5 - d4) * i) / numberOfBlocks;
			double d8 = d2 + ((d3 - d2) * i) / numberOfBlocks;
			double d9 = (random.nextDouble() * numberOfBlocks) / 16D;
			double d10 = (MathHelper.sin((i * (float) Math.PI) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin((i * (float) Math.PI) / numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int j = MathHelper.floor_double(d6 - d10 / 2D);
			int k = MathHelper.floor_double(d7 - d11 / 2D);
			int l = MathHelper.floor_double(d8 - d10 / 2D);
			int i1 = MathHelper.floor_double(d6 + d10 / 2D);
			int j1 = MathHelper.floor_double(d7 + d11 / 2D);
			int k1 = MathHelper.floor_double(d8 + d10 / 2D);

			for (int x = j; x <= i1; x++) {
				double d12 = ((x + 0.5D) - d6) / (d10 / 2D);

				if (d12 * d12 >= 1.0D) {
					continue;
				}

				for (int y = k; y <= j1; y++) {
					double d13 = ((y + 0.5D) - d7) / (d11 / 2D);

					if (d12 * d12 + d13 * d13 >= 1.0D) {
						continue;
					}

					for (int z = l; z <= k1; z++) {
						double d14 = ((z + 0.5D) - d8) / (d10 / 2D);
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
							if(y < 64) {
								if (isValidPlacement(world, x, y, z)) {
									if (OreGeneration.RUTILE && random.nextInt(OreGeneration.RUTILE_CHANCE) == 0) {
										world.setBlock(x, y, z, Core.oreBlocks.blockID, OresMeta.RUTILE, 2);
									} else {
										world.setBlock(x, y, z, Core.oreBlocks.blockID, OresMeta.LIMESTONE, 2);
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
	
	private boolean canReplace(World world, int x, int y, int z) {
		if(world.getBlockId(x, y, z) == Block.stone.blockID) {
			return true;
		}
		
		if(world.getBlockId(x, y, z) == Block.gravel.blockID) {
			return true;
		}
		
		if(world.getBlockId(x, y, z) == Block.dirt.blockID) {
			return true;
		}
		
		if(world.getBlockId(x, y, z) == Block.sand.blockID) {
			return true;
		}
		
		return false;
	}

	private boolean isValidPlacement(World world, int x, int y, int z) {
		if(!world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
			return false;
		}
		
		try {
			if(canReplace(world, x, y, z)) {
				for (int i = 0; i < 10; i++) {
					if (BlockHelper.isWater(world, x, y + i, z)) {
						return true;
					}
				}
			}
		} catch (Exception e) {}
		
		return false;
	}
}