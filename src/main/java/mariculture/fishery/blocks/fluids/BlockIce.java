package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockIce extends BlockFluid {
    public BlockIce(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 3;
        quantaPerBlockFloat = 3;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase && !world.isRemote) {
            EntityLivingBase living = (EntityLivingBase) entity;
            if (!living.isPotionActive(Potion.moveSlowdown.id)) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2, true));
            }
        }
    }
}
