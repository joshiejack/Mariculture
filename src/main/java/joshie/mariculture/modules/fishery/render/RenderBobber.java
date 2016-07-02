package joshie.mariculture.modules.fishery.render;

import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBobber extends Render<EntityFishHookMC> {
    private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");

    public RenderBobber(RenderManager manager) {
        super(manager);
    }

    @Override
    public void doRender(EntityFishHookMC entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        bindEntityTexture(entity);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.rotate(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entity));
        }

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertexbuffer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        if (entity.angler != null && !renderOutlines) {
            int k = entity.angler.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
            float f7 = 0F;
            float f8 = 0F;
            float f9 = (entity.angler.prevRenderYawOffset + (entity.angler.renderYawOffset - entity.angler.prevRenderYawOffset) * partialTicks) * 0.017453292F;
            double d0 = (double)MathHelper.sin(f9);
            double d1 = (double)MathHelper.cos(f9);
            double d2 = (double)k * 0.35D;
            double d4 = 0F;
            double d5 = 0F;
            double d6 = 0F;
            double d7 = 0F;

            if ((renderManager.options == null || renderManager.options.thirdPersonView <= 0) && entity.angler == Minecraft.getMinecraft().thePlayer) {
                Vec3d vec3d = new Vec3d((double)k * -0.36D, -0.05D, 0.4D);
                vec3d = vec3d.rotatePitch(-(entity.angler.prevRotationPitch + (entity.angler.rotationPitch - entity.angler.prevRotationPitch) * partialTicks) * 0.017453292F);
                vec3d = vec3d.rotateYaw(-(entity.angler.prevRotationYaw + (entity.angler.rotationYaw - entity.angler.prevRotationYaw) * partialTicks) * 0.017453292F);
                vec3d = vec3d.rotateYaw(f8 * 0.5F);
                vec3d = vec3d.rotatePitch(-f8 * 0.7F);
                d4 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * (double)partialTicks + vec3d.xCoord;
                d5 = entity.angler.prevPosY + (entity.angler.posY - entity.angler.prevPosY) * (double)partialTicks + vec3d.yCoord;
                d6 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * (double)partialTicks + vec3d.zCoord;
                d7 = (double)entity.angler.getEyeHeight();
            } else {
                d4 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * (double)partialTicks - d1 * d2 - d0 * 0.8D;
                d5 = entity.angler.prevPosY + (double)entity.angler.getEyeHeight() + (entity.angler.posY - entity.angler.prevPosY) * (double)partialTicks - 0.45D;
                d6 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * (double)partialTicks - d0 * d2 + d1 * 0.8D;
                d7 = entity.angler.isSneaking() ? -0.1875D : 0.0D;
            }

            double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
            double d8 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + 0.25D;
            double d9 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
            double d10 = (double)((float)(d4 - d13));
            double d11 = (double)((float)(d5 - d8)) + d7;
            double d12 = (double)((float)(d6 - d9));
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            for (int i1 = 0; i1 <= 16; ++i1) {
                float f10 = (float)i1 / 16.0F;
                vertexbuffer.pos(x + d10 * (double)f10, y + d11 * (double)(f10 * f10 + f10) * 0.5D + 0.25D, z + d12 * (double)f10).color(0, 0, 0, 255).endVertex();
            }

            tessellator.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFishHookMC entity) {
        return FISH_PARTICLES;
    }
}

