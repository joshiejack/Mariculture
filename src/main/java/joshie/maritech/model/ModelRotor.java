package joshie.maritech.model;

import joshie.mariculture.core.render.IModelMariculture;
import joshie.maritech.tile.TileRotor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelRotor extends ModelBase implements IModelMariculture {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer Blade19;
    private ModelRenderer Blade18;
    private ModelRenderer Blade17;
    private ModelRenderer Blade13;
    private ModelRenderer Blade12;
    private ModelRenderer Blade11;
    private ModelRenderer Blade10;
    private ModelRenderer Blade9;
    private ModelRenderer Blade8;
    private ModelRenderer Blade7;
    private ModelRenderer Blade6;
    private ModelRenderer Blade5;
    private ModelRenderer Blade4;
    private ModelRenderer Blade3;
    private ModelRenderer Blade2;
    private ModelRenderer Blade1;
    private ModelRenderer Blade16;
    private ModelRenderer Blade15;
    private ModelRenderer Blade14;
    private ModelRenderer Centre;

    public ModelRotor() {
        textureWidth = 64;
        textureHeight = 64;

        Blade19 = new ModelRenderer(this, 0, 0);
        Blade19.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade19.setRotationPoint(0F, 0F, 0F);
        Blade19.setTextureSize(64, 64);
        Blade19.mirror = true;
        setRotation(Blade19, 0.5235988F, 0F, 0.0610865F);
        Blade18 = new ModelRenderer(this, 0, 0);
        Blade18.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade18.setRotationPoint(0F, 0F, 0F);
        Blade18.setTextureSize(64, 64);
        Blade18.mirror = true;
        setRotation(Blade18, 0.8726646F, 0F, 0.0610865F);
        Blade17 = new ModelRenderer(this, 0, 0);
        Blade17.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade17.setRotationPoint(0F, 0F, 0F);
        Blade17.setTextureSize(64, 64);
        Blade17.mirror = true;
        setRotation(Blade17, 1.22173F, 0F, 0.0610865F);
        Blade13 = new ModelRenderer(this, 0, 0);
        Blade13.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade13.setRotationPoint(0F, 0F, 0F);
        Blade13.setTextureSize(64, 64);
        Blade13.mirror = true;
        setRotation(Blade13, -3.665191F, 0F, 0.0610865F);
        Blade12 = new ModelRenderer(this, 0, 0);
        Blade12.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade12.setRotationPoint(0F, 0F, 0F);
        Blade12.setTextureSize(64, 64);
        Blade12.mirror = true;
        setRotation(Blade12, -3.316126F, 0F, 0.0610865F);
        Blade11 = new ModelRenderer(this, 0, 0);
        Blade11.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade11.setRotationPoint(0F, 0F, 0F);
        Blade11.setTextureSize(64, 64);
        Blade11.mirror = true;
        setRotation(Blade11, -2.96706F, 0F, 0.0610865F);
        Blade10 = new ModelRenderer(this, 0, 0);
        Blade10.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade10.setRotationPoint(0F, 0F, 0F);
        Blade10.setTextureSize(64, 64);
        Blade10.mirror = true;
        setRotation(Blade10, -2.617994F, 0F, 0.0610865F);
        Blade9 = new ModelRenderer(this, 0, 0);
        Blade9.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade9.setRotationPoint(0F, 0F, 0F);
        Blade9.setTextureSize(64, 64);
        Blade9.mirror = true;
        setRotation(Blade9, -2.268928F, 0F, 0.0610865F);
        Blade8 = new ModelRenderer(this, 0, 0);
        Blade8.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade8.setRotationPoint(0F, 0F, 0F);
        Blade8.setTextureSize(64, 64);
        Blade8.mirror = true;
        setRotation(Blade8, -2.268928F, 0F, 0.0610865F);
        Blade7 = new ModelRenderer(this, 0, 0);
        Blade7.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade7.setRotationPoint(0F, 0F, 0F);
        Blade7.setTextureSize(64, 64);
        Blade7.mirror = true;
        setRotation(Blade7, -1.919862F, 0F, 0.0610865F);
        Blade6 = new ModelRenderer(this, 0, 0);
        Blade6.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade6.setRotationPoint(0F, 0F, 0F);
        Blade6.setTextureSize(64, 64);
        Blade6.mirror = true;
        setRotation(Blade6, -1.570796F, 0F, 0.0610865F);
        Blade5 = new ModelRenderer(this, 0, 0);
        Blade5.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade5.setRotationPoint(0F, 0F, 0F);
        Blade5.setTextureSize(64, 64);
        Blade5.mirror = true;
        setRotation(Blade5, -1.22173F, 0F, 0.0610865F);
        Blade4 = new ModelRenderer(this, 0, 0);
        Blade4.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade4.setRotationPoint(0F, 0F, 0F);
        Blade4.setTextureSize(64, 64);
        Blade4.mirror = true;
        setRotation(Blade4, -0.8726646F, 0F, 0.0610865F);
        Blade3 = new ModelRenderer(this, 0, 0);
        Blade3.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade3.setRotationPoint(0F, 0F, 0F);
        Blade3.setTextureSize(64, 64);
        Blade3.mirror = true;
        setRotation(Blade3, -0.5235988F, 0F, 0.0610865F);
        Blade2 = new ModelRenderer(this, 0, 0);
        Blade2.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade2.setRotationPoint(0F, 0F, 0F);
        Blade2.setTextureSize(64, 64);
        Blade2.mirror = true;
        setRotation(Blade2, -0.1745329F, 0F, 0.0610865F);
        Blade1 = new ModelRenderer(this, 0, 0);
        Blade1.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade1.setRotationPoint(0F, 0F, 0F);
        Blade1.setTextureSize(64, 64);
        Blade1.mirror = true;
        setRotation(Blade1, 0.1745329F, 0F, 0.0610865F);
        Blade16 = new ModelRenderer(this, 0, 0);
        Blade16.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade16.setRotationPoint(0F, 0F, 0F);
        Blade16.setTextureSize(64, 64);
        Blade16.mirror = true;
        setRotation(Blade16, -4.712389F, 0F, 0.0610865F);
        Blade15 = new ModelRenderer(this, 0, 0);
        Blade15.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade15.setRotationPoint(0F, 0F, 0F);
        Blade15.setTextureSize(64, 64);
        Blade15.mirror = true;
        setRotation(Blade15, -4.363323F, 0F, 0.0610865F);
        Blade14 = new ModelRenderer(this, 0, 0);
        Blade14.addBox(-6F, 0F, 6F, 12, 1, 20);
        Blade14.setRotationPoint(0F, 0F, 0F);
        Blade14.setTextureSize(64, 64);
        Blade14.mirror = true;
        setRotation(Blade14, -4.014257F, 0F, 0.0610865F);
        Centre = new ModelRenderer(this, 0, 28);
        Centre.addBox(-9F, -6F, -6F, 18, 12, 12);
        Centre.setRotationPoint(0F, 0F, 0F);
        Centre.setTextureSize(64, 64);
        Centre.mirror = true;
        setRotation(Centre, 0F, 0F, 0F);
    }

    @Override
    public void render(TileEntity tile, double x, double y, double z) {
        render((TileRotor) tile, x, y, z);
    }

    public void render(TileRotor rotor, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        if (rotor.orientation == ForgeDirection.NORTH) {
            GL11.glRotatef(90, 0F, 1F, 0F);
        }

        GL11.glPushMatrix();
        GL11.glRotatef(rotor.angle, 1F, 0F, 0F);
        Blade19.render(scale);
        Blade18.render(scale);
        Blade17.render(scale);
        Blade13.render(scale);
        Blade12.render(scale);
        Blade11.render(scale);
        Blade10.render(scale);
        Blade9.render(scale);
        Blade8.render(scale);
        Blade7.render(scale);
        Blade6.render(scale);
        Blade5.render(scale);
        Blade4.render(scale);
        Blade3.render(scale);
        Blade2.render(scale);
        Blade1.render(scale);
        Blade16.render(scale);
        Blade15.render(scale);
        Blade14.render(scale);
        GL11.glPopMatrix();

        Centre.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
