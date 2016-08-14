package joshie.mariculture.modules.diving.render;

import joshie.mariculture.core.util.ModelArmor;
import net.minecraft.client.model.ModelRenderer;

public class ModelSnorkel extends ModelArmor {
    private ModelRenderer mask;
    private ModelRenderer frameLeft;
    private ModelRenderer frameRight;
    private ModelRenderer sideLeft;
    private ModelRenderer sideRight;
    private ModelRenderer back;
    private ModelRenderer mouth;
    private ModelRenderer tube0;
    private ModelRenderer tube1;
    private ModelRenderer tube2;
    private ModelRenderer tube3;
    private ModelRenderer tube4;

    public ModelSnorkel() {
        textureWidth = 32;
        textureHeight = 32;

        mask = new ModelRenderer(this, 4, 11);
        mask.addBox(-4F, -7F, -5F, 8, 6, 1);
        mask.setRotationPoint(0F, 0F, 0F);
        mask.setTextureSize(32, 32);
        mask.mirror = true;
        setRotation(mask, 0F, 0F, 0F);
        frameLeft = new ModelRenderer(this, 0, 12);
        frameLeft.addBox(4F, -6.5F, -5F, 1, 5, 1);
        frameLeft.setRotationPoint(0F, 0F, 0F);
        frameLeft.setTextureSize(32, 32);
        frameLeft.mirror = true;
        setRotation(frameLeft, 0F, 0F, 0F);
        frameRight = new ModelRenderer(this, 0, 12);
        frameRight.addBox(-5F, -6.5F, -5F, 1, 5, 1);
        frameRight.setRotationPoint(0F, 0F, 0F);
        frameRight.setTextureSize(32, 32);
        frameRight.mirror = true;
        setRotation(frameRight, 0F, 0F, 0F);
        sideLeft = new ModelRenderer(this, 0, 23);
        sideLeft.addBox(4.01F, -5.5F, -4F, 1, 1, 8);
        sideLeft.setRotationPoint(0F, 0F, 0F);
        sideLeft.setTextureSize(32, 32);
        sideLeft.mirror = true;
        setRotation(sideLeft, 0F, 0F, 0F);
        sideRight = new ModelRenderer(this, 0, 23);
        sideRight.addBox(-5.01F, -5.5F, -4F, 1, 1, 8);
        sideRight.setRotationPoint(0F, 0F, 0F);
        sideRight.setTextureSize(32, 32);
        sideRight.mirror = true;
        setRotation(sideRight, 0F, 0F, 0F);
        back = new ModelRenderer(this, 0, 30);
        back.addBox(-4F, -5.5F, 4.01F, 8, 1, 1);
        back.setRotationPoint(0F, 0F, 0F);
        back.setTextureSize(32, 32);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        mouth = new ModelRenderer(this, 0, 8);
        mouth.addBox(-1F, -1F, -5F, 2, 1, 1);
        mouth.setRotationPoint(0F, 0F, 0F);
        mouth.setTextureSize(32, 32);
        mouth.mirror = true;
        setRotation(mouth, 0F, 0F, 0F);
        tube0 = new ModelRenderer(this, 14, 7);
        tube0.addBox(0F, -1.5F, -6F, 2, 1, 1);
        tube0.setRotationPoint(0F, 0F, 0F);
        tube0.setTextureSize(32, 32);
        tube0.mirror = true;
        setRotation(tube0, 0F, 0F, -0.0872665F);
        tube1 = new ModelRenderer(this, 14, 0);
        tube1.addBox(2F, -1F, -6.4F, 2, 1, 1);
        tube1.setRotationPoint(0F, 0F, 0F);
        tube1.setTextureSize(32, 32);
        tube1.mirror = true;
        setRotation(tube1, 0F, 0F, -0.3490659F);
        tube2 = new ModelRenderer(this, 14, 0);
        tube2.addBox(3F, 1.9F, -6.5F, 3, 1, 1);
        tube2.setRotationPoint(0F, 0F, 0F);
        tube2.setTextureSize(32, 32);
        tube2.mirror = true;
        setRotation(tube2, 0F, 0F, -1.134464F);
        tube3 = new ModelRenderer(this, 0, 0);
        tube3.addBox(4.2F, 4.2F, -6.3F, 4, 1, 1);
        tube3.setRotationPoint(0F, 0F, 0F);
        tube3.setTextureSize(32, 32);
        tube3.mirror = true;
        setRotation(tube3, 0F, 0F, -1.570796F);
        tube4 = new ModelRenderer(this, 14, 7);
        tube4.addBox(5.3F, 7.1F, -6F, 2, 1, 1);
        tube4.setRotationPoint(0F, 0F, 0F);
        tube4.setTextureSize(32, 32);
        tube4.mirror = true;
        setRotation(tube4, 0F, 0F, -2.007129F);

        mask.addChild(frameLeft);
        mask.addChild(frameRight);
        mask.addChild(sideLeft);
        mask.addChild(sideRight);
        mask.addChild(back);
        mask.addChild(mouth);
        mask.addChild(tube0);
        mask.addChild(tube1);
        mask.addChild(tube2);
        mask.addChild(tube3);
        mask.addChild(tube4);
    }

    @Override
    public void updateRender() {
        bipedHeadwear.showModel = false;
        bipedHead = mask;
    }
}