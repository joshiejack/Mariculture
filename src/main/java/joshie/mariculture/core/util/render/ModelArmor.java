package joshie.mariculture.core.util.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class ModelArmor extends ModelBiped {
    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        prepareRender(entityIn);
        updateRender();
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    protected void updateRender() {}

    public void prepareRender(Entity entity) {
        EntityLivingBase living = (EntityLivingBase) entity;
        isSneak = living != null ? living.isSneaking() : false;
        if(living != null && living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            if (player.isSpectator()) {
                setInvisible(false);
                bipedHead.showModel = true;
                bipedHeadwear.showModel = true;
            } else {
                ItemStack itemstack = player.getHeldItemMainhand();
                ItemStack itemstack1 = player.getHeldItemOffhand();
                setInvisible(true);
                ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
                ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

                if (itemstack != null) {
                    modelbiped$armpose = ModelBiped.ArmPose.ITEM;

                    if (player.getItemInUseCount() > 0) {
                        EnumAction enumaction = itemstack.getItemUseAction();
                        if (enumaction == EnumAction.BLOCK)  {
                            modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                        } else if (enumaction == EnumAction.BOW) {
                            modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                        }
                    }
                }

                if (itemstack1 != null) {
                    modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

                    if (player.getItemInUseCount() > 0) {
                        EnumAction enumaction1 = itemstack1.getItemUseAction();
                        if (enumaction1 == EnumAction.BLOCK) {
                            modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                        }
                    }
                }

                if (player.getPrimaryHand() == EnumHandSide.RIGHT) {
                    rightArmPose = modelbiped$armpose;
                    leftArmPose = modelbiped$armpose1;
                } else {
                    rightArmPose = modelbiped$armpose1;
                    leftArmPose = modelbiped$armpose;
                }
            }
        }
    }

    protected void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
