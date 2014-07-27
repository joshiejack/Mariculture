package mariculture.fishery.blocks.fluids;

import java.util.Random;

import mariculture.core.blocks.base.BlockFluid;
import mariculture.core.helpers.PlayerHelper;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockGunpowder extends BlockFluid {
    public BlockGunpowder(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 12;
        quantaPerBlockFloat = 12;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(32) == 0) {
            world.createExplosion(PlayerHelper.getFakePlayer(world), x, y, z, 1.5F, false);
        }
        
        super.updateTick(world, x, y, z, rand);
    }
}
