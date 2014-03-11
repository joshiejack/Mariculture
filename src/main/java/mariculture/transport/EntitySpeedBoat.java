package mariculture.transport;

import java.util.List;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.SpawnItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
    private boolean isBoatEmpty;
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

    public EntitySpeedBoat(World world) {
        super(world);
        isBoatEmpty = true;
        speedMultiplier = 0.3D;
        preventEntitySpawning = true;
        setSize(1.5F, 0.6F);
        yOffset = height / 2.0F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        dataWatcher.addObject(17, new Integer(0));
        dataWatcher.addObject(18, new Integer(1));
        dataWatcher.addObject(19, new Float(0.0F));
    }

    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.boundingBox;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public boolean canBePushed() {
        return true;
    }

    public EntitySpeedBoat(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y + (double)yOffset, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
    }

    public double getMountedYOffset() {
        return (double)height * 0.0D - 0.30000001192092896D;
    }

    public boolean attackEntityFrom(DamageSource source, float par2) {
    	if (!isDead) {
    		ItemStack boat = new ItemStack(Transport.speedBoat);
			if (source.getEntity() instanceof EntityPlayer
					&& !((EntityPlayer) source.getEntity()).capabilities.isCreativeMode) {
				if(source.getEntity() != null && source.getEntity() instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) source.getEntity();
					if (!player.inventory.addItemStackToInventory(boat)) {
						if(!worldObj.isRemote) {
							SpawnItemHelper.spawnItem(worldObj, (int)posX, (int)posY, (int)posZ, boat);
						}
					}
				} else if(!worldObj.isRemote) {
					entityDropItem(boat, 0.0F);
				}
			}

			if(!worldObj.isRemote) setDead();
		}

		return true;
    }

    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        setForwardDirection(-getForwardDirection());
        setTimeSinceHit(10);
        setDamageTaken(getDamageTaken() * 11.0F);
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int rotation) {
        if (isBoatEmpty) {
            boatPosRotationIncrements = rotation + 5;
        } else {
            double d3 = x - posX;
            double d4 = y - posY;
            double d5 = z - posZ;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;

            if (d6 <= 1.0D) {
                return;
            }

            boatPosRotationIncrements = 30;
        }

        boatX = x;
        boatY = y;
        boatZ = z;
        boatYaw = (double)yaw;
        boatPitch = (double)pitch;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z) {
        velocityX = motionX = x;
        velocityY = motionY = y;
        velocityZ = motionZ = z;
    }

    public void onUpdate() {
        super.onUpdate();

        if (getTimeSinceHit() > 0) {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }

        if (getDamageTaken() > 0.0F) {
            setDamageTaken(getDamageTaken() - 1.0F);
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        byte b0 = 5;
        double d0 = 0.0D;

        for (int i = 0; i < b0; ++i) {
            double d1 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (double)(i + 0) / (double)b0 - 0.125D;
            double d3 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (double)(i + 1) / (double)b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(boundingBox.minX, d1, boundingBox.minZ, boundingBox.maxX, d3, boundingBox.maxZ);

            if (worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0D / (double)b0;
            }
        }

        double d10 = Math.sqrt(motionX * motionX + motionZ * motionZ);
        double d2;
        double d4;
        int j;

        if (d10 > 0.26249999999999996D) {
            d2 = Math.cos((double)rotationYaw * Math.PI / 180.0D);
            d4 = Math.sin((double)rotationYaw * Math.PI / 180.0D);

            for (j = 0; (double)j < 1.0D + d10 * 60.0D; ++j) {
                double d5 = (double)(rand.nextFloat() * 2.0F - 1.0F);
                double d6 = (double)(rand.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;

                if (rand.nextBoolean()) {
                    d8 = posX - d2 * d5 * 0.8D + d4 * d6;
                    d9 = posZ - d4 * d5 * 0.8D - d2 * d6;
                    worldObj.spawnParticle("splash", d8, posY - 0.125D, d9, motionX, motionY, motionZ);
                } else {
                    d8 = posX + d2 + d4 * d5 * 0.7D;
                    d9 = posZ + d4 - d2 * d5 * 0.7D;
                    worldObj.spawnParticle("splash", d8, posY - 0.125D, d9, motionX, motionY, motionZ);
                }
            }
        }

        double d11;
        double d12;

        if (worldObj.isRemote && isBoatEmpty) {
        	if (riddenByEntity != null) {
				if (!goneRight) {
					motorPos = motorPos + 0.1F;
					if (motorPos > 0.15F) {
						goneRight = true;
					}
				} else {
					motorPos = motorPos - 0.1F;
					if (motorPos < -0.15F) {
						goneRight = false;
					}
				}
			}
        	
            if (boatPosRotationIncrements > 0) {
                d2 = posX + (boatX - posX) / (double)boatPosRotationIncrements;
                d4 = posY + (boatY - posY) / (double)boatPosRotationIncrements;
                d11 = posZ + (boatZ - posZ) / (double)boatPosRotationIncrements;
                d12 = MathHelper.wrapAngleTo180_double(boatYaw - (double)rotationYaw);
                rotationYaw = (float)((double)rotationYaw + d12 / (double)boatPosRotationIncrements);
                rotationPitch = (float)((double)rotationPitch + (boatPitch - (double)rotationPitch) / (double)boatPosRotationIncrements);
                --boatPosRotationIncrements;
                setPosition(d2, d4, d11);
                setRotation(rotationYaw, rotationPitch);
            } else  {
                d2 = posX + motionX;
                d4 = posY + motionY;
                d11 = posZ + motionZ;
                setPosition(d2, d4, d11);

                if (onGround) {
                    motionX *= 0.5D;
                    motionY *= 0.5D;
                    motionZ *= 0.5D;
                }

                motionX *= 0.9900000095367432D;
                motionY *= 0.949999988079071D;
                motionZ *= 0.9900000095367432D;
            }
        } else {
            if (d0 < 1.0D) {
                d2 = d0 * 2.0D - 1.0D;
                motionY += 0.03999999910593033D * d2;
            } else  {
                if (motionY < 0.0D) {
                    motionY /= 2.0D;
                }

                motionY += 0.007000000216066837D;
            }

            if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase)riddenByEntity;
                float f = riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
                motionX += -Math.sin((double)(f * (float)Math.PI / 180.0F)) * speedMultiplier * (double)entitylivingbase.moveForward * 0.05000000074505806D;
                motionZ += Math.cos((double)(f * (float)Math.PI / 180.0F)) * speedMultiplier * (double)entitylivingbase.moveForward * 0.05000000074505806D;
            }

            d2 = Math.sqrt(motionX * motionX + motionZ * motionZ);

            if (d2 > 1D) {
                d4 = 1D / d2;
                motionX *= d4;
                motionZ *= d4;
                d2 = 1D;
            }

            if (d2 > d10 && speedMultiplier < 1D) {
                speedMultiplier += (1D - speedMultiplier) / 100.0D;

                if (speedMultiplier > 1D) {
                    speedMultiplier = 1D;
                }
            } else {
                speedMultiplier -= (speedMultiplier - 0.1D) / 100.0D;

                if (speedMultiplier < 0.1D) {
                    speedMultiplier = 0.1D;
                }
            }

            int l;

            for (l = 0; l < 4; ++l) {
                int i1 = MathHelper.floor_double(posX + ((double)(l % 2) - 0.5D) * 0.8D);
                j = MathHelper.floor_double(posZ + ((double)(l / 2) - 0.5D) * 0.8D);

                for (int j1 = 0; j1 < 2; ++j1) {
                    int k = MathHelper.floor_double(posY) + j1;
                    Block block = worldObj.getBlock(i1, k, j);

                    if (block == Blocks.snow_layer) {
                        worldObj.setBlockToAir(i1, k, j);
                        isCollidedHorizontally = false;
                    } else if (block == Blocks.waterlily) {
                        worldObj.func_147480_a(i1, k, j, true);
                        isCollidedHorizontally = false;
                    }
                }
            }

            if (onGround) {
                motionX *= 0.5D;
                motionY *= 0.5D;
                motionZ *= 0.5D;
            }

            if (riddenByEntity != null || onGround) moveEntity(motionX, motionY, motionZ);
            if (!(isCollidedHorizontally && d10 > 0.2D)) {
            	motionX *= 0.9900000095367432D;
                motionY *= 0.949999988079071D;
                motionZ *= 0.9900000095367432D;
            }

            rotationPitch = 0.0F;
            d4 = (double)rotationYaw;
            d11 = prevPosX - posX;
            d12 = prevPosZ - posZ;

            if (d11 * d11 + d12 * d12 > 0.001D) {
                d4 = (double)((float)(Math.atan2(d12, d11) * 180.0D / Math.PI));
            }

            double d7 = MathHelper.wrapAngleTo180_double(d4 - (double)rotationYaw);

            if (d7 > 20.0D) {
                d7 = 20.0D;
            }

            if (d7 < -20.0D) {
                d7 = -20.0D;
            }

            rotationYaw = (float)((double)rotationYaw + d7);
            setRotation(rotationYaw, rotationPitch);
            for(int i = -2; i < 3; i++) {
				for(int k = -2; k < 3; k++) {
					if(this.worldObj.getBlock((int)posX + i, (int)posY, (int)posZ + k) == Blocks.waterlily) {
						BlockHelper.destroyBlock(worldObj, (int)posX, (int)posY, (int)posZ);
					}
				}
			}

            if (!worldObj.isRemote) {
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty()) {
                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        Entity entity = (Entity)list.get(k1);

                        if (entity != riddenByEntity && entity.canBePushed() && entity instanceof EntitySpeedBoat) {
                            entity.applyEntityCollision(this);
                        }
                    }
                }

                if (riddenByEntity != null && riddenByEntity.isDead) {
                    riddenByEntity = null;
                }
            }
        }
    }

    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            double d0 = Math.cos((double)rotationYaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin((double)rotationYaw * Math.PI / 180.0D) * 0.4D;
            riddenByEntity.setPosition(posX + d0, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
        }
    }

    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    public boolean interactFirst(EntityPlayer player) {
        if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != player) {
            return true;
        } else {
            if (!worldObj.isRemote) {
                player.mountEntity(this);
            }

            return true;
        }
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double par1, boolean par3) {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);

        if (par3) {
            if (fallDistance > 3.0F)  {
                fall(fallDistance);
                fallDistance = 0.0F;
            }
        } else if (worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && par1 < 0.0D) {
            fallDistance = (float)((double)fallDistance - par1);
        }
    }

    public void setDamageTaken(float par1) {
        dataWatcher.updateObject(19, Float.valueOf(par1));
    }

    public float getDamageTaken() {
        return dataWatcher.getWatchableObjectFloat(19);
    }

    public void setTimeSinceHit(int par1) {
        dataWatcher.updateObject(17, Integer.valueOf(par1));
    }

    public int getTimeSinceHit() {
        return dataWatcher.getWatchableObjectInt(17);
    }

    public void setForwardDirection(int par1) {
        dataWatcher.updateObject(18, Integer.valueOf(par1));
    }

    public int getForwardDirection() {
        return dataWatcher.getWatchableObjectInt(18);
    }

    @SideOnly(Side.CLIENT)
    public void setIsBoatEmpty(boolean par1) {
        isBoatEmpty = par1;
    }
}