package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFlux extends BlockFluid {
    public BlockFlux(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 9;
        quantaPerBlockFloat = 9;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase && !world.isRemote && world.rand.nextInt(64) == 0) {
            world.addWeatherEffect(new EntityLightningBolt(world, x + world.rand.nextInt(5) - world.rand.nextInt(10), y, z + world.rand.nextInt(5) - world.rand.nextInt(10)));
        }
    }
}
