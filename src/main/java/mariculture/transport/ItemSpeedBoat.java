package mariculture.transport;

import java.util.Iterator;
import java.util.List;

import mariculture.api.core.MaricultureTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemSpeedBoat extends Item {
    public ItemSpeedBoat() {
        maxStackSize = 1;
        setCreativeTab(MaricultureTab.tabWorld);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        float var4 = 1.0F;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * var4;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * var4;
        double x = player.prevPosX + (player.posX - player.prevPosX) * var4;
        double y = player.prevPosY + (player.posY - player.prevPosY) * var4 + 1.62D - player.yOffset;
        double z = player.prevPosZ + (player.posZ - player.prevPosZ) * var4;
        Vec3 var13 = world.getWorldVec3Pool().getVecFromPool(x, y, z);
        float var14 = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float var15 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float var16 = -MathHelper.cos(-pitch * 0.017453292F);
        float var17 = MathHelper.sin(-pitch * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
        MovingObjectPosition var24 = world.rayTraceBlocks(var13, var23, true);

        if (var24 == null) return stack;
        else {
            Vec3 var25 = player.getLook(var4);
            boolean flag = false;
            float var27 = 1.0F;
            List var28 = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand(var27, var27, var27));
            Iterator var29 = var28.iterator();

            while (var29.hasNext()) {
                Entity var30 = (Entity) var29.next();

                if (var30.canBeCollidedWith()) {
                    float var31 = var30.getCollisionBorderSize();
                    AxisAlignedBB var32 = var30.boundingBox.expand(var31, var31, var31);

                    if (var32.isVecInside(var13)) {
                        flag = true;
                    }
                }
            }

            if (flag) return stack;
            else {
                if (var24.typeOfHit == MovingObjectType.BLOCK) {
                    int var35 = var24.blockX;
                    int var33 = var24.blockY;
                    int var34 = var24.blockZ;

                    if (world.getBlock(var35, var33, var34) == Blocks.water) {
                        if (!world.isRemote) {
                            if (world.getBlock(var35, var33, var34) == Blocks.snow) {
                                --var33;
                            }

                            world.spawnEntityInWorld(new EntitySpeedBoat(world, var35 + 0.5F, var33 + 0.5F, var34 + 0.5F));
                        }

                        if (!player.capabilities.isCreativeMode) {
                            --stack.stackSize;
                        }
                    }
                }

                return stack;
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {}
}
