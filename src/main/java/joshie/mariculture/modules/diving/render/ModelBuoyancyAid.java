package joshie.mariculture.modules.diving.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelBuoyancyAid extends ModelBiped {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelBiped default_;
    private ModelRenderer log;
    private ModelRenderer strap1;
    private ModelRenderer strap2;
    private ModelRenderer strap3;
    private ModelRenderer strap4;
    private ModelRenderer strap5;
    private ModelRenderer strap6;

    public ModelBuoyancyAid() {
        textureWidth = 64;
        textureHeight = 64;
        log = new ModelRenderer(this, 0, 0);
        log.addBox(-6F, 2F, -6F, 12, 12, 12);
        log.setRotationPoint(0F, 0F, 0F);
        log.setTextureSize(64, 64);
        log.mirror = true;
        setRotation(log, 0F, 0F, 0F);
        strap1 = new ModelRenderer(this, 0, 36);
        strap1.addBox(-5F, -1F, -6F, 1, 1, 6);
        strap1.setRotationPoint(0F, 0F, 0F);
        strap1.setTextureSize(64, 64);
        strap1.mirror = true;
        setRotation(strap1, 0.5204921F, 0F, 0F);
        strap2 = new ModelRenderer(this, 32, 32);
        strap2.addBox(-5F, -1F, -1F, 1, 1, 2);
        strap2.setRotationPoint(0F, 0F, 0F);
        strap2.setTextureSize(64, 64);
        strap2.mirror = true;
        setRotation(strap2, 0F, 0F, 0F);
        strap3 = new ModelRenderer(this, 0, 36);
        strap3.addBox(-5F, -1F, 0F, 1, 1, 6);
        strap3.setRotationPoint(0F, 0F, 0F);
        strap3.setTextureSize(64, 64);
        strap3.mirror = true;
        setRotation(strap3, -0.5205006F, 0F, 0F);
        strap4 = new ModelRenderer(this, 0, 36);
        strap4.addBox(4F, -1F, 0F, 1, 1, 6);
        strap4.setRotationPoint(0F, 0F, 0F);
        strap4.setTextureSize(64, 64);
        strap4.mirror = true;
        setRotation(strap4, -0.5205006F, 0F, 0F);
        strap5 = new ModelRenderer(this, 32, 32);
        strap5.addBox(4F, -1F, -1F, 1, 1, 2);
        strap5.setRotationPoint(0F, 0F, 0F);
        strap5.setTextureSize(64, 64);
        strap5.mirror = true;
        setRotation(strap5, 0F, 0F, 0F);
        strap6 = new ModelRenderer(this, 0, 36);
        strap6.addBox(4F, -1F, -6F, 1, 1, 6);
        strap6.setRotationPoint(0F, 0F, 0F);
        strap6.setTextureSize(64, 64);
        strap6.mirror = true;
    }

    public ModelBuoyancyAid setBiped(ModelBiped biped) {
        this.default_ = biped;
        return this;
    }

    private void setOffsetAndAngle(ModelRenderer renderer, float offset, float angle) {
        renderer.offsetY = offset;
        renderer.rotateAngleX = angle;
    }

    @Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0F, -0.0129F, 0.0F);

        log.rotateAngleY = 0.0F;
        if (entity.isSneaking()) {
            setOffsetAndAngle(log, 0.25F, 0.5F);
            setOffsetAndAngle(strap1, 0.25F, 1.204921F);
            setOffsetAndAngle(strap2, 0.25F, 0.5F);
            setOffsetAndAngle(strap3, 0.25F, -0.0205006F);
            setOffsetAndAngle(strap4, 0.25F, -0.0205006F);
            setOffsetAndAngle(strap5, 0.25F, 0.5F);
            setOffsetAndAngle(strap6, 0.25F, 1.204921F);
        } else {
            setOffsetAndAngle(log, 0.0F, 0.0F);
            setOffsetAndAngle(strap1, 0.0F, 0.5204921F);
            setOffsetAndAngle(strap2, 0.0F, 0.0F);
            setOffsetAndAngle(strap3, 0.0F, -0.5205006F);
            setOffsetAndAngle(strap4, 0.0F, -0.5205006F);
            setOffsetAndAngle(strap5, 0.0F, 0.0F);
            setOffsetAndAngle(strap6, 0.0F, 0.5204921F);
        }

        log.render(scale);
        strap1.render(scale);
        strap2.render(scale);
        strap3.render(scale);
        strap4.render(scale);
        strap5.render(scale);
        strap6.render(scale);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}