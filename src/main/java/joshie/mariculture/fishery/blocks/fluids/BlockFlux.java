package joshie.mariculture.fishery.blocks.fluids;

import java.util.Random;

import joshie.mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
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
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(8) == 0) {
            world.addWeatherEffect(new EntityLightningBolt(world, x + world.rand.nextInt(5) - world.rand.nextInt(10), y, z + world.rand.nextInt(5) - world.rand.nextInt(10)));
        }

        super.updateTick(world, x, y, z, rand);
    }
}
