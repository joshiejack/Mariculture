package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.InventoHelper;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPressureVessel extends GuiMariculture {
	private TilePressureVessel tile;

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
	public String getName() {
		return InventoHelper.getName(tile);
	}

	@Override
	public int getX() {
		return 46;
	}
}
