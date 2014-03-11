package mariculture.world.decorate;

import java.util.Random;

import mariculture.core.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenKelp extends WorldGenerator {
    private Block block;
    private int meta;

    public WorldGenKelp(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
    	for(int l = 0; l < 128; l++) {
    		int i1 = x + rand.nextInt(8) - rand.nextInt(8); 
    		int k1 = z + rand.nextInt(8) - rand.nextInt(8);
    		int j1 = 62;
    		
    		do {
    			j1--;
    		} while (BlockHelper.isWater(world, i1, j1, k1));
    		
    		for(int i = 0; j1 + i < j1 + rand.nextInt(5); j1++) {
    			if (i == 0 && world.getBlock(i1, j1, k1) == block) world.setBlockMetadataWithNotify(i1, j1, k1, meta, 2);
    			if(BlockHelper.isWater(world, i1, j1 + 2, k1)) world.setBlock(i1, j1 + 1, k1, block, meta, 2);
    			else break;
    		}
    		
    		if(world.getBlock(i1, j1, k1) == block) world.setBlock(i1, j1, k1, block, meta - 1, 2);
    	}

        return true;
    }
}
