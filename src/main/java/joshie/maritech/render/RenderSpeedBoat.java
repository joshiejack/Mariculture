package joshie.maritech.render;

import joshie.maritech.entity.EntitySpeedBoat;
import joshie.maritech.lib.MTModInfo;
import joshie.maritech.model.ModelSpeedBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpeedBoat extends Render {
    private static final ResourceLocation resource = new ResourceLocation(MTModInfo.MODPATH + ":textures/entity/speedboat.png");
    private ModelSpeedBoat modelBoat;

    public RenderSpeedBoat() {
        shadowSize = 0.5F;
        modelBoat = new ModelSpeedBoat();
    }

    public void renderBoat(EntitySpeedBoat entity, double x, double y, double z, float yaw, float tickTime) {
        bindEntityTexture(entity);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated((float) x, (float) y - 0.1F, (float) z);
        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glRotatef(180.0F + yaw, 0.0F, 1.0F, 0.0F);

        modelBoat.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float tickTime) {
        renderBoat((EntitySpeedBoat) entity, x, y, z, yaw, tickTime);
    }

    protected ResourceLocation func_110781_a(EntitySpeedBoat entity) {
        return resource;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return func_110781_a((EntitySpeedBoat) entity);
    }
}
