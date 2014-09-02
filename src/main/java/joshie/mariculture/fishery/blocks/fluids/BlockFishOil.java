package joshie.mariculture.fishery.blocks.fluids;

import joshie.mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFishOil extends BlockFluid {
    public BlockFishOil(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 7;
        quantaPerBlockFloat = 7;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase && !world.isRemote) {
            EntityLivingBase living = (EntityLivingBase) entity;
            if (!living.isPotionActive(Potion.regeneration.id)) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0, true));
            }
        }
    }
}
