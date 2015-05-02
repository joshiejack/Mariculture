package mariculture.fishery.render;

import mariculture.core.render.RenderBase;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

public class RenderFishTank extends RenderBase {
    public RenderFishTank() {}

    @Override
    public void renderBlock() {
        render.renderAllFaces = false;
        if (isItem()) {
            setTexture(FluidRegistry.WATER.getIcon());
            renderBlock(0, 0, 0, 1, 1, 1);
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            render.clearOverrideBlockTexture();
            renderBlock(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
        } else {
            setTexture(FluidRegistry.WATER.getIcon());
            render.renderStandardBlock(block, x, y, z);
            render.clearOverrideBlockTexture();
            render.setRenderBounds(-0.01D, -0.01D, -0.01D, 1.01D, 1.01D, 1.01D);
            render.renderStandardBlock(block, x, y, z);
        }
    }
}
