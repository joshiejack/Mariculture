package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.helpers.BlockHelper;
import mariculture.factory.blocks.TileTurbineWater;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTurbineWater extends GuiMariculture {
	private TileTurbineWater tile;

	public GuiTurbineWater(InventoryPlayer player, TileTurbineWater tile) {
		super(new ContainerTurbineWater(tile, player), "turbine");
		this.tile = tile;
		features.add(new FeatureTank(tile, 84, 15, TankSize.DOUBLE));
	}

	@Override
	public String getName() {
		return BlockHelper.getName(tile);
	}

	@Override
	public int getX() {
		return 52;
	}
}
