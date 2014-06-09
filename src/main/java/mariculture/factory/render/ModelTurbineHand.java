package mariculture.factory.render;

import mariculture.core.render.IModelMariculture;
import mariculture.factory.tile.TileTurbineBase.EnergyStage;
import mariculture.factory.tile.TileTurbineHand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelTurbineHand extends ModelBase implements IModelMariculture {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer Base2;
    private ModelRenderer Top;
    private ModelRenderer Base;
    private ModelRenderer Middle;
    private ModelRenderer Blade1L;
    private ModelRenderer Blade2L;
    private ModelRenderer Blade3L;
    private ModelRenderer Blade4L;
    private ModelRenderer Blade1H;
    private ModelRenderer Blade2H;
    private ModelRenderer Blade3H;
    private ModelRenderer Blade4H;

    public ModelTurbineHand() {
        textureWidth = 64;
        textureHeight = 64;

        Base2 = new ModelRenderer(this, 0, 32);
        Base2.addBox(-7F, -14F, -7F, 14, 1, 14);
        Base2.setRotationPoint(0F, 13F, 0F);
        Base2.setTextureSize(64, 32);
        Base2.mirror = true;
        setRotation(Base2, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 32, 23);
        Top.addBox(-4F, -15F, -4F, 8, 1, 8);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 47);
        Base.addBox(-8F, -14F, -8F, 16, 1, 16);
        Base.setRotationPoint(0F, 14F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Middle = new ModelRenderer(this, 0, 15);
        Middle.addBox(-2F, -14F, -2F, 4, 13, 4);
        Middle.setRotationPoint(0F, 0F, 0F);
        Middle.setTextureSize(64, 32);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);
        Blade1L = new ModelRenderer(this, 0, 0);
        Blade1L.addBox(-1F, -6F, -7F, 2, 2, 14);
        Blade1L.setRotationPoint(0F, 0F, 0F);
        Blade1L.setTextureSize(64, 32);
        Blade1L.mirror = true;
        setRotation(Blade1L, 0F, -1.178097F, 0F);
        Blade2L = new ModelRenderer(this, 0, 0);
        Blade2L.addBox(-1F, -6F, -7F, 2, 2, 14);
        Blade2L.setRotationPoint(0F, 0F, 0F);
        Blade2L.setTextureSize(64, 32);
        Blade2L.mirror = true;
        setRotation(Blade2L, 0F, -0.3926991F, 0F);
        Blade3L = new ModelRenderer(this, 0, 0);
        Blade3L.addBox(-1F, -6F, -7F, 2, 2, 14);
        Blade3L.setRotationPoint(0F, 0F, 0F);
        Blade3L.setTextureSize(64, 32);
        Blade3L.mirror = true;
        setRotation(Blade3L, 0F, 1.178097F, 0F);
        Blade4L = new ModelRenderer(this, 0, 0);
        Blade4L.addBox(-1F, -6F, -7F, 2, 2, 14);
        Blade4L.setRotationPoint(0F, 0F, 0F);
        Blade4L.setTextureSize(64, 32);
        Blade4L.mirror = true;
        setRotation(Blade4L, 0F, 0.3926991F, 0F);
        Blade1H = new ModelRenderer(this, 0, 0);
        Blade1H.addBox(-1F, -9F, -7F, 2, 2, 14);
        Blade1H.setRotationPoint(0F, 0F, 0F);
        Blade1H.setTextureSize(64, 32);
        Blade1H.mirror = true;
        setRotation(Blade1H, 0F, 0F, 0F);
        Blade2H = new ModelRenderer(this, 0, 0);
        Blade2H.addBox(-1F, -9F, -7F, 2, 2, 14);
        Blade2H.setRotationPoint(0F, 0F, 0F);
        Blade2H.setTextureSize(64, 32);
        Blade2H.mirror = true;
        setRotation(Blade2H, 0F, 1.570796F, 0F);
        Blade3H = new ModelRenderer(this, 0, 0);
        Blade3H.addBox(-1F, -9F, -7F, 2, 2, 14);
        Blade3H.setRotationPoint(0F, 0F, 0F);
        Blade3H.setTextureSize(64, 32);
        Blade3H.mirror = true;
        setRotation(Blade3H, 0F, 0.7853982F, 0F);
        Blade4H = new ModelRenderer(this, 0, 0);
        Blade4H.addBox(-1F, -9F, -7F, 2, 2, 14);
        Blade4H.setRotationPoint(0F, 0F, 0F);
        Blade4H.setTextureSize(64, 32);
        Blade4H.mirror = true;
        setRotation(Blade4H, 0F, -0.7853982F, 0F);
    }

    @Override
    public void render(TileEntity tile, double x, double y, double z) {
        render((TileTurbineHand) tile, x, y, z);
    }

    private void render(TileTurbineHand tile, double x, double y, double z) {
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

        Base2.render(scale);
        Top.render(scale);
        Base.render(scale);

        int xCoord = 0;
        int yCoord = 15;
        if (tile.energyStage != EnergyStage.BLUE) {
            xCoord = 16;
            yCoord = 15;
        }

        Middle = new ModelRenderer(this, xCoord, yCoord);
        Middle.addBox(-2F, -14F, -2F, 4, 13, 4);
        Middle.setRotationPoint(0F, 0F, 0F);
        Middle.setTextureSize(64, 32);
        Middle.mirror = true;
        setRotation(Middle, 0F, 0F, 0F);

        float angle = tile.getAngle();

        Middle.rotateAngleY = angle;
        Blade1L.rotateAngleY = angle;
        Blade2L.rotateAngleY = angle + 45;
        Blade3L.rotateAngleY = angle + 90;
        Blade4L.rotateAngleY = angle + 135;

        Blade1H.rotateAngleY = (float) (angle + 22.5);
        Blade2H.rotateAngleY = (float) (angle + 45 + 22.5);
        Blade3H.rotateAngleY = (float) (angle + 90 + 22.5);
        Blade4H.rotateAngleY = (float) (angle + 135 + 22.5);

        Middle.render(scale);
        Blade1L.render(scale);
        Blade2L.render(scale);
        Blade3L.render(scale);
        Blade4L.render(scale);
        Blade1H.render(scale);
        Blade2H.render(scale);
        Blade3H.render(scale);
        Blade4H.render(scale);

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
        Base2.render(scale);
        Top.render(scale);
        Base.render(scale);
        Middle.render(scale);
        Blade1L.render(scale);
        Blade2L.render(scale);
        Blade3L.render(scale);
        Blade4L.render(scale);
        Blade1H.render(scale);
        Blade2H.render(scale);
        Blade3H.render(scale);
        Blade4H.render(scale);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
