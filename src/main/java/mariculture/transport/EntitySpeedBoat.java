package mariculture.transport;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySpeedBoat extends Entity {
	private boolean field_70279_a;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public float motorPos = 0F;
	private boolean goneRight = false;

	public EntitySpeedBoat(World par1World) {
		super(par1World);
		this.field_70279_a = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
		return par1Entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntitySpeedBoat(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y + this.yOffset, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	public double getMountedYOffset() {
		return this.height * 0.0D - 0.30000001192092896D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (!this.worldObj.isRemote && !this.isDead) {
			if (par1DamageSource.getEntity() instanceof EntityPlayer
					&& !((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode) {
				this.dropItemWithOffset(Transport.speedBoat.itemID, 1, 0.0F);
			}

			this.setDead();
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int rotation) {
		if (this.field_70279_a) {
			this.boatPosRotationIncrements = rotation + 5;
		} else {
			double d3 = x - this.posX;
			double d4 = y - this.posY;
			double d5 = z - this.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D) {
				return;
			}

			this.boatPosRotationIncrements = 30;
		}

		this.boatX = x;
		this.boatY = y;
		this.boatZ = z;
		this.boatYaw = yaw;
		this.boatPitch = pitch;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5) {
		this.velocityX = this.motionX = par1;
		this.velocityY = this.motionY = par3;
		this.velocityZ = this.motionZ = par5;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i) {
			double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125D;
			double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ,
					this.boundingBox.maxX, d2, this.boundingBox.maxZ);

			if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d0 += 1.0D / b0;
			}
		}

		double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d4;
		double d5;

		if (d3 > 0.26249999999999996D) {
			d4 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
			d5 = Math.sin(this.rotationYaw * Math.PI / 180.0D);

			for (int j = 0; j < 1.0D + d3 * 60.0D; ++j) {
				double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
				double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double d8;
				double d9;

				if (this.rand.nextBoolean()) {
					d8 = this.posX - d4 * d6 * 0.8D + d5 * d7;
					d9 = this.posZ - d5 * d6 * 0.8D - d4 * d7;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				} else {
					d8 = this.posX + d4 + d5 * d6 * 0.7D;
					d9 = this.posZ + d5 - d4 * d6 * 0.7D;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
			}
		}

		double d10;
		double d11;

		if (this.worldObj.isRemote && this.field_70279_a) {
			if (this.riddenByEntity != null) {
				if (!goneRight) {
					this.motorPos = this.motorPos + 0.1F;
					if (this.motorPos > 0.15F) {
						goneRight = true;
					}
				} else {
					this.motorPos = this.motorPos - 0.1F;
					if (this.motorPos < -0.15F) {
						goneRight = false;
					}
				}
			}

			if (this.boatPosRotationIncrements > 0) {
				d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
				d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
				d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
				d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + d10 / this.boatPosRotationIncrements);
				this.rotationPitch = (float) (this.rotationPitch + (this.boatPitch - this.rotationPitch)
						/ this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(d4, d5, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				d4 = this.posX + this.motionX;
				d5 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d4, d5, d11);

				if (this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}
		} else {
			if (d0 < 1.0D) {
				d4 = d0 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d4;
			} else {
				if (this.motionY < 0.0D) {
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
				d4 = ((EntityLivingBase) this.riddenByEntity).moveForward;

				if (d4 > 0.0D) {
					d5 = -Math.sin(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
					d11 = Math.cos(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
					this.motionX += d5 * this.speedMultiplier * 0.05000000074505806D;
					this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;

					this.motionX *= 2F;
					this.motionZ *= 2F;
				}
			}

			d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (d4 > 0.35D) {
				d5 = 0.35D / d4;
				this.motionX *= d5;
				this.motionZ *= d5;
				d4 = 0.35D;
			}

			if (d4 > d3 && this.speedMultiplier < 0.35D) {
				this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

				if (this.speedMultiplier > 0.35D) {
					this.speedMultiplier = 0.35D;
				}
			} else {
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if (this.speedMultiplier < 0.07D) {
					this.speedMultiplier = 0.07D;
				}
			}

			if (this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			if (this.riddenByEntity != null || this.onGround) {
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
			} else {
				this.motionX = 0F;
				this.motionZ = 0F;
			}
			if (this.isCollidedHorizontally && d3 > 0.2D) {
			} else {
				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}

			this.rotationPitch = 0.0F;
			d5 = this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d10 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d10 * d10 > 0.001D) {
				d5 = ((float) (Math.atan2(d10, d11) * 180.0D / Math.PI));
			}

			double d12 = MathHelper.wrapAngleTo180_double(d5 - this.rotationYaw);

			if (d12 > 20.0D) {
				d12 = 20.0D;
			}

			if (d12 < -20.0D) {
				d12 = -20.0D;
			}

			this.rotationYaw = (float) (this.rotationYaw + d12);
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if (!this.worldObj.isRemote) {
				
				for(int i = -2; i < 3; i++) {
					for(int j = -2; j < 3; j++) {
						if(this.worldObj.getBlockId((int)this.posX + i, (int)this.posY, (int)this.posZ + j) == Block.waterlily.blockID) {
							this.worldObj.destroyBlock((int)this.posX + i, (int)this.posY, (int)this.posZ + j, true);
						}
					}
				}
				
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
						this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
				int l;

				if (list != null && !list.isEmpty()) {
					for (l = 0; l < list.size(); ++l) {
						Entity entity = (Entity) list.get(l);

						if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntitySpeedBoat) {
							entity.applyEntityCollision(this);
						}
					}
				}

				for (l = 0; l < 4; ++l) {
					int i1 = MathHelper.floor_double(this.posX + (l % 2 - 0.5D) * 0.8D);
					int j1 = MathHelper.floor_double(this.posZ + (l / 2 - 0.5D) * 0.8D);

					for (int k1 = 0; k1 < 2; ++k1) {
						int l1 = MathHelper.floor_double(this.posY) + k1;
						int i2 = this.worldObj.getBlockId(i1, l1, j1);

						if (i2 == Block.snow.blockID) {
							this.worldObj.setBlockToAir(i1, l1, j1);
						} else if (i2 == Block.waterlily.blockID) {
							this.worldObj.destroyBlock(i1, l1, j1, true);
						}
					}
				}

				if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
					this.riddenByEntity = null;
				}
			}
		}
	}

	@Override
	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d0,
					this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public boolean func_130002_c(EntityPlayer par1EntityPlayer) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
			return true;
		} else {
			if (!this.worldObj.isRemote) {
				par1EntityPlayer.mountEntity(this);
			}

			return true;
		}
	}

	public void setDamageTaken(float par1) {
		this.dataWatcher.updateObject(19, Float.valueOf(par1));
	}

	public float getDamageTaken() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	public void setTimeSinceHit(int par1) {
		this.dataWatcher.updateObject(17, Integer.valueOf(par1));
	}

	public int getTimeSinceHit() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	public void setForwardDirection(int par1) {
		this.dataWatcher.updateObject(18, Integer.valueOf(par1));
	}

	public int getForwardDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	@SideOnly(Side.CLIENT)
	public void func_70270_d(boolean par1) {
		this.field_70279_a = par1;
	}
}
