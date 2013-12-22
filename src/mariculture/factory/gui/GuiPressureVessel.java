package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPressureVessel extends GuiMariculture {
	private final TilePressureVessel tile;

	public GuiPressureVessel(InventoryPlayer player, TilePressureVessel tile) {
		super(new ContainerPressureVessel(tile, player), "pressurevessel");
		this.tile = tile;
		features.add(new FeatureTank(tile, 84, 15, TankSize.DOUBLE));
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
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 46, 5, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {}
}