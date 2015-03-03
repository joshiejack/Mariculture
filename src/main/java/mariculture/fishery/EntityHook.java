package mariculture.fishery;

import io.netty.buffer.ByteBuf;

import java.util.List;

import mariculture.api.fishery.Fishing;
import mariculture.core.config.FishMechanics;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityHook extends EntityFishHook implements IEntityAdditionalSpawnData {
    private float damage = 0.0F;
    private int baitQuality;

    public EntityHook(World world) {
        super(world);
    }

    public EntityHook(World world, EntityPlayer player) {
        super(world, player);
    }

    public EntityHook(World world, EntityPlayer player, float damage, int quality) {
        super(world, player);

        this.damage = damage;
        baitQuality = quality;
    }

    @Override
    public void onUpdate() {
        onEntityUpdate();

        if (field_146055_aB > 0) {
            double d7 = posX + (field_146056_aC - posX) / field_146055_aB;
            double d8 = posY + (field_146057_aD - posY) / field_146055_aB;
            double d9 = posZ + (field_146058_aE - posZ) / field_146055_aB;
            double d1 = MathHelper.wrapAngleTo180_double(field_146059_aF - rotationYaw);
            rotationYaw = (float) (rotationYaw + d1 / field_146055_aB);
            rotationPitch = (float) (rotationPitch + (field_146060_aG - rotationPitch) / field_146055_aB);
            --field_146055_aB;
            setPosition(d7, d8, d9);
            setRotation(rotationYaw, rotationPitch);
        } else {
            if (!worldObj.isRemote) {
                ItemStack itemstack = null;
                if (field_146042_b != null && !field_146042_b.isDead) {
                    itemstack = field_146042_b.getCurrentEquippedItem();
                }
                if (field_146042_b == null || field_146042_b.isDead || !field_146042_b.isEntityAlive() || itemstack == null || !(itemstack.getItem() instanceof ItemFishingRod) || getDistanceSqToEntity(field_146042_b) > 1024.0D) {
                    setDead();
                    if (field_146042_b != null) {
                        field_146042_b.fishEntity = null;
                    }

                    return;
                }

                if (field_146043_c != null) {
                    if (!field_146043_c.isDead) {
                        posX = field_146043_c.posX;
                        posY = field_146043_c.boundingBox.minY + field_146043_c.height * 0.8D;
                        posZ = field_146043_c.posZ;
                        return;
                    }

                    field_146043_c = null;
                }
            }

            if (field_146044_a > 0) {
                --field_146044_a;
            }

            if (field_146051_au) {
                if (worldObj.getBlock(field_146037_g, field_146048_h, field_146050_i) == field_146046_j) {
                    ++field_146049_av;

                    if (field_146049_av == 1200) {
                        setDead();
                    }

                    return;
                }

                field_146051_au = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                field_146049_av = 0;
                field_146047_aw = 0;
            } else {
                ++field_146047_aw;
            }

            Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
            MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec31, vec3);
            vec31 = Vec3.createVectorHelper(posX, posY, posZ);
            vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

            if (movingobjectposition != null) {
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            double d2;

            for (int i = 0; i < list.size(); ++i) {
                Entity entity1 = (Entity) list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != field_146042_b || field_146047_aw >= 5)) {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null) {
                        d2 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d2 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d2;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null) if (movingobjectposition.entityHit != null) {
                if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, field_146042_b), damage)) {
                    field_146043_c = movingobjectposition.entityHit;
                }
            } else {
                field_146051_au = true;
            }

            if (!field_146051_au) {
                moveEntity(motionX, motionY, motionZ);
                float f5 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

                for (rotationPitch = (float) (Math.atan2(motionY, f5) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
                    ;
                }

                while (rotationPitch - prevRotationPitch >= 180.0F) {
                    prevRotationPitch += 360.0F;
                }

                while (rotationYaw - prevRotationYaw < -180.0F) {
                    prevRotationYaw -= 360.0F;
                }

                while (rotationYaw - prevRotationYaw >= 180.0F) {
                    prevRotationYaw += 360.0F;
                }

                rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
                rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
                float f6 = 0.92F;

                if (onGround || isCollidedHorizontally) {
                    f6 = 0.5F;
                }

                byte b0 = 5;
                double d10 = 0.0D;

                for (int j = 0; j < b0; ++j) {
                    double d3 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (j + 0) / b0 - 0.125D + 0.125D;
                    double d4 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (j + 1) / b0 - 0.125D + 0.125D;
                    AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(boundingBox.minX, d3, boundingBox.minZ, boundingBox.maxX, d4, boundingBox.maxZ);

                    if (worldObj.isAABBInMaterial(axisalignedbb1, Material.water) || worldObj.provider.isHellWorld && worldObj.isAABBInMaterial(axisalignedbb1, Material.lava)) {
                        d10 += 0.65D / b0;
                    }
                }

                if (!worldObj.isRemote && d10 > 0.0D) {
                    WorldServer worldserver = (WorldServer) worldObj;
                    int k = 1;

                    if (rand.nextFloat() < 0.25F && worldObj.canLightningStrikeAt(MathHelper.floor_double(posX), MathHelper.floor_double(posY) + 1, MathHelper.floor_double(posZ))) {
                        k = 2;
                    }

                    if (rand.nextFloat() < 0.5F && !worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY) + 1, MathHelper.floor_double(posZ))) {
                        --k;
                    }

                    if (field_146045_ax > 0) {
                        --field_146045_ax;
                        if (field_146045_ax <= 0) {
                            field_146040_ay = 0;
                            field_146038_az = 0;
                        }
                    } else {
                        float f1;
                        float f2;
                        double d5;
                        double d6;
                        double d11;
                        float f7;

                        if (field_146038_az > 0) {
                            field_146038_az -= k;
                            if (field_146038_az <= 0) {
                                motionY -= 0.20000000298023224D;
                                playSound("random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                                f1 = MathHelper.floor_double(boundingBox.minY);
                                worldserver.func_147487_a("bubble", posX, f1 + 1.0F, posZ, (int) (1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D);
                                worldserver.func_147487_a("wake", posX, f1 + 1.0F, posZ, (int) (1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D);
                                field_146045_ax = MathHelper.getRandomIntegerInRange(rand, 20, 50);
                            } else {
                                field_146054_aA = (float) (field_146054_aA + rand.nextGaussian() * 4.0D);
                                f1 = field_146054_aA * 0.017453292F;
                                f7 = MathHelper.sin(f1);
                                f2 = MathHelper.cos(f1);
                                d11 = posX + f7 * field_146038_az * 0.1F;
                                d5 = MathHelper.floor_double(boundingBox.minY) + 1.0F;
                                d6 = posZ + f2 * field_146038_az * 0.1F;

                                if (rand.nextFloat() < 0.15F) {
                                    worldserver.func_147487_a("bubble", d11, d5 - 0.10000000149011612D, d6, 1, f7, 0.1D, f2, 0.0D);
                                }

                                float f3 = f7 * 0.04F;
                                float f4 = f2 * 0.04F;
                                worldserver.func_147487_a("wake", d11, d5, d6, 0, f4, 0.01D, -f3, 1.0D);
                                worldserver.func_147487_a("wake", d11, d5, d6, 0, -f4, 0.01D, f3, 1.0D);
                            }
                        } else if (field_146040_ay > 0) {
                            if (FishMechanics.SPEED_MULTIPLIER > 0) {
                                field_146040_ay -= FishMechanics.SPEED_MULTIPLIER;
                            }
                            
                            field_146040_ay -= k;
                            f1 = 0.15F;

                            if (field_146040_ay < 20) {
                                f1 = (float) (f1 + (20 - field_146040_ay) * 0.05D);
                            } else if (field_146040_ay < 40) {
                                f1 = (float) (f1 + (40 - field_146040_ay) * 0.02D);
                            } else if (field_146040_ay < 60) {
                                f1 = (float) (f1 + (60 - field_146040_ay) * 0.01D);
                            }

                            if (rand.nextFloat() < f1) {
                                f7 = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F) * 0.017453292F;
                                f2 = MathHelper.randomFloatClamp(rand, 25.0F, 60.0F);
                                d11 = posX + MathHelper.sin(f7) * f2 * 0.1F;
                                d5 = MathHelper.floor_double(boundingBox.minY) + 1.0F;
                                d6 = posZ + MathHelper.cos(f7) * f2 * 0.1F;
                                worldserver.func_147487_a("splash", d11, d5, d6, 2 + rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
                            }

                            if (field_146040_ay <= 0) {
                                field_146054_aA = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F);
                                field_146038_az = MathHelper.getRandomIntegerInRange(rand, 20, 80);
                            }
                        } else {
                            field_146040_ay = MathHelper.getRandomIntegerInRange(rand, 100, baitQuality > 0 ? 900 : 1500);
                            field_146040_ay -= EnchantmentHelper.func_151387_h(field_146042_b) * 25 * 5 * (baitQuality > 0 ? baitQuality / 10 : 0.75);
                        }
                    }

                    if (field_146045_ax > 0) {
                        motionY -= rand.nextFloat() * rand.nextFloat() * rand.nextFloat() * 0.2D;
                    }
                }

                d2 = d10 * 2.0D - 1.0D;
                motionY += 0.03999999910593033D * d2;

                if (d10 > 0.0D) {
                    f6 = (float) (f6 * 0.9D);
                    motionY *= 0.8D;
                }

                motionX *= f6;
                motionY *= f6;
                motionZ *= f6;
                setPosition(posX, posY, posZ);
            }
        }
    }

    @Override
    public int func_146034_e() {
        if (worldObj.isRemote) return 0;
        else {
            byte b0 = 0;

            if (field_146043_c != null) {
                double d0 = field_146042_b.posX - posX;
                double d2 = field_146042_b.posY - posY;
                double d4 = field_146042_b.posZ - posZ;
                double d6 = MathHelper.sqrt_double(d0 * d0 + d2 * d2 + d4 * d4);
                double d8 = 0.1D;
                field_146043_c.motionX += d0 * d8;
                field_146043_c.motionY += d2 * d8 + MathHelper.sqrt_double(d6) * 0.08D;
                field_146043_c.motionZ += d4 * d8;
                b0 = 3;
            } else if (field_146045_ax > 0) {
                ItemStack result = Fishing.fishing.getCatch(worldObj, (int) posX, (int) posY, (int) posZ, field_146042_b, field_146042_b.getHeldItem());
                if (result != null) {
                    EntityItemFireImmune entityitem = new EntityItemFireImmune(worldObj, posX, posY, posZ, result);
                    double d1 = field_146042_b.posX - posX;
                    double d3 = field_146042_b.posY - posY;
                    double d5 = field_146042_b.posZ - posZ;
                    double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
                    double d9 = 0.1D;
                    entityitem.motionX = d1 * d9;
                    entityitem.motionY = d3 * d9 + MathHelper.sqrt_double(d7) * 0.08D;
                    entityitem.motionZ = d5 * d9;
                    worldObj.spawnEntityInWorld(entityitem);
                    field_146042_b.worldObj.spawnEntityInWorld(new EntityXPOrb(field_146042_b.worldObj, field_146042_b.posX, field_146042_b.posY + 0.5D, field_146042_b.posZ + 0.5D, rand.nextInt(6) + 1));
                    b0 = 1;
                } else {
                    b0 = 0;
                }
            }

            if (field_146051_au) {
                b0 = 2;
            }

            setDead();
            field_146042_b.fishEntity = null;
            return b0;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("Quality", (byte) baitQuality);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        baitQuality = nbt.getByte("Quality");
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(field_146042_b != null ? field_146042_b.getEntityId() : 0);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        field_146042_b = (EntityPlayer) worldObj.getEntityByID(buffer.readInt());
    }
}