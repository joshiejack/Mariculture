package joshie.mariculture.fishery.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

public class ModelFishingHat extends ModelBiped {
    private ModelRenderer TieLeft;
    private ModelRenderer TieBottom;
    private ModelRenderer Middle;
    private ModelRenderer Top;
    private ModelRenderer TieRight;
    private ModelRenderer Bottom;

    public ModelFishingHat() {
        textureWidth = 64;
        textureHeight = 64;

        TieLeft = new ModelRenderer(this, 0, 0);
        TieLeft.addBox(3F, -6F, -3F, 1, 7, 1);
        TieLeft.setRotationPoint(0F, 0F, 0F);
        TieLeft.setTextureSize(64, 64);
        TieLeft.mirror = true;
        setRotation(TieLeft, -0.2602503F, 0F, 0F);
        TieBottom = new ModelRenderer(this, 0, 0);
        TieBottom.addBox(-4F, 0F, -3F, 8, 1, 1);
        TieBottom.setRotationPoint(0F, 0F, 0F);
        TieBottom.setTextureSize(64, 64);
        TieBottom.mirror = true;
        setRotation(TieBottom, -0.2230717F, 0F, 0F);
        Middle = new ModelRenderer(this, 0, 30);
        Middle.addBox(-5F, -7F, -5F, 10, 1, 10);
        Middle.setRotationPoint(0F, 0F, 0F);
        Middle.setTextureSize(64, 64);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 14);
        Top.addBox(-4F, -11F, -4F, 8, 4, 8);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        TieRight = new ModelRenderer(this, 0, 0);
        TieRight.addBox(-4F, -6F, -3F, 1, 7, 1);
        TieRight.setRotationPoint(0F, 0F, 0F);
        TieRight.setTextureSize(64, 64);
        TieRight.mirror = true;
        setRotation(TieRight, -0.2602503F, 0F, 0F);
        Bottom = new ModelRenderer(this, 0, 45);
        Bottom.addBox(-6F, -6F, -6F, 12, 1, 12);
        Bottom.setRotationPoint(0F, 0F, 0F);
        Bottom.setTextureSize(64, 64);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof EntityLivingBase) {
            this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
            GL11.glPushMatrix();
            GL11.glScalef(1.05F, 1.05F, 1.05F);
            TieLeft.render(f5);
            TieBottom.render(f5);
            Middle.render(f5);
            Top.render(f5);
            TieRight.render(f5);
            Bottom.render(f5);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
        this.TieLeft.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.TieBottom.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Middle.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Top.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.TieRight.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Bottom.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.TieLeft.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.TieBottom.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Middle.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Top.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.TieRight.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Bottom.rotateAngleY = par4 / (180F / (float) Math.PI);

        if (entity.isSneaking()) {
            this.TieLeft.rotationPointY = 1.0F;
            this.TieBottom.rotationPointY = 1.0F;
            this.Middle.rotationPointY = 1.0F;
            this.Top.rotationPointY = 1.0F;
            this.TieRight.rotationPointY = 1.0F;
            this.Bottom.rotationPointY = 1.0F;
        } else {
            this.TieLeft.rotationPointY = 0F;
            this.TieBottom.rotationPointY = 0F;
            this.Middle.rotationPointY = 0F;
            this.Top.rotationPointY = 0F;
            this.TieRight.rotationPointY = 0F;
            this.Bottom.rotationPointY = 0F;
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
