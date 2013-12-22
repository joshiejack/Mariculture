package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileTurbineWater;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTurbineWater extends GuiMariculture {
	private final TileTurbineWater tile;

	public GuiTurbineWater(InventoryPlayer player, TileTurbineWater tile) {
		super(new ContainerTurbineWater(tile, player), "turbine");
		this.tile = tile;
		features.add(new FeatureTank(tile, 84, 15, TankSize.DOUBLE));
	}
	
	@Override
	public void drawForeground() {
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 52, 4, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {}
}