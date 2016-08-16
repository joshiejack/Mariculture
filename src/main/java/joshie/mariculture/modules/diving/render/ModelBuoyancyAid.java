package joshie.mariculture.modules.diving.render;

import joshie.mariculture.core.util.render.ModelArmor;
import net.minecraft.client.model.ModelRenderer;

public class ModelBuoyancyAid extends ModelArmor {
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
        setRotation(strap6, 0.5204921F, 0F, 0F);

        log.addChild(strap1);
        log.addChild(strap2);
        log.addChild(strap3);
        log.addChild(strap4);
        log.addChild(strap5);
        log.addChild(strap6);
    }

    @Override
    public void updateRender() {
        bipedHead.showModel = false;
        bipedHeadwear.showModel = false;
        bipedBody = log;
    }
}