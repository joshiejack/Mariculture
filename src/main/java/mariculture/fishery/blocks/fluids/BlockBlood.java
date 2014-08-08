package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockBlood extends BlockFluid {
    public BlockBlood(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 2;
        quantaPerBlockFloat = 2;
        tickRate = 45;
    }

    @Override
    public void velocityToAddToEntity(World world, int x, int y, int z, Entity entity, Vec3 vec) {
        entity.motionX /= 2;
        entity.motionZ /= 2;
        entity.motionY /= 2;

        if (entity.motionY < -0.05) {
            entity.motionY = -0.05;
        }
    }
}
