package mariculture.transport;

import java.util.List;

import mariculture.core.helpers.SpawnItemHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySpeedBoat extends Entity {
	public boolean isBoatEmpty;
	public double speedMultiplier;
	public int boatPosRotationIncrements;
	public double boatX;
	public double boatY;
	public double boatZ;
	public double boatYaw;
	public double boatPitch;
	@SideOnly(Side.CLIENT)
	public double velocityX;
	@SideOnly(Side.CLIENT)
	public double velocityY;
	@SideOnly(Side.CLIENT)
	public double velocityZ;
	public int ticker;
	public float motorPos;
	private boolean goneRight = false;

	public EntitySpeedBoat(World par1World) {
		super(par1World);
		this.isBoatEmpty = true;
		this.speedMultiplier = 0.15D;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
		return par1Entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public EntitySpeedBoat(World par1World, double par2, double par4, double par6) {
		this(par1World);
		this.setPosition(par2, par4 + (double) this.yOffset, par6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = par2;
		this.prevPosY = par4;
		this.prevPosZ = par6;
	}

	public double getMountedYOffset() {
		return (double) this.height * 0.0D - 0.30000001192092896D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isDead) {
			ItemStack boat = new ItemStack(Transport.speedBoat);
			boolean flag = source.getEntity() instanceof EntityPlayer;
			if (flag) {
				EntityPlayer player = (EntityPlayer) source.getEntity();
				if (!player.capabilities.isCreativeMode) {
					if (!player.inventory.addItemStackToInventory(boat)) {
						if (!worldObj.isRemote) {
							SpawnItemHelper.spawnItem(worldObj, (int) posX, (int) posY, (int) posZ, boat);
						}
					}
				}

				if (!worldObj.isRemote) {
					this.setDead();
				}
			}
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
		if (this.isBoatEmpty) {
			this.boatPosRotationIncrements = par9 + 7;
		} else {
			double d3 = par1 - this.posX;
			double d4 = par3 - this.posY;
			double d5 = par5 - this.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D) {
				return;
			}

			this.boatPosRotationIncrements = 5;
		}

		this.boatX = par1;
		this.boatY = par3;
		this.boatZ = par5;
		this.boatYaw = (double) par7;
		this.boatPitch = (double) par8;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5) {
		this.velocityX = this.motionX = par1;
		this.velocityY = this.motionY = par3;
		this.velocityZ = this.motionZ = par5;
	}

	public void onUpdate() {
		super.onUpdate();

		this.isCollidedHorizontally = false;

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
			double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 0) / (double) b0 - 0.125D;
			double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 1) / (double) b0 - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);

			if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d0 += 1.0D / (double) b0;
			}
		}

		double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d2;
		double d4;
		int j;

		if (d10 > 0.26249999999999996D) {
			d2 = Math.cos((double) this.rotationYaw * Math.PI / 180.0D);
			d4 = Math.sin((double) this.rotationYaw * Math.PI / 180.0D);

			for (j = 0; (double) j < 1.0D + d10 * 60.0D; ++j) {
				double d5 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
				double d6 = (double) (this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double d8;
				double d9;

				if (this.rand.nextBoolean()) {
					d8 = this.posX - d2 * d5 * 0.8D + d4 * d6;
					d9 = this.posZ - d4 * d5 * 0.8D - d2 * d6;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				} else {
					d8 = this.posX + d2 + d4 * d5 * 0.7D;
					d9 = this.posZ + d4 - d2 * d5 * 0.7D;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
			}
		}

		double d11;
		double d12;

		if (this.worldObj.isRemote && this.isBoatEmpty) {
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
				d2 = this.posX + (this.boatX - this.posX) / (double) this.boatPosRotationIncrements;
				d4 = this.posY + (this.boatY - this.posY) / (double) this.boatPosRotationIncrements;
				d11 = this.posZ + (this.boatZ - this.posZ) / (double) this.boatPosRotationIncrements;
				d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + d12 / (double) this.boatPosRotationIncrements);
				this.rotationPitch = (float) ((double) this.rotationPitch + (this.boatPitch - (double) this.rotationPitch) / (double) this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(d2, d4, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				d2 = this.posX + this.motionX;
				d4 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d2, d4, d11);

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
				d2 = d0 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d2;
			} else {
				if (this.motionY < 0.0D) {
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;
				float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
				this.motionX += -Math.sin((double) (f * (float) Math.PI / 180.0F)) * this.speedMultiplier * (double) entitylivingbase.moveForward * 0.05000000074505806D;
				this.motionZ += Math.cos((double) (f * (float) Math.PI / 180.0F)) * this.speedMultiplier * (double) entitylivingbase.moveForward * 0.05000000074505806D;
			}

			d2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (d2 > 0.35D) {
				d4 = 0.35D / d2;
				this.motionX *= d4;
				this.motionZ *= d4;
				d2 = 0.35D;
			}

			if (d2 > d10 && this.speedMultiplier < 10D) {
				this.speedMultiplier += (25D - this.speedMultiplier) / 150.0D;

				if (this.speedMultiplier > 10D) {
					this.speedMultiplier = 10D;
				}
			} else {
				this.speedMultiplier -= (this.speedMultiplier - 15D) / 150.0D;

				if (this.speedMultiplier < 0.07D) {
					this.speedMultiplier = 0.07D;
				}
			}

			if (!worldObj.isRemote) {
				for (int i = -1; i < 2; i++) {
					for (int k = -1; k < 2; k++) {
						if (this.worldObj.getBlock((int) this.posX + i, (int) this.posY, (int) this.posZ + k) == Blocks.waterlily) {
							this.worldObj.func_147480_a((int) this.posX + i, (int) this.posY, (int) this.posZ + k, true);
						}
					}
				}

				List list = worldObj.getEntitiesWithinAABB(EntitySquid.class, this.getBoundingBox().expand(1.0D, 1.0D, 1.0D));
				if (!list.isEmpty()) {
					for (Object i : list) {
						EntitySquid squid = (EntitySquid) i;
						squid.attackEntityFrom(DamageSource.inWall, 25F);
					}
				}
			}

			if (this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			if (this.riddenByEntity == null) {
				this.motionX = 0D;
				this.motionY = 0D;
				this.motionZ = 0D;
			} else {
				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}

			this.rotationPitch = 0.0F;
			d4 = (double) this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d12 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d12 * d12 > 0.001D) {
				d4 = (double) ((float) (Math.atan2(d12, d11) * 180.0D / Math.PI));
			}

			double d7 = MathHelper.wrapAngleTo180_double(d4 - (double) this.rotationYaw);

			if (d7 > 20.0D) {
				d7 = 20.0D;
			}

			if (d7 < -20.0D) {
				d7 = -20.0D;
			}

			this.rotationYaw = (float) ((double) this.rotationYaw + d7);
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if (!this.worldObj.isRemote) {
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
				if (list != null && !list.isEmpty()) {
					for (int k1 = 0; k1 < list.size(); ++k1) {
						Entity entity = (Entity) list.get(k1);

						if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
							entity.applyEntityCollision(this);
						}
					}
				}

				if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
					this.riddenByEntity = null;
				}
			}
		}
	}

	public void updateRiderPosition() {
		if (this.riddenByEntity != null) {
			double d0 = Math.cos((double) this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double d1 = Math.sin((double) this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}

	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
	}

	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public boolean interactFirst(EntityPlayer par1EntityPlayer) {
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer) {
			return true;
		} else {
			if (!this.worldObj.isRemote) {
				par1EntityPlayer.mountEntity(this);
			}

			return true;
		}
	}

	protected void updateFallState(double par1, boolean par3) {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);

		if (par3) {
			if (this.fallDistance > 3.0F) {
				this.fall(this.fallDistance);
				this.fallDistance = 0.0F;
			}
		} else if (this.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && par1 < 0.0D) {
			this.fallDistance = (float) ((double) this.fallDistance - par1);
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
	public void setIsBoatEmpty(boolean par1) {
		this.isBoatEmpty = par1;
	}
}