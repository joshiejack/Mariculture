package maritech.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFLUDDSquirt extends EntityThrowable {
    private boolean damage = false;
    public float size = 1F;

    public EntityFLUDDSquirt(World world) {
        super(world);
    }

    public EntityFLUDDSquirt(World world, EntityPlayer entity, boolean damage) {
        super(world, entity);
        this.damage = damage;
    }

    public EntityFLUDDSquirt(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        for (int l = 0; l < 90; ++l) {
            worldObj.spawnParticle("cloud", posX + motionX * l, posY - 0.5D, posZ + motionZ * l, 0, 0, 0);
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition thingHit) {
        if (thingHit.entityHit != null && damage) {
            DamageSource source = null;
            if (thingHit.entityHit instanceof EntityPlayer) {
                source = new EntityDamageSourceIndirect("fludd", this, getThrower()).setProjectile();
            } else {
                source = new EntityDamageSource("fludd", this).setProjectile();
            }
            
            thingHit.entityHit.attackEntityFrom(source, 10);
        }

        for (int var5 = 0; var5 < 8; ++var5) {
            worldObj.spawnParticle("splash", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote) {
            setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setBoolean("damage", damage);
        tagCompound.setFloat("size", size);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        damage = tagCompound.getBoolean("damage");
        size = tagCompound.getFloat("size");
    }

    public void setSize(final float size) {
        this.size = size;
    }
}
