package mariculture.factory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
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
			this.worldObj.spawnParticle("cloud", this.posX + (this.motionX * l), this.posY, this.posZ + (this.motionZ * l), 0, 0, 0);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition thingHit) {
		if (thingHit.entityHit != null && damage) {
			DamageSource source = (new EntityDamageSourceIndirect("fludd", this, this.getThrower())).setProjectile();
			thingHit.entityHit.attackEntityFrom(source, 10);
		}

		for (int var5 = 0; var5 < 8; ++var5) {
			this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setBoolean("damage", this.damage);
		tagCompound.setFloat("size", this.size);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		this.damage = tagCompound.getBoolean("damage");
		this.size = tagCompound.getFloat("size");
	}

	public void setSize(final float size) {
		this.size = size;
	}
}
