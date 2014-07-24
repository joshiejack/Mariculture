package mariculture.transport;

import java.util.List;

import mariculture.core.config.GeneralStuff;
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
        isBoatEmpty = true;
        speedMultiplier = 0.15D;
        preventEntitySpawning = true;
        setSize(1.5F, 0.6F);
        yOffset = height / 2.0F;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(17, new Integer(0));
        dataWatcher.addObject(18, new Integer(1));
        dataWatcher.addObject(19, new Float(0.0F));
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return par1Entity.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntitySpeedBoat(World par1World, double par2, double par4, double par6) {
        this(par1World);
        setPosition(par2, par4 + yOffset, par6);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = par2;
        prevPosY = par4;
        prevPosZ = par6;
    }

    @Override
    public double getMountedYOffset() {
        return height * 0.0D - 0.30000001192092896D;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!isDead) {
            ItemStack boat = new ItemStack(Transport.speedBoat);
            boolean flag = source.getEntity() instanceof EntityPlayer;
            if (flag) {
                EntityPlayer player = (EntityPlayer) source.getEntity();
                if (!player.capabilities.isCreativeMode) if (!player.inventory.addItemStackToInventory(boat)) if (!worldObj.isRemote) {
                    SpawnItemHelper.spawnItem(worldObj, (int) posX, (int) posY, (int) posZ, boat);
                }

                if (!worldObj.isRemote) {
                    setDead();
                }
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        setForwardDirection(-getForwardDirection());
        setTimeSinceHit(10);
        setDamageTaken(getDamageTaken() * 11.0F);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        if (isBoatEmpty) {
            boatPosRotationIncrements = par9 + 7;
        } else {
            double d3 = par1 - posX;
            double d4 = par3 - posY;
            double d5 = par5 - posZ;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;

            if (d6 <= 1.0D) return;

            boatPosRotationIncrements = 5;
        }

        boatX = par1;
        boatY = par3;
        boatZ = par5;
        boatYaw = par7;
        boatPitch = par8;
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        velocityX = motionX = par1;
        velocityY = motionY = par3;
        velocityZ = motionZ = par5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        isCollidedHorizontally = false;

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
            double d1 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (i + 0) / b0 - 0.125D;
            double d3 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (i + 1) / b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(boundingBox.minX, d1, boundingBox.minZ, boundingBox.maxX, d3, boundingBox.maxZ);

            if (worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
                d0 += 1.0D / b0;
            }
        }

        double d10 = Math.sqrt(motionX * motionX + motionZ * motionZ);
        double d2;
        double d4;
        int j;

        if (d10 > 0.26249999999999996D) {
            d2 = Math.cos(rotationYaw * Math.PI / 180.0D);
            d4 = Math.sin(rotationYaw * Math.PI / 180.0D);

            for (j = 0; j < 1.0D + d10 * 60.0D; ++j) {
                double d5 = rand.nextFloat() * 2.0F - 1.0F;
                double d6 = (rand.nextInt(2) * 2 - 1) * 0.7D;
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
            if (riddenByEntity != null) if (!goneRight) {
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

            if (boatPosRotationIncrements > 0) {
                d2 = posX + (boatX - posX) / boatPosRotationIncrements;
                d4 = posY + (boatY - posY) / boatPosRotationIncrements;
                d11 = posZ + (boatZ - posZ) / boatPosRotationIncrements;
                d12 = MathHelper.wrapAngleTo180_double(boatYaw - rotationYaw);
                rotationYaw = (float) (rotationYaw + d12 / boatPosRotationIncrements);
                rotationPitch = (float) (rotationPitch + (boatPitch - rotationPitch) / boatPosRotationIncrements);
                --boatPosRotationIncrements;
                setPosition(d2, d4, d11);
                setRotation(rotationYaw, rotationPitch);
            } else {
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
            } else {
                if (motionY < 0.0D) {
                    motionY /= 2.0D;
                }
                
                if (d0 > 0.9D) {
                    motionY += 0.007000000216066837D * GeneralStuff.SPEEDBOAT_VERTICAL_MODIFIER;
                } else {
                    motionY += 0.007000000216066837D;
                }
            }

            if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) riddenByEntity;
                float f = riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
                motionX += -Math.sin(f * (float) Math.PI / 180.0F) * speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
                motionZ += Math.cos(f * (float) Math.PI / 180.0F) * speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
            }

            d2 = Math.sqrt(motionX * motionX + motionZ * motionZ);

            if (d2 > 0.35D) {
                d4 = 0.35D / d2;
                motionX *= d4;
                motionZ *= d4;
                d2 = 0.35D;
            }

            if (d2 > d10 && speedMultiplier < 10D) {
                speedMultiplier += (25D - speedMultiplier) / 150.0D;

                if (speedMultiplier > 10D) {
                    speedMultiplier = 10D;
                }
            } else {
                speedMultiplier -= (speedMultiplier - 15D) / 150.0D;

                if (speedMultiplier < 0.07D) {
                    speedMultiplier = 0.07D;
                }
            }

            if (!worldObj.isRemote) {
                for (int i = -1; i < 2; i++) {
                    for (int k = -1; k < 2; k++)
                        if (worldObj.getBlock((int) posX + i, (int) posY, (int) posZ + k) == Blocks.waterlily) {
                            worldObj.func_147480_a((int) posX + i, (int) posY, (int) posZ + k, true);
                        }
                }

                List list = worldObj.getEntitiesWithinAABB(EntitySquid.class, getBoundingBox().expand(1.0D, 1.0D, 1.0D));
                if (!list.isEmpty()) {
                    for (Object i : list) {
                        EntitySquid squid = (EntitySquid) i;
                        squid.attackEntityFrom(DamageSource.inWall, 25F);
                    }
                }
            }

            if (onGround) {
                motionX *= 0.5D;
                motionY *= 0.5D;
                motionZ *= 0.5D;
            }

            moveEntity(motionX, motionY, motionZ);

            if (riddenByEntity == null) {
                motionX = 0D;
                motionY = 0D;
                motionZ = 0D;
            } else {
                motionX *= 0.9900000095367432D;
                motionY *= 0.949999988079071D;
                motionZ *= 0.9900000095367432D;
            }

            rotationPitch = 0.0F;
            d4 = rotationYaw;
            d11 = prevPosX - posX;
            d12 = prevPosZ - posZ;

            if (d11 * d11 + d12 * d12 > 0.001D) {
                d4 = (float) (Math.atan2(d12, d11) * 180.0D / Math.PI);
            }

            double d7 = MathHelper.wrapAngleTo180_double(d4 - rotationYaw);

            if (d7 > 20.0D) {
                d7 = 20.0D;
            }

            if (d7 < -20.0D) {
                d7 = -20.0D;
            }

            rotationYaw = (float) (rotationYaw + d7);
            setRotation(rotationYaw, rotationPitch);

            if (!worldObj.isRemote) {
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                if (list != null && !list.isEmpty()) {
                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        Entity entity = (Entity) list.get(k1);

                        if (entity != riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
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

    @Override
    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            double d0 = Math.cos(rotationYaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin(rotationYaw * Math.PI / 180.0D) * 0.4D;
            riddenByEntity.setPosition(posX + d0, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != par1EntityPlayer) return true;
        else {
            if (!worldObj.isRemote) {
                par1EntityPlayer.mountEntity(this);
            }

            return true;
        }
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);

        if (par3) {
            if (fallDistance > 3.0F) {
                fall(fallDistance);
                fallDistance = 0.0F;
            }
        } else if (worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && par1 < 0.0D) {
            fallDistance = (float) (fallDistance - par1);
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
