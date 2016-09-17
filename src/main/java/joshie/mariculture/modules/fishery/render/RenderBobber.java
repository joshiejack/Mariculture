package joshie.mariculture.modules.fishery.render;

import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBobber extends Render<EntityFishHookMC> {
    private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");

    public RenderBobber(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFishHookMC entity) {
        return FISH_PARTICLES;
    }
}

