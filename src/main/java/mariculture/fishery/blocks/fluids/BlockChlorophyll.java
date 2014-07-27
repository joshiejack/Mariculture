package mariculture.fishery.blocks.fluids;

import java.util.Random;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockChlorophyll extends BlockFluid {
    public BlockChlorophyll(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 7;
        quantaPerBlockFloat = 7;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(32) == 0) {
            int x2 = x + world.rand.nextInt(16);
            int z2 = z + world.rand.nextInt(16);
            int y2 = world.getTopSolidOrLiquidBlock(x2, z2);
            world.getBiomeGenForCoords(x2, z2).plantFlower(world, world.rand, x2, y2, z2);
        }
        
        super.updateTick(world, x, y, z, rand);
    }
}
