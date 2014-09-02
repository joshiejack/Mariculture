package joshie.mariculture.diving.render;

import joshie.mariculture.core.render.IModelMariculture;
import joshie.mariculture.core.tile.TileAirPump;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelAirPump extends ModelBase implements IModelMariculture {
    private static final float scale = (float) (1.0 / 20.0);
    private ModelRenderer base;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer wheel1;
    private ModelRenderer wheel2;

    public ModelAirPump() {
        textureWidth = 64;
        textureHeight = 64;

        base = new ModelRenderer(this, 15, 25);
        base.addBox(-6F, -6F, -4F, 10, 14, 8);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(64, 64);
        base.mirror = true;

        leg1 = new ModelRenderer(this, 0, 0);
        leg1.addBox(-6F, 8F, -4F, 1, 1, 8);
        leg1.setRotationPoint(0F, 0F, 0F);
        leg1.setTextureSize(64, 64);
        leg1.mirror = true;

        leg2 = new ModelRenderer(this, 0, 0);
        leg2.addBox(3F, 8F, -4F, 1, 1, 8);
        leg2.setRotationPoint(0F, 0F, 0F);
        leg2.setTextureSize(64, 64);
        leg2.mirror = true;

        wheel1 = new ModelRenderer(this, 30, 0);
        wheel1.addBox(0F, -5F, -5F, 1, 10, 10);
        wheel1.setRotationPoint(-7F, -3F, 0F);
        wheel1.setTextureSize(64, 64);
        wheel1.mirror = true;

        wheel2 = new ModelRenderer(this, 30, 0);
        wheel2.addBox(0F, -5F, -5F, 1, 10, 10);
        wheel2.setRotationPoint(4F, -3F, 0F);
        wheel2.setTextureSize(64, 64);
        wheel2.mirror = true;
    }

    @Override
    public void render(TileEntity tile, double x, double y, double z) {
        render((TileAirPump) tile, x, y, z);
    }

    public void render(TileAirPump tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glTranslated(x + 0.45F, y + 0.45F, z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        ForgeDirection dir = tile.orientation;
        if (dir == ForgeDirection.WEST) {
            GL11.glRotatef(90, 0F, 1F, 0F);
        } else if (dir == ForgeDirection.EAST) {
            GL11.glRotatef(-90, 0F, 1F, 0F);
        } else if (dir == ForgeDirection.NORTH) {
            GL11.glRotatef(180, 0F, 1F, 0F);
        } else if (dir == ForgeDirection.SOUTH) {
            ;
        }

        base.render(scale);
        leg1.render(scale);
        leg2.render(scale);

        float wheelAngle1 = (float) tile.getWheelAngle(1);
        float wheelAngle2 = (float) tile.getWheelAngle(2);

        wheel1.rotateAngleX = wheelAngle1;
        wheel2.rotateAngleX = wheelAngle2;

        wheel1.render(scale);
        wheel2.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public void renderInventory(ItemRenderType type) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        switch (type) {
            case INVENTORY:
                GL11.glScalef(15F, 15F, 15F);
                GL11.glTranslatef(0.6F, 0.5F, 0.7F);
                GL11.glRotatef(15, 0.0F, 1.5F, 0.0F);
                break;
            default:
                GL11.glScalef(1.3F, 1.3F, 1.3F);
                GL11.glTranslatef(0.4F, -0.25F, 0.3F);
                break;
        }

        base.render(scale);
        leg1.render(scale);
        leg2.render(scale);
        wheel1.render(scale);
        wheel2.render(scale);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
