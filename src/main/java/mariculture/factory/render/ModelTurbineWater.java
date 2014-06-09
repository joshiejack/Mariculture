package mariculture.factory.render;

import mariculture.core.render.IModelMariculture;
import mariculture.factory.tile.TileTurbineBase.EnergyStage;
import mariculture.factory.tile.TileTurbineWater;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelTurbineWater extends ModelBase implements IModelMariculture {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer Base;
    private ModelRenderer Rod;
    private ModelRenderer Blade6;
    private ModelRenderer Blade1;
    private ModelRenderer Blade2;
    private ModelRenderer Blade3;
    private ModelRenderer Blade4;
    private ModelRenderer Blade5;
    private ModelRenderer Blade7;
    private ModelRenderer Blade8;
    private ModelRenderer Blade9;
    private ModelRenderer Blade10;
    private ModelRenderer Blade11;
    private ModelRenderer Blade12;
    private ModelRenderer Top;

    public ModelTurbineWater() {
        textureWidth = 64;
        textureHeight = 64;

        Base = new ModelRenderer(this, 0, 18);
        Base.addBox(-8F, 0F, -8F, 16, 2, 16);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Rod = new ModelRenderer(this, 48, 0);
        Rod.addBox(-2F, -14F, -2F, 4, 14, 4);
        Rod.setRotationPoint(0F, 0F, 0F);
        Rod.setTextureSize(64, 64);
        Rod.mirror = true;
        setRotation(Rod, 0F, 0F, 0F);
        Blade1 = new ModelRenderer(this, 0, 0);
        Blade1.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade1.setRotationPoint(0F, 0F, 0F);
        Blade1.setTextureSize(64, 64);
        Blade1.mirror = true;
        setRotation(Blade1, 0F, 0F, 0F);
        Blade2 = new ModelRenderer(this, 0, 0);
        Blade2.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade2.setRotationPoint(0F, 0F, 0F);
        Blade2.setTextureSize(64, 64);
        Blade2.mirror = true;
        setRotation(Blade2, 0F, 0.5235988F, 0F);
        Blade3 = new ModelRenderer(this, 0, 0);
        Blade3.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade3.setRotationPoint(0F, 0F, 0F);
        Blade3.setTextureSize(64, 64);
        Blade3.mirror = true;
        setRotation(Blade3, 0F, 1.047198F, 0F);
        Blade4 = new ModelRenderer(this, 0, 0);
        Blade4.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade4.setRotationPoint(0F, 0F, 0F);
        Blade4.setTextureSize(64, 64);
        Blade4.mirror = true;
        setRotation(Blade4, 0F, 1.570796F, 0F);
        Blade5 = new ModelRenderer(this, 0, 0);
        Blade5.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade5.setRotationPoint(0F, 0F, 0F);
        Blade5.setTextureSize(64, 64);
        Blade5.mirror = true;
        setRotation(Blade5, 0F, 2.094395F, 0F);
        Blade6 = new ModelRenderer(this, 0, 0);
        Blade6.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade6.setRotationPoint(0F, 0F, 0F);
        Blade6.setTextureSize(64, 64);
        Blade6.mirror = true;
        setRotation(Blade6, 0F, 2.617994F, 0F);
        Blade7 = new ModelRenderer(this, 0, 0);
        Blade7.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade7.setRotationPoint(0F, 0F, 0F);
        Blade7.setTextureSize(64, 64);
        Blade7.mirror = true;
        setRotation(Blade7, 0F, 3.141593F, 0F);
        Blade8 = new ModelRenderer(this, 0, 0);
        Blade8.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade8.setRotationPoint(0F, 0F, 0F);
        Blade8.setTextureSize(64, 64);
        Blade8.mirror = true;
        setRotation(Blade8, 0F, 3.665191F, 0F);
        Blade9 = new ModelRenderer(this, 0, 0);
        Blade9.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade9.setRotationPoint(0F, 0F, 0F);
        Blade9.setTextureSize(64, 64);
        Blade9.mirror = true;
        setRotation(Blade9, 0F, 4.18879F, 0F);
        Blade10 = new ModelRenderer(this, 0, 0);
        Blade10.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade10.setRotationPoint(0F, 0F, 0F);
        Blade10.setTextureSize(64, 64);
        Blade10.mirror = true;
        setRotation(Blade10, 0F, 4.712389F, 0F);
        Blade11 = new ModelRenderer(this, 0, 0);
        Blade11.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade11.setRotationPoint(0F, 0F, 0F);
        Blade11.setTextureSize(64, 64);
        Blade11.mirror = true;
        setRotation(Blade11, 0F, 5.235988F, 0F);
        Blade12 = new ModelRenderer(this, 0, 0);
        Blade12.addBox(2F, -10F, -1F, 5, 8, 2);
        Blade12.setRotationPoint(0F, 0F, 0F);
        Blade12.setTextureSize(64, 64);
        Blade12.mirror = true;
        setRotation(Blade12, 0F, 5.759586F, 0F);
        Top = new ModelRenderer(this, 0, 36);
        Top.addBox(-4F, -15F, -4F, 8, 1, 8);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
    }

    @Override
    public void render(TileEntity tile, double x, double y, double z) {
        render((TileTurbineWater) tile, x, y, z);
    }

    private void render(TileTurbineWater tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        ForgeDirection facing = tile.orientation;
        if (facing == ForgeDirection.DOWN) {
            GL11.glTranslated(x + 0.5F, y + 0.89F, z + 0.5F);
            GL11.glRotatef(180, 0F, 0F, 1F);
        } else if (facing == ForgeDirection.EAST) {
            GL11.glTranslated(x + 0.11F, y + 0.497F, z + 0.5F);
            GL11.glRotatef(-90, 0F, 0F, 1F);
        } else if (facing == ForgeDirection.NORTH) {
            GL11.glTranslated(x + 0.5F, y + 0.497F, z + 0.89F);
            GL11.glRotatef(-90, 1F, 0F, 0F);
        } else if (facing == ForgeDirection.WEST) {
            GL11.glTranslated(x + 0.89F, y + 0.497F, z + 0.5F);
            GL11.glRotatef(90, 0F, 0F, 1F);
        } else if (facing == ForgeDirection.SOUTH) {
            GL11.glTranslated(x + 0.5F, y + 0.497F, z + 0.11);
            GL11.glRotatef(90, 1F, 0F, 0F);
        } else if (facing == ForgeDirection.UP) {
            GL11.glTranslated(x + 0.5F, y + 0.1F, z + 0.5F);
            GL11.glRotatef(90, 0F, 1F, 0F);
        }

        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glScalef(1.2F, 1.2F, 1.2F);

        Base.render(scale);
        int xCoord = 48;
        int yCoord = 0;
        if (tile.energyStage == EnergyStage.GREEN) {
            xCoord = 32;
            yCoord = 0;
        } else if (tile.energyStage == EnergyStage.YELLOW) {
            xCoord = 16;
            yCoord = 0;
        } else if (tile.energyStage == EnergyStage.ORANGE) {
            xCoord = 0;
            yCoord = 45;
        } else if (tile.energyStage == EnergyStage.RED) {
            xCoord = 0;
            yCoord = 13;
        }

        Rod = new ModelRenderer(this, xCoord, yCoord);
        Rod.addBox(-2F, -14F, -2F, 4, 14, 4);
        Rod.setRotationPoint(0F, 0F, 0F);
        Rod.setTextureSize(64, 64);
        Rod.mirror = true;
        setRotation(Rod, 0F, 0F, 0F);

        float angle = tile.getAngle();

        Rod.rotateAngleY = angle;
        Blade1.rotateAngleY = angle;
        Blade2.rotateAngleY = angle + 30;
        Blade3.rotateAngleY = angle + 60;
        Blade4.rotateAngleY = angle + 90;
        Blade5.rotateAngleY = angle + 120;
        Blade6.rotateAngleY = angle + 150;
        Blade7.rotateAngleY = angle + 180;
        Blade8.rotateAngleY = angle + 210;
        Blade9.rotateAngleY = angle + 240;
        Blade10.rotateAngleY = angle + 270;
        Blade11.rotateAngleY = angle + 300;
        Blade12.rotateAngleY = angle + 330;

        Rod.render(scale);
        Blade1.render(scale);
        Blade2.render(scale);
        Blade3.render(scale);
        Blade4.render(scale);
        Blade5.render(scale);
        Blade6.render(scale);
        Blade7.render(scale);
        Blade8.render(scale);
        Blade9.render(scale);
        Top.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderInventory(ItemRenderType type) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        switch (type) {
            case INVENTORY:
                GL11.glScalef(16F, 16F, 16F);
                GL11.glTranslatef(0.5F, 0.86F, 0F);
                break;
            case ENTITY:
                GL11.glRotatef(180, 0, 0, 1);
                break;
            default:
                GL11.glRotatef(35, 1, 0, 0);
                GL11.glRotatef(45, 0, -5, 0);
                GL11.glRotatef(180, 1, 0, 0);
                break;
        }
        Rod.render(scale);
        Blade1.render(scale);
        Blade2.render(scale);
        Blade3.render(scale);
        Blade4.render(scale);
        Blade5.render(scale);
        Blade6.render(scale);
        Blade7.render(scale);
        Blade8.render(scale);
        Blade9.render(scale);
        Blade10.render(scale);
        Blade11.render(scale);
        Blade12.render(scale);
        Top.render(scale);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
