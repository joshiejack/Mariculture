package mariculture.world.decorate;

import java.util.Random;

import mariculture.api.core.CoralRegistry;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.WorldGeneration;
import mariculture.world.BlockCoral;
import mariculture.world.WorldPlus;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.Loader;

public class WorldGenReef extends WorldGenerator {
	private int numberOfBlocks;
	private int top;

	public WorldGenReef(int num) {
		numberOfBlocks = num;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return generate(world, random, x, world.getTopSolidOrLiquidBlock(x, z), z);
	}

	public boolean generate(World world, Random random, int xCoord, int zCoord) {
		boolean isSet = false;
		float f = random.nextFloat() * (float) Math.PI;
		double d = xCoord + 8 + (MathHelper.sin(f) * numberOfBlocks) / 8F;
		double d1 = xCoord + 8 - (MathHelper.sin(f) * numberOfBlocks) / 8F;
		double d2 = zCoord + 8 + (MathHelper.cos(f) * numberOfBlocks) / 8F;
		double d3 = zCoord + 8 - (MathHelper.cos(f) * numberOfBlocks) / 8F;
		double d4 = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		double d5 = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		int max = WorldGeneration.CORAL_DEPTH;

		if (Loader.isModLoaded("BiomesOPlenty")) {
			max = max + 10;
		}

		if (BlockHelper.isAir(world, (int) d, (int) d4 + 3, (int) d2)) {
			return false;
		}

		if (BlockHelper.isAir(world, (int) d, (int) (d4 + max), (int) d2)) {
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
						boolean addedCoral = false;
						for (int z = l; z <= k1; z++) {
							double d14 = ((z + 0.5D) - d8) / (d10 / 2D);

							if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (BlockHelper.isWater(world, x, y, z))) {
								if (y < 64) {
									if (!isSet) {
										isSet = true;
										top = world.getTopSolidOrLiquidBlock(x, z) + 1;
									}

									if (world.getTopSolidOrLiquidBlock(x, z) <= top) {
										if (BlockHelper.isWater(world, x, world.getTopSolidOrLiquidBlock(x, z) + 3, z)) {
											BlockHelper.setBlock(world, x, world.getTopSolidOrLiquidBlock(x, z), z, Core.oreBlocks, OresMeta.CORAL_ROCK);
										}
									} else if (world.getTopSolidOrLiquidBlock(x, z) == top + 1) {
										if (world.getBlock(x, world.getTopSolidOrLiquidBlock(x, z) - 1, z) == Core.oreBlocks
												&& world.getBlockMetadata(x, world.getTopSolidOrLiquidBlock(x, z) - 1, z) == OresMeta.CORAL_ROCK) {
											if (BlockHelper.isWater(world, x, world.getTopSolidOrLiquidBlock(x, z) + 1, z)) {
												if (random.nextInt(400) == 0) {
													BlockHelper.setBlock(world, x, world.getTopSolidOrLiquidBlock(x, z), z, Blocks.sponge, 0);
												} else {
													ItemStack coral = CoralRegistry.corals.get(random.nextInt(CoralRegistry.corals.size()));
													BlockHelper.setBlock(world, x, world.getTopSolidOrLiquidBlock(x, z), z, WorldPlus.coral, coral.getItemDamage());
													BlockCoral.fullSpread(world, x, world.getTopSolidOrLiquidBlock(x, z), z, random);
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
		}

		return true;
	}
}