package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeaturePower;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.factory.blocks.TileTurbineBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTurbine extends GuiMariculture {
	public GuiTurbine(InventoryPlayer player, TileTurbineBase tile) {
		super(new ContainerTurbine(tile, player), "turbine", 10);
		features.add(new FeatureTank(tile, 108, 19, TankSize.DOUBLE));
		features.add(new FeatureRedstone(tile));
		features.add(new FeatureUpgrades());
		features.add(new FeaturePower(tile, 9, 17));
	}
}
