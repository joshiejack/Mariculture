package mariculture.world.render;

import mariculture.world.EntityRockhopper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ModelRockhopper extends ModelBase {
    public ModelRenderer hair;
    public ModelRenderer head;
    public ModelRenderer beak;
    public ModelRenderer bodyUpper;
    public ModelRenderer bodyLower;
    public ModelRenderer chest;
    public ModelRenderer tail;
    public ModelRenderer leftFlipper;
    public ModelRenderer rightFlipper;
    public ModelRenderer leftFootAnkle;
    public ModelRenderer leftFootFoot;
    public ModelRenderer rightFootAnkle;
    public ModelRenderer rightFootFoot;

    public ModelRockhopper() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leftFlipper = new ModelRenderer(this, 0, 46);
        this.leftFlipper.mirror = true;
        this.leftFlipper.setRotationPoint(4.0F, 8.0F, -3.0F);
        this.leftFlipper.addBox(0.0F, 0.0F, 0.0F, 12, 1, 5, 0.0F);
        this.setRotateAngle(leftFlipper, -0.10471975511965977F, 0.0F, 0.9599310885968813F);
        this.hair = new ModelRenderer(this, 20, 15);
        this.hair.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.hair.addBox(-3.0F, -7.0F, -3.5F, 6, 1, 5, 0.0F);
        this.setRotateAngle(hair, 0.03490658503988659F, 0.0F, 0.0F);
        this.bodyUpper = new ModelRenderer(this, 34, 24);
        this.bodyUpper.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.bodyUpper.addBox(-4.0F, -5.0F, -4.0F, 8, 7, 7, 0.0F);
        this.tail = new ModelRenderer(this, 0, 20);
        this.tail.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.tail.addBox(-3.0F, 8.5F, 4.0F, 6, 2, 1, 0.0F);
        this.beak = new ModelRenderer(this, 15, 25);
        this.beak.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.beak.addBox(-2.0F, -4.0F, -7.5F, 4, 3, 3, 0.0F);
        this.setRotateAngle(beak, 0.03490658503988659F, 0.0F, 0.0F);
        this.leftFootAnkle = new ModelRenderer(this, 0, 30);
        this.leftFootAnkle.setRotationPoint(2.0F, 22.0F, 1.0F);
        this.leftFootAnkle.addBox(-1.0F, 0.0F, -2.0F, 3, 1, 2, 0.0F);
        this.leftFootFoot = new ModelRenderer(this, 0, 40);
        this.leftFootFoot.setRotationPoint(2.0F, 22.0F, 1.0F);
        this.leftFootFoot.addBox(-1.0F, 1.0F, -4.0F, 3, 1, 4, 0.0F);
        this.chest = new ModelRenderer(this, 0, 0);
        this.chest.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.chest.addBox(-4.0F, -4.0F, -5.0F, 8, 12, 1, 0.0F);
        this.rightFootAnkle = new ModelRenderer(this, 0, 30);
        this.rightFootAnkle.setRotationPoint(-3.0F, 22.0F, 1.0F);
        this.rightFootAnkle.addBox(-1.0F, 0.0F, -2.0F, 3, 1, 2, 0.0F);
        this.rightFootFoot = new ModelRenderer(this, 0, 40);
        this.rightFootFoot.setRotationPoint(-3.0F, 22.0F, 1.0F);
        this.rightFootFoot.addBox(-1.0F, 1.0F, -4.0F, 3, 1, 4, 0.0F);
        this.head = new ModelRenderer(this, 30, 0);
        this.head.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.head.addBox(-3.0F, -6.0F, -4.5F, 6, 5, 7, 0.0F);
        this.setRotateAngle(head, 0.03490658503988659F, 0.0F, 0.0F);
        this.bodyLower = new ModelRenderer(this, 28, 46);
        this.bodyLower.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.bodyLower.addBox(-5.0F, 0.0F, -4.0F, 10, 10, 8, 0.0F);
        this.rightFlipper = new ModelRenderer(this, 0, 46);
        this.rightFlipper.setRotationPoint(-4.0F, 8.0F, -3.0F);
        this.rightFlipper.addBox(-11.0F, 0.0F, 0.0F, 11, 1, 5, 0.0F);
        this.setRotateAngle(rightFlipper, -0.10471975511965977F, 0.0F, -0.9599310885968813F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        EntityRockhopper rockhopper = (EntityRockhopper) entity;
        if (rockhopper.isChild()) {
            GL11.glScalef(0.2F, 0.2F, 0.2F);
            GL11.glTranslatef(0F, 6F, 0F);
        } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glTranslatef(0F, 1.5F, 0F);
        }
        
        GL11.glRotated(rockhopper.rotationBody, 1D, 0F, 1D);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, rockhopper);
        this.rightFootFoot.render(f5);
        this.hair.render(f5);
        this.head.render(f5);
        this.bodyLower.render(f5);
        this.leftFootFoot.render(f5);
        this.tail.render(f5);
        this.leftFootAnkle.render(f5);
        this.rightFootAnkle.render(f5);
        this.rightFlipper.render(f5);
        this.bodyUpper.render(f5);
        this.leftFlipper.render(f5);
        this.chest.render(f5);
        this.beak.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, EntityRockhopper entity) {
        this.hair.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
        this.hair.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
        this.head.rotateAngleX = this.hair.rotateAngleX;
        this.head.rotateAngleY = this.hair.rotateAngleY;
        this.beak.rotateAngleX = this.hair.rotateAngleX;
        this.beak.rotateAngleY = this.hair.rotateAngleY;
        this.rightFootAnkle.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        this.rightFootFoot.rotateAngleX = this.rightFootAnkle.rotateAngleX * 2;
        this.leftFootAnkle.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 1.4F * p_78087_2_;
        this.leftFootFoot.rotateAngleX = this.leftFootAnkle.rotateAngleX * 2;
        this.rightFlipper.rotateAngleZ = (float) -entity.rotationWing / 10;
        this.leftFlipper.rotateAngleZ = (float) entity.rotationWing / 10;
    }
}
