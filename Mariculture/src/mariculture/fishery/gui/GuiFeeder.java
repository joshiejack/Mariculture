package mariculture.fishery.gui;

import java.util.List;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.blocks.TileFeeder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class GuiFeeder extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/feeder.png");
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
	private final TileFeeder tile;

	public GuiFeeder(InventoryPlayer player, TileFeeder tile_entity) {
		super(new ContainerFeeder(tile_entity, player));
		tile = tile_entity;
	}

	public List<String> handleTooltip(int mousex, int mousey, List<String> currenttip) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		if (mousex >= x + 16 && mousex <= x + 51 && mousey >= y + 14 && mousey <= y + 73) {
			currenttip.add(tile.getFishFood());
		}

		if (mousex >= x + 81 && mousex <= x + 84 && mousey >= y + 20 && mousey <= y + 37) {
			currenttip = tile.getLifespan(0, currenttip);
		}

		if (mousex >= x + 110 && mousex <= x + 113 && mousey >= y + 20 && mousey <= y + 37) {
			currenttip = tile.getLifespan(1, currenttip);
		}

		return currenttip;
	}

	public List<String> handleItemTooltip(ItemStack stack, int mousex, int mousey, List<String> currenttip) {
		if (stack != null) {
			int value = FishFoodHandler.getValue(stack);

			if (value > 0) {
				currenttip.add(StatCollector.translateToLocal("mariculture.string.provides") + " " + value + " "
						+ StatCollector.translateToLocal("mariculture.string.fishFood"));
			}
		}

		return currenttip;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 58, 6, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tile.getCatchTimeRemainingScaled(60);
		this.drawTexturedModalRect(x + 114, y + 9 + 60 - progress, 176, 160 - progress, 28, progress + 2);

		int fish1 = tile.getFishLifeScaled(0, 17);
		if (fish1 > -1) {
			this.drawTexturedModalRect(x + 81, y + 20 + 17 - fish1, 176, 62 + 17 - fish1, 4, fish1 + 2);
		} else {
			this.drawTexturedModalRect(x + 81, y + 20, 180, 62, 4, 18);
		}

		int fish2 = tile.getFishLifeScaled(1, 17);
		if (fish2 > -1) {
			this.drawTexturedModalRect(x + 110, y + 20 + 17 - fish2, 176, 62 + 17 - fish2, 4, fish2 + 2);
		} else {
			this.drawTexturedModalRect(x + 110, y + 20, 180, 62, 4, 18);
		}

		if (tile.getScaledBurnTime(58) > 0) {
			displayGauge(x, y, 15, 17, tile.getScaledBurnTime(58),
					FluidRegistry.getFluidStack(FluidDictionary.fish_food, 1000));
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