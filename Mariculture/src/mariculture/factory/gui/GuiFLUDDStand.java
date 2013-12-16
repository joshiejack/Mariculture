package mariculture.factory.gui;

import java.util.List;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileFLUDDStand;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class GuiFLUDDStand extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/turbine.png");
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
	private final TileFLUDDStand tile;

	public GuiFLUDDStand(InventoryPlayer player, TileFLUDDStand tile) {
		super(new ContainerFLUDDStand(tile, player));
		this.tile = tile;
	}

	public List<String> handleTooltip(int mousex, int mousey, List<String> currenttip) {
		final int x = (width - xSize) / 2;
		final int y = (height - ySize) / 2;

		if (mousex >= x + 83 && mousex <= x + 118 && mousey >= y + 14 && mousey <= y + 73) {
			currenttip.add(tile.getLiquidName());
			currenttip.add(tile.getLiquidQty() + "mB");
		}

		return currenttip;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 64, 5, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		final int x = (width - xSize) / 2;
		final int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (tile.getTankScaled(58) > 0) {
			displayGauge(x, y, 15, 84, tile.getTankScaled(58), tile.getFluid());
		}
	}

	private void displayGauge(int j, int k, int line, int col, int squaled, FluidStack liquid) {
		if (liquid == null) {
			return;
		}
		int start = 0;

		Icon liquidIcon = null;
		final Fluid fluid = liquid.getFluid();
		if (fluid != null && fluid.getStillIcon() != null) {
			liquidIcon = fluid.getStillIcon();
		}
		mc.renderEngine.bindTexture(BLOCK_TEXTURE);

		if (liquidIcon != null) {
			while (true) {
				int x;

				if (squaled > 16) {
					x = 16;
					squaled -= 16;
				} else {
					x = squaled;
					squaled = 0;
				}

				drawTexturedModelRectFromIcon(j + col, k + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
				drawTexturedModelRectFromIcon(j + col + 10, k + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
				drawTexturedModelRectFromIcon(j + col + 18, k + line + 58 - x - start, liquidIcon, 16, 16 - (16 - x));
				start = start + 16;

				if (x == 0 || squaled == 0) {
					break;
				}
			}
		}

		mc.renderEngine.bindTexture(TEXTURE);
		drawTexturedModalRect(j + col, k + line, 176, 0, 16, 60);
	}
}