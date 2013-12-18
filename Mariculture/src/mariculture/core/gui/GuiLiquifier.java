package mariculture.core.gui;

import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.Mariculture;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.PrefixColor;
import mariculture.fishery.items.ItemFishyFood;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class GuiLiquifier extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/liquifier.png");
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;
	private final TileLiquifier tile;

	public GuiLiquifier(InventoryPlayer player, TileLiquifier tile) {
		super(new ContainerLiquifier(tile, player));
		this.tile = tile;
	}

	public List<String> handleTooltip(final int mousex, final int mousey, final List<String> currenttip) {
		final int x = (width - xSize) / 2;
		final int y = (height - ySize) / 2;

		if (mousex >= x + 9 && mousex <= x + 13 && mousey >= y + 14 && mousey <= y + 73) {
			currenttip.add(tile.getRealTemperature() + "\u00B0" + "C");
		}

		if (mousex >= x + 85 && mousex <= x + 101 && mousey >= y + 13 && mousey <= y + 73) {
			currenttip.add(tile.getLiquidName());
			currenttip.add(tile.getLiquidQty() + "mB");
		}

		return currenttip;
	}

	public List<String> handleItemTooltip(ItemStack stack, int mousex, int mousey, List<String> currenttip) {
		if (stack != null) {
			int meltingPoint = MaricultureHandlers.smelter.getMeltingPoint(stack);
			int maxTemp = MaricultureHandlers.smelter.getBurnTemp(stack, true);

			if (meltingPoint > 0) {
				currenttip.add(PrefixColor.ORANGE + StatCollector.translateToLocal("mariculture.string.melting") + ": " + meltingPoint + "\u00B0" + "C");
			}
			
			if (MaricultureHandlers.smelter.getResult(stack, -1) != null) {
				SmelterOutput result = MaricultureHandlers.smelter.getResult(stack, -1);
				if (result.fluid.amount > 0) {
					currenttip.add(PrefixColor.INDIGO + FluidHelper.getName(result.fluid.getFluid()) + ": " + tile.getFluidAmount(stack, result.fluid.amount) + "mB");
				}
				
				if(result.output != null && result.chance > 0) {
					int chance = (int) (((float)1 / result.chance) * 100);
					currenttip.add(PrefixColor.GREY + chance + StatCollector.translateToLocal("mariculture.string.percent") + result.output.getDisplayName());
				}
			}

			if (maxTemp > -1) {
				currenttip.add(PrefixColor.GREY + StatCollector.translateToLocal("mariculture.string.maxTemp") + ": " + maxTemp + "\u00B0" + "C");
			}
		}

		return currenttip;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 16, 4, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (tile.getTankScaled(58) > 0) {
			displayGauge(x, y, 15, 85, tile.getTankScaled(58), tile.getFluid());
		}

		if (tile.isBurning()) {
			final int burn = tile.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(x + 31, y + 38 + 12 - burn, 176, 72 - burn, 14, burn + 2);
		}

		int progress = tile.getCookProgressScaled(24);
		this.drawTexturedModalRect(x + 57, y + 37, 176, 74, progress + 1, 16);

		int temp = tile.getTemperatureScaled(60);
		this.drawTexturedModalRect(x + 9, y + 14 + 60 - temp, 198, 60 - temp, 5, temp);
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