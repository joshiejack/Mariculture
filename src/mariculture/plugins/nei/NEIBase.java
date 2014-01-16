package mariculture.plugins.nei;

import static codechicken.core.gui.GuiDraw.drawTexturedModalRect;
import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.core.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;

public abstract class NEIBase extends TemplateRecipeHandler {
	protected void drawFluidRect(int j, int k, FluidStack fluid, TankSize size) {
		int scale = fluid.amount * 58 / 5000;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		drawScaledTexturedModelRectFromIcon(j, k, fluid.getFluid().getIcon(), 16, 16);
		drawScaledTexturedModelRectFromIcon(j + 16, k, fluid.getFluid().getIcon(), 16, 16);
		drawScaledTexturedModelRectFromIcon(j + 32, k, fluid.getFluid().getIcon(), 2, 16);
		Minecraft.getMinecraft().renderEngine.bindTexture(Feature.texture);
		drawTexturedModalRect(j, k - 42, 16, 0, 34, 60);
		GuiDraw.changeTexture(getGuiTexture());
	}
	
	public static void drawScaledTexturedModelRectFromIcon(int i, int j, Icon icon, int x, int y) {
		if (icon == null) {
			return;
		}
		
		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(i + 0, j + y, GuiDraw.gui.getZLevel(), minU, minV + (maxV - minV) * y / 16.0D);
		tessellator.addVertexWithUV(i + x, j + y, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * x / 16.0D, minV + (maxV - minV) * y / 16.0D);
		tessellator.addVertexWithUV(i + x, j + 0, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * x / 16.0D, minV);
		tessellator.addVertexWithUV(i + 0, j + 0, GuiDraw.gui.getZLevel(), minU, minV);
		tessellator.draw();
	}
}
