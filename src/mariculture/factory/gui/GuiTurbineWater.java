package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileTurbineWater;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTurbineWater extends GuiMariculture {
	private final TileTurbineWater tile;

	public GuiTurbineWater(InventoryPlayer player, TileTurbineWater tile) {
		super(new ContainerTurbineWater(tile, player), "turbine");
		this.tile = tile;
	}
	
	@Override
	public void addToolTip() {
		if (mouseX >= 83 && mouseX <= 118 && mouseY >= 14 && mouseY <= 73) {
			tooltip.add(tile.getLiquidName());
			tooltip.add(tile.getLiquidQty() + "mB");
		}
	}
	
	@Override
	public void drawForeground() {
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 52, 4, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {
		if (tile.getTankScaled(58) > 0) {
			addTank(x, y, 15, 84, tile.getTankScaled(58), tile.getFluid(), TankType.DOUBLE);
		}
	}
}