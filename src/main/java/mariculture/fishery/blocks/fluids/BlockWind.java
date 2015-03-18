package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockWind extends BlockFluid {
    public BlockWind(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 32;
        quantaPerBlockFloat = 32;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        entity.motionX += 0.5D - world.rand.nextDouble();
        entity.motionY += 0.5D - world.rand.nextDouble();
        entity.motionZ += 0.5D - world.rand.nextDouble();
    }
}
