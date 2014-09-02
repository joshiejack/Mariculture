package joshie.mariculture.fishery.blocks.fluids;

import joshie.mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockPoison extends BlockFluid {
    public BlockPoison(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 9;
        quantaPerBlockFloat = 9;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase && !world.isRemote) {
            EntityLivingBase living = (EntityLivingBase) entity;
            if (!living.isPotionActive(Potion.poison.id)) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1, true));
            }
        }
    }
}
