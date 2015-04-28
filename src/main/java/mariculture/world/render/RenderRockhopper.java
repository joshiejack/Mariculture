package mariculture.world.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRockhopper extends RenderLiving {
    private static final ResourceLocation texture = new ResourceLocation("mariculture", "textures/entity/rockhopper.png");
    private ModelRockhopper model;
    
    public RenderRockhopper(ModelBase model, float shadowSize) {
        super(model, shadowSize);
        this.model = (ModelRockhopper) model;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
