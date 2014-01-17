package mariculture.fishery;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFishing extends EntityFishHook {
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private boolean inGround;
	public int shake;
	private int ticksInGround;
	private int ticksInAir;
	private int ticksCatchable;
	public Entity bobber;
	private int fishPosRotationIncrements;
	private double fishX;
	private double fishY;
	private double fishZ;
	private double fishYaw;
	private double fishPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;
	private short CATCH_CHANCE = 500;

	public EntityFishing(World world) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.setSize(0.25F, 0.25F);
		this.ignoreFrustumCheck = true;
	}

	@SideOnly(Side.CLIENT)
	public EntityFishing(World world, double x, double y, double z, EntityPlayer player) {
		this(world);
		this.setPosition(x, y, z);
		this.ignoreFrustumCheck = true;
		this.angler = player;
		player.fishEntity = this;
	}

	public EntityFishing(World world, EntityPlayer player, int baitQuality) {
		super(world);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.ignoreFrustumCheck = true;
		this.angler = player;
		this.angler.fishEntity = this;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(player.posX, player.posY + 1.62D - (double) player.yOffset, player.posZ,
				player.rotationYaw, player.rotationPitch);
		this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f = 0.4F;
		this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * f);
		this.calculateVelocity(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
		this.CATCH_CHANCE = (short) baitQuality;
	}

	@Override
	public void onUpdate() {
		if (this.fishPosRotationIncrements > 0) {
			double x = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
			double y = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
			double z = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
			double var7 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
			this.rotationYaw = (float) (this.rotationYaw + var7 / this.fishPosRotationIncrements);
			this.rotationPitch = (float) (this.rotationPitch + (this.fishPitch - this.rotationPitch)
					/ this.fishPosRotationIncrements);
			--this.fishPosRotationIncrements;
			this.setPosition(x, y, z);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		} else {
			if (!this.worldObj.isRemote) {
				if(this.angler == null || this.angler.getCurrentEquippedItem() == null) {
					this.setDead();
					return;
				}
				
				ItemStack var1 = this.angler.getCurrentEquippedItem();

				if (this.angler.isDead || !this.angler.isEntityAlive() || var1 == null
						|| !(var1.getItem() instanceof ItemBaseRod)
						|| this.getDistanceSqToEntity(this.angler) > 1024.0D) {
					this.setDead();
					this.angler.fishEntity = null;
					return;
				}

				if (this.bobber != null) {
					if (!this.bobber.isDead) {
						this.posX = this.bobber.posX;
						this.posY = this.bobber.boundingBox.minY + this.bobber.height * 0.8D;
						this.posZ = this.bobber.posZ;
						return;
					}

					this.bobber = null;
				}
			}

			if (this.shake > 0) {
				--this.shake;
			}

			if (this.inGround) {
				int var19 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

				if (var19 == this.inTile) {
					++this.ticksInGround;

					if (this.ticksInGround == 1200) {
						this.setDead();
					}

					return;
				}

				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				++this.ticksInAir;
			}

			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX,
					this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.clip(vec3, vec31);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY,
					this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord,
						movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
					this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d4 = 0.0D;
			double d5;

			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = (Entity) list.get(j);

				if (entity1.canBeCollidedWith() && (entity1 != this.angler || this.ticksInAir >= 5)) {
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null) {
						d5 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d5 < d4 || d4 == 0.0D) {
							entity = entity1;
							d4 = d5;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null) {
				if (movingobjectposition.entityHit != null) {
					if (movingobjectposition.entityHit.attackEntityFrom(
							DamageSource.causeThrownDamage(this, this.angler), 0.0F)) {
						this.bobber = movingobjectposition.entityHit;
					}
				} else {
					this.inGround = true;
				}
			}

			if (!this.inGround) {
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				float var24 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

				for (this.rotationPitch = (float) (Math.atan2(this.motionY, var24) * 180.0D / Math.PI); this.rotationPitch
						- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
					;
				}

				while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
					this.prevRotationPitch += 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
					this.prevRotationYaw -= 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
					this.prevRotationYaw += 360.0F;
				}

				this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
				this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
				float f2 = 0.92F;

				if (this.onGround || this.isCollidedHorizontally) {
					f2 = 0.5F;
				}

				byte b0 = 5;
				double d6 = 0.0D;

				for (int var29 = 0; var29 < b0; ++var29) {
					double d7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var29 + 0)
							/ b0 - 0.125D + 0.125D;
					double d8 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var29 + 1)
							/ b0 - 0.125D + 0.125D;
					AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d7,
							this.boundingBox.minZ, this.boundingBox.maxX, d8, this.boundingBox.maxZ);

					if (this.worldObj.isAABBInMaterial(axisalignedbb1, Material.water)
							|| this.worldObj.isAABBInMaterial(axisalignedbb1, Material.lava)) {
						d6 += 1.0D / (double) b0;
					}
				}

				if (d6 > 0.0D) {
					if (this.ticksCatchable > 0) {
						--this.ticksCatchable;
					} else {
						short catchChance = CATCH_CHANCE;
						if (rand.nextInt(2000) < catchChance) {
							this.ticksCatchable = this.rand.nextInt(60) + 20;
							this.motionY -= 0.20000000298023224D;
							this.worldObj.playSoundAtEntity(this.angler, "random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
							
							float var30 = MathHelper.floor_double(this.boundingBox.minY);
							int l;
							float f4;
							float f5;

							for (l = 0; l < 1.0F + this.width * 20.0F; ++l) {
								f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								this.worldObj.spawnParticle("bubble", this.posX + f5, var30 + 1.0F, this.posZ + f4,
										this.motionX, this.motionY - this.rand.nextFloat() * 0.2F, this.motionZ);
							}

							for (l = 0; l < 1.0F + this.width * 20.0F; ++l) {
								f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								this.worldObj.spawnParticle("splash", this.posX + f5, var30 + 1.0F, this.posZ + f4,
										this.motionX, this.motionY, this.motionZ);
							}
						}
					}
				}

				if (this.ticksCatchable > 0) {
					this.motionY -= (double) (this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2D;
				}

				d5 = d6 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d5;

				if (d6 > 0.0D) {
					f2 = (float) ((double) f2 * 0.9D);
					this.motionY *= 0.8D;
				}

				this.motionX *= (double) f2;
				this.motionY *= (double) f2;
				this.motionZ *= (double) f2;
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) this.xTile);
		tagCompound.setShort("yTile", (short) this.yTile);
		tagCompound.setShort("zTile", (short) this.zTile);
		tagCompound.setShort("catchChance", this.CATCH_CHANCE);
		tagCompound.setByte("inTile", (byte) this.inTile);
		tagCompound.setByte("shake", (byte) this.shake);
		tagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) {
		this.xTile = tagCompound.getShort("xTile");
		this.yTile = tagCompound.getShort("yTile");
		this.zTile = tagCompound.getShort("zTile");
		this.CATCH_CHANCE = tagCompound.getShort("catchChance");
		this.inTile = tagCompound.getByte("inTile") & 255;
		this.shake = tagCompound.getByte("shake") & 255;
		this.inGround = tagCompound.getByte("inGround") == 1;
	}

	@Override
	public int catchFish() {
		if (this.worldObj.isRemote) {
			return 0;
		} else {
			byte b0 = 0;

			if (this.bobber != null) {
				double d0 = this.angler.posX - this.posX;
				double d1 = this.angler.posY - this.posY;
				double d2 = this.angler.posZ - this.posZ;
				double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
				double d4 = 0.1D;
				this.bobber.motionX += d0 * d4;
				this.bobber.motionY += d1 * d4 + (double) MathHelper.sqrt_double(d3) * 0.08D;
				this.bobber.motionZ += d2 * d4;
				b0 = 3;
			} else if (this.ticksCatchable > 0
					&& this.angler.getCurrentEquippedItem().getItem() instanceof ItemBaseRod) {
				EnumRodQuality quality = ((ItemBaseRod) this.angler.getCurrentEquippedItem().getItem()).getQuality();
				ItemStack loot = Fishing.loot.getLoot(new Random(), quality, this.worldObj, (int) this.posX,
						(int) this.posY, (int) this.posZ);
				if (loot != null) {
					EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, loot);
					entityItem.fireResistance = 200;
					double var3 = this.angler.posX - this.posX;
					double var5 = this.angler.posY - this.posY;
					double var7 = this.angler.posZ - this.posZ;
					double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
					double var11 = 0.1D;
					entityItem.motionX = var3 * var11;
					entityItem.motionY = var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D;
					entityItem.motionZ = var7 * var11;
					this.worldObj.spawnEntityInWorld(entityItem);
					this.angler.addStat(StatList.fishCaughtStat, 1);
					this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX,
							this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
					b0 = 1;
				} else {
					this.ticksCatchable = this.rand.nextInt(120) + 40;
				}
			}

			if (this.inGround) {
				b0 = 2;
			}

			this.setDead();
			this.angler.fishEntity = null;
			return b0;
		}
	}
}
