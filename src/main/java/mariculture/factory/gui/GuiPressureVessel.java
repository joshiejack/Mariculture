package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.helpers.BlockHelper;
import mariculture.factory.tile.TilePressureVessel;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiPressureVessel extends GuiMariculture {
    private TilePressureVessel tile;

    public GuiPressureVessel(InventoryPlayer player, TilePressureVessel tile) {
        super(new ContainerPressureVessel(tile, player), "pressurevessel", 10);
        this.tile = tile;
        features.add(new FeatureTank(tile, 87, 19, TankSize.DOUBLE));
        features.add(new FeatureUpgrades());
    }

    @Override
    public String getName() {
        return BlockHelper.getName(tile);
    }

    @Override
    public int getX() {
        return 46;
    }
}
