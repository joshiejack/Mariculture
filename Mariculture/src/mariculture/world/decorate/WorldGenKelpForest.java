package mariculture.world.decorate;

import java.util.Random;

import mariculture.core.lib.WorldGeneration;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenKelpForest extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		this.generate(world, random, x, y, z, 3);
		return true;
	}

	public boolean generate(World world, Random random, int x, int y, int z, int max) {
		int x2 = x + random.nextInt(8) - random.nextInt(8);
		int z2 = z + random.nextInt(8) - random.nextInt(8);
		int height = random.nextInt(max);

		for (int x3 = -(random.nextInt(32) + 32); x3 < random.nextInt(32) + 64; x3++) {
			for (int z3 = -(random.nextInt(32) + 32); z3 < random.nextInt(32) + 64; z3++) {
				if (random.nextInt(5) == 0) {
					int randX = random.nextInt(64) - 32;
					int randZ = random.nextInt(64) - 32;
					if (world.getChunkProvider().chunkExists(x2 + x3 + randZ >> 4, z2 + z3 + randX >> 4)) {
				        try {
				        	new WorldGenKelp().generate(world, random, x2 + x3 + randZ, z2 + z3 + randX, true, max, WorldGeneration.KELP_FOREST_DENSITY);
				        } catch (Exception e) { }
					}
					
					if (world.getChunkProvider().chunkExists(x2 + x3 + randX >> 4, z2 + z3 + randZ >> 4)) {
				        try {
				        	new WorldGenKelp().generate(world, random, x2 + x3 + randX, z2 + z3 + randZ, true, max, WorldGeneration.KELP_FOREST_DENSITY);
				        } catch (Exception e) { }
					}
					
					if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
				        try {
				        	new WorldGenKelp().generate(world, random, x2 + x3 + randX, z2 + z3 + randZ, true, 5, WorldGeneration.KELP_FOREST_DENSITY);
				        } catch (Exception e) { }
					}
				}
			}
		}

		return true;
	}
}
