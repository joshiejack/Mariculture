package mariculture.diving.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class ModelLifejacket extends ModelBiped {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer Log;
    private ModelRenderer Strap1;
    private ModelRenderer Strap2;
    private ModelRenderer Strap3;
    private ModelRenderer Strap4;
    private ModelRenderer Strap5;
    private ModelRenderer Strap6;

    public ModelLifejacket() {
        textureWidth = 64;
        textureHeight = 64;

        Log = new ModelRenderer(this, 0, 0);
        Log.addBox(-6F, 2F, -6F, 12, 12, 12);
        Log.setRotationPoint(0F, 0F, 0F);
        Log.setTextureSize(64, 64);
        Log.mirror = true;
        setRotation(Log, 0F, 0F, 0F);
        Strap1 = new ModelRenderer(this, 0, 36);
        Strap1.addBox(-5F, -1F, -6F, 1, 1, 6);
        Strap1.setRotationPoint(0F, 0F, 0F);
        Strap1.setTextureSize(64, 64);
        Strap1.mirror = true;
        setRotation(Strap1, 0.5204921F, 0F, 0F);
        Strap2 = new ModelRenderer(this, 32, 32);
        Strap2.addBox(-5F, -1F, -1F, 1, 1, 2);
        Strap2.setRotationPoint(0F, 0F, 0F);
        Strap2.setTextureSize(64, 64);
        Strap2.mirror = true;
        setRotation(Strap2, 0F, 0F, 0F);
        Strap3 = new ModelRenderer(this, 0, 36);
        Strap3.addBox(-5F, -1F, 0F, 1, 1, 6);
        Strap3.setRotationPoint(0F, 0F, 0F);
        Strap3.setTextureSize(64, 64);
        Strap3.mirror = true;
        setRotation(Strap3, -0.5205006F, 0F, 0F);
        Strap4 = new ModelRenderer(this, 0, 36);
        Strap4.addBox(4F, -1F, 0F, 1, 1, 6);
        Strap4.setRotationPoint(0F, 0F, 0F);
        Strap4.setTextureSize(64, 64);
        Strap4.mirror = true;
        setRotation(Strap4, -0.5205006F, 0F, 0F);
        Strap5 = new ModelRenderer(this, 32, 32);
        Strap5.addBox(4F, -1F, -1F, 1, 1, 2);
        Strap5.setRotationPoint(0F, 0F, 0F);
        Strap5.setTextureSize(64, 64);
        Strap5.mirror = true;
        setRotation(Strap5, 0F, 0F, 0F);
        Strap6 = new ModelRenderer(this, 0, 36);
        Strap6.addBox(4F, -1F, -6F, 1, 1, 6);
        Strap6.setRotationPoint(0F, 0F, 0F);
        Strap6.setTextureSize(64, 64);
        Strap6.mirror = true;
        setRotation(Strap6, 0.5204921F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(0F, -0.0129F, 0.0F);

            Log.render(scale);
            Strap1.render(scale);
            Strap2.render(scale);
            Strap3.render(scale);
            Strap4.render(scale);
            Strap5.render(scale);
            Strap6.render(scale);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
