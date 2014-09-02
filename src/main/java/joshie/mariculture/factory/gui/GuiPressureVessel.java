package joshie.mariculture.factory.gui;

import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.gui.feature.FeatureTank;
import joshie.mariculture.core.gui.feature.FeatureTank.TankSize;
import joshie.mariculture.core.gui.feature.FeatureUpgrades;
import joshie.mariculture.core.helpers.BlockHelper;
import joshie.mariculture.factory.tile.TilePressureVessel;
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
