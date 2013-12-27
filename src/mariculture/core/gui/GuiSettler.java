package mariculture.core.gui;

import java.util.List;

import mariculture.core.Mariculture;
import mariculture.core.blocks.TileSettler;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class GuiSettler extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/settler.png");
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
	private final TileSettler tile;

	public GuiSettler(InventoryPlayer player, TileSettler tile) {
		super(new ContainerSettler(tile, player));
		this.tile = tile;
	}

	public List<String> handleTooltip(int mousex, int mousey, List<String> currenttip) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		if (mousex >= x + 42 && mousex <= x + 77 && mousey >= y + 13 && mousey <= y + 73) {
			currenttip.add(tile.getLiquidName());
			currenttip.add(tile.getLiquidQty() + "mB");
		}

		return currenttip;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 52, 4, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (tile.getTankScaled(58) > 0) {
			displayGauge(x, y, 15, 43, tile.getTankScaled(58), tile.getFluid());
		}

		int progress = tile.getFreezeProgressScaled(24);
		this.drawTexturedModalRect(x + 82, y + 37, 176, 74, progress + 1, 16);
	}

	private void displayGauge(int j, int k, int line, int col, int squaled, FluidStack liquid) {
		if (liquid == null) {
			return;
		}

		int start = 0;

		Icon liquidIcon = null;
		Fluid fluid = liquid.getFluid();

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