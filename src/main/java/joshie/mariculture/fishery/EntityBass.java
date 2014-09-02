package joshie.mariculture.fishery;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBass extends EntityThrowable {
    public EntityBass(World world) {
        super(world);
    }

    public EntityBass(World world, EntityPlayer entity) {
        super(world, entity);
    }

    public EntityBass(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObject) {
        if (movingObject.entityHit != null) {
            movingObject.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 2);
        }

        if (!worldObj.isRemote) {
            worldObj.createExplosion((Entity) null, posX, posY, posZ, 0.8F, true);
        }

        for (int var5 = 0; var5 < 8; ++var5) {
            worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote) {
            setDead();
        }
    }
}
