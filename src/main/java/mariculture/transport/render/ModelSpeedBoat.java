package mariculture.transport.render;

import mariculture.transport.EntitySpeedBoat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelSpeedBoat extends ModelBase {
    private ModelRenderer base;
    private ModelRenderer boatwindow;
    private ModelRenderer sidelrg1;
    private ModelRenderer motorend;
    private ModelRenderer sidelong1;
    private ModelRenderer sidelrg2;
    private ModelRenderer sidelong2;
    private ModelRenderer front;
    private ModelRenderer back;
    private ModelRenderer motorhead;

    public ModelSpeedBoat() {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 45);
        base.addBox(-17F, 0F, -8F, 32, 3, 16);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, -0.0174533F, 0F, 0F);
        boatwindow = new ModelRenderer(this, 33, 15);
        boatwindow.addBox(-17F, -11F, -8F, 1, 5, 16);
        boatwindow.setRotationPoint(0F, 0F, 0F);
        boatwindow.setTextureSize(128, 64);
        boatwindow.mirror = true;
        setRotation(boatwindow, -0.0174533F, 0F, 0F);
        sidelrg1 = new ModelRenderer(this, 0, 27);
        sidelrg1.addBox(-17F, -7F, 8F, 9, 9, 2);
        sidelrg1.setRotationPoint(0F, 0F, 0F);
        sidelrg1.setTextureSize(128, 64);
        sidelrg1.mirror = true;
        setRotation(sidelrg1, -0.0174533F, 0F, 0F);
        motorend = new ModelRenderer(this, 0, 0);
        motorend.addBox(17F, 10F, -1F, 3, 10, 2);
        motorend.setRotationPoint(0F, 0F, 0F);
        motorend.setTextureSize(128, 64);
        motorend.mirror = true;
        setRotation(motorend, 0F, 0F, -0.6320364F);
        sidelong1 = new ModelRenderer(this, 0, 38);
        sidelong1.addBox(-8F, -4F, 8F, 24, 5, 2);
        sidelong1.setRotationPoint(0F, 0F, 0F);
        sidelong1.setTextureSize(128, 64);
        sidelong1.mirror = true;
        setRotation(sidelong1, -0.0174533F, 0F, 0F);
        sidelrg2 = new ModelRenderer(this, 0, 27);
        sidelrg2.addBox(-17F, -7F, -10F, 9, 9, 2);
        sidelrg2.setRotationPoint(0F, 0F, 0F);
        sidelrg2.setTextureSize(128, 64);
        sidelrg2.mirror = true;
        setRotation(sidelrg2, -0.0174533F, 0F, 0F);
        sidelong2 = new ModelRenderer(this, 0, 38);
        sidelong2.addBox(-8F, -4F, -10F, 24, 5, 2);
        sidelong2.setRotationPoint(0F, 0F, 0F);
        sidelong2.setTextureSize(128, 64);
        sidelong2.mirror = true;
        setRotation(sidelong2, -0.0174533F, 0F, 0F);
        front = new ModelRenderer(this, 52, 20);
        front.addBox(-20F, -7F, -8F, 3, 9, 16);
        front.setRotationPoint(0F, 0F, 0F);
        front.setTextureSize(128, 64);
        front.mirror = true;
        setRotation(front, -0.0174533F, 0F, 0F);
        back = new ModelRenderer(this, 0, 5);
        back.addBox(15F, -4F, -8F, 3, 6, 16);
        back.setRotationPoint(0F, 0F, 0F);
        back.setTextureSize(128, 64);
        back.mirror = true;
        setRotation(back, -0.0174533F, 0F, 0F);
        motorhead = new ModelRenderer(this, 35, 0);
        motorhead.addBox(16F, 4F, -3F, 5, 6, 6);
        motorhead.setRotationPoint(0F, 0F, 0F);
        motorhead.setTextureSize(128, 64);
        motorhead.mirror = true;
        setRotation(motorhead, -0.0174533F, 0F, -0.6320364F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);

        GL11.glTranslatef(0.4F, 0.1F, 0F);
        EntitySpeedBoat boat = (EntitySpeedBoat) entity;
        motorend.rotateAngleX = boat.motorPos;
        base.render(f5);
        boatwindow.render(f5);
        sidelrg1.render(f5);
        motorend.render(f5);
        sidelong1.render(f5);
        sidelrg2.render(f5);
        sidelong2.render(f5);
        front.render(f5);
        back.render(f5);
        motorhead.render(f5);
    }

    public void renderInventory(ItemRenderType type, float scale) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        if (type == ItemRenderType.INVENTORY) {
            GL11.glRotatef(35, 1, 0, 0);
            GL11.glRotatef(45, 0, -5, 0);
            GL11.glScalef(7F, 7F, 7F);
            GL11.glTranslatef(0F, 0.7F, -1.61F);
        } else if (type == ItemRenderType.ENTITY) {
            GL11.glRotatef(180, 0, 0, 1);
        }

        base.render(scale);
        boatwindow.render(scale);
        sidelrg1.render(scale);
        motorend.render(scale);
        sidelong1.render(scale);
        sidelrg2.render(scale);
        sidelong2.render(scale);
        front.render(scale);
        back.render(scale);
        motorhead.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }
}
