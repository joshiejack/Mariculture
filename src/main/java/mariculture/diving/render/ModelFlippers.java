package mariculture.diving.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ModelFlippers extends ModelBiped {
    private ModelRenderer RFlipper;
    private ModelRenderer RBase;
    private ModelRenderer LFlipper;
    private ModelRenderer LBase;

    public ModelFlippers() {
        textureWidth = 64;
        textureHeight = 32;

        RFlipper = new ModelRenderer(this, 0, 18);
        RFlipper.addBox(-2F, 11F, -15F, 5, 1, 13);
        RFlipper.setRotationPoint(-3F, 22F, 0F);
        RFlipper.setTextureSize(64, 32);
        RFlipper.mirror = true;
        setRotation(RFlipper, 0F, 0.0872665F, 0F);
        RBase = new ModelRenderer(this, 0, 0);
        RBase.addBox(-2F, 10F, -3F, 5, 2, 6);
        RBase.setRotationPoint(-3F, 22F, 0F);
        RBase.setTextureSize(64, 32);
        RBase.mirror = true;
        setRotation(RBase, 0F, 0F, 0F);
        LFlipper = new ModelRenderer(this, 0, 18);
        LFlipper.addBox(-3F, 11F, -15F, 5, 1, 13);
        LFlipper.setRotationPoint(3F, 22F, 0F);
        LFlipper.setTextureSize(64, 32);
        LFlipper.mirror = true;
        setRotation(LFlipper, 0F, -0.0872665F, 0F);
        LBase = new ModelRenderer(this, 0, 0);
        LBase.addBox(-3F, 10F, -3F, 5, 2, 6);
        LBase.setRotationPoint(3F, 22F, 0F);
        LBase.setTextureSize(64, 32);
        LBase.mirror = true;
        setRotation(LBase, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (entity instanceof EntityPlayer) {
            this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            RFlipper.render(f5);
            RBase.render(f5);
            LFlipper.render(f5);
            LBase.render(f5);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
        this.RFlipper.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.RBase.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.LFlipper.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.LBase.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;


        if (this.isRiding) {
            this.RFlipper.rotateAngleX = -((float) Math.PI * 2F / 5F);
            this.LFlipper.rotateAngleX = -((float) Math.PI * 2F / 5F);
            this.RFlipper.rotateAngleY = ((float) Math.PI / 10F);
            this.LFlipper.rotateAngleY = -((float) Math.PI / 10F);

            this.RBase.rotateAngleX = -((float) Math.PI * 2F / 5F);
            this.LBase.rotateAngleX = -((float) Math.PI * 2F / 5F);
            this.RBase.rotateAngleY = ((float) Math.PI / 10F);
            this.LBase.rotateAngleY = -((float) Math.PI / 10F);
        }

        float f6;
        float f7;

        
        if (entity.isSneaking()) {
            this.RFlipper.rotationPointZ = 4.0F;
            this.LFlipper.rotationPointZ = 4.0F;
            this.RFlipper.rotationPointY = 9.0F;
            this.LFlipper.rotationPointY = 9.0F;
            this.RBase.rotationPointZ = 4.0F;
            this.LBase.rotationPointZ = 4.0F;
            this.RBase.rotationPointY = 9.0F;
            this.LBase.rotationPointY = 9.0F;
        } else {
            this.RFlipper.rotationPointZ = 0.1F;
            this.LFlipper.rotationPointZ = 0.1F;
            this.RFlipper.rotationPointY = 12.0F;
            this.LFlipper.rotationPointY = 12.0F;
            this.RBase.rotationPointZ = 0.1F;
            this.LBase.rotationPointZ = 0.1F;
            this.RBase.rotationPointY = 12.0F;
            this.LBase.rotationPointY = 12.0F;
        } 
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
