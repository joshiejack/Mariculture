package mariculture.core.gui;

import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Text;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiLiquifier extends GuiMariculture {
	private final TileLiquifier tile;

	public GuiLiquifier(InventoryPlayer player, TileLiquifier tile) {
		super(new ContainerLiquifier(tile, player), "liquifier");
		this.tile = tile;
	}

	@Override
	public void addToolTip() {		
		if (mouseX >= 9 && mouseX <= 13 && mouseY >= 14 && mouseY <= 73) {
			tooltip.add(tile.getRealTemperature() + "\u00B0" + "C");
		}
		
		if (mouseX >= 84 && mouseX <= 101 && mouseY >= 13 && mouseY <= 72) {
			tooltip.add(tile.getLiquidName());
			tooltip.add(tile.getLiquidQty() + "mB");
		}
	}

	@Override
	public void addItemToolTip(ItemStack stack, List<String> currenttip) {
		if (stack != null) {
			int meltingPoint = MaricultureHandlers.smelter.getMeltingPoint(stack);
			int maxTemp = MaricultureHandlers.smelter.getBurnTemp(stack, true);

			if (meltingPoint > 0) {
				currenttip.add(Text.ORANGE + StatCollector.translateToLocal("mariculture.string.melting") + ": " + meltingPoint + "\u00B0" + "C");
			}
			
			if (MaricultureHandlers.smelter.getResult(stack, -1) != null) {
				SmelterOutput result = MaricultureHandlers.smelter.getResult(stack, -1);
				if (result.fluid.amount > 0) {
					currenttip.add(Text.INDIGO + FluidHelper.getName(result.fluid.getFluid()) + ": " + tile.getFluidAmount(stack, result.fluid.amount) + "mB");
				}
				
				if(result.output != null && result.chance > 0) {
					int chance = (int) (((float)1 / result.chance) * 100);
					currenttip.add(Text.GREY + chance + StatCollector.translateToLocal("mariculture.string.percent") + result.output.getDisplayName());
				}
			}

			if (maxTemp > -1) {
				currenttip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.maxTemp") + ": " + maxTemp + "\u00B0" + "C");
			}
		}
	}
	
	@Override
	public void drawForeground() {
		 this.fontRenderer.drawString(InventoryHelper.getName(tile), 16, 4, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {
		if (tile.getTankScaled(58) > 0) {
			addTank(x, y, 15, 85, tile.getTankScaled(58), tile.getFluid(), TankType.SINGLE);
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
}