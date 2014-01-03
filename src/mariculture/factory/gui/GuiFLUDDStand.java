package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.factory.blocks.TileFLUDDStand;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFLUDDStand extends GuiMariculture {
	public GuiFLUDDStand(InventoryPlayer player, TileFLUDDStand tile) {
		super(new ContainerFLUDDStand(tile, player), "fluddstand", 10);
		features.add(new FeatureTank(tile, 61, 19, TankSize.DOUBLE));
		features.add(new FeatureRedstone(tile));
		features.add(new FeatureUpgrades());
	}

	@Override
	public int getX() {
		return 60;
	}
}

